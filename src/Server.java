package December;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private int port = 8000;
    private ArrayList<ServWorker> workers;
    private Route route;
    private boolean isSSL;
    private String file_static_path;
    private final static int MAXLEN = 8196;

    public Server(int port){
        this.port = port;
        this.route = new Route();
        this.workers = new ArrayList<ServWorker>();
    }

    public void run() {
        try {
            ServerSocket serv_sock = new ServerSocket();
            InetSocketAddress ipep = new InetSocketAddress(this.port);
            serv_sock.bind(ipep);

            while(true){
                Socket sock = serv_sock.accept();
                InetAddress inetAddr = serv_sock.getInetAddress();
                ServWorker worker = new ServWorker(sock, inetAddr);
                worker.start();
                this.workers.add(worker);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ServWorker extends Thread{
        private Socket sock;
        private String request_path = null;
        private InputStream reader = null;
        private PrintWriter writer = null;
        private OutputStream out = null;
        private InetAddress inetAddr;
        private String method;
        private Utility util;

        private HttpRequest request;
        private HttpResponse response;

        public ServWorker(Socket worker_sock, InetAddress inetAddr) throws IOException {
            this.sock = worker_sock;
            this.inetAddr = inetAddr;
            this.reader = sock.getInputStream();
            this.request = new HttpRequest(this.reader);
            this.method = this.request.getMethod();
            this.request_path = this.request.getPath();
            this.out = sock.getOutputStream();
            this.util = new Utility();
        }

        @Override
        public void run() {
            try {
                if(!route.isRegistedRoutePath(this.request_path, this.method)) {
                    this.sendFobiddenMessage();

                } else {
                    this.response = new HttpResponse(this.request_path);
                    String data = route.getRouteTask(
                            this.request_path, this.method).task(this.request, this.response);

                    int type = this.util.getType(this.request_path);
                    switch (type){
                        case Utility.TEXT:
                        case Utility.APPLICATION:
                        case Utility.FONT:
                            this.sendPayload(data);
                            break;
                        case Utility.IMAGE:
                        case Utility.VIDEO:
                        default:
                            sendBinaryPayload();
                            break;
                    }
                }

                this.close();
                this.printLog();
            } catch(Exception e){
                e.printStackTrace();
                this.internalError();
            }
        }

        private void close(){
            try {
                this.reader.close();
                sock.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            workers.remove(this);
        }

        private void printLog() {
            System.out.printf("+%-18s [%29s] - %-9s %-15s %d - %-20s\n",
                    inetAddr.getHostAddress(),
                    this.response.getDate(),
                    this.request.getMethod(),
                    this.request.getPath(),
                    this.response.getStatus_code(),
                    this.response.getStatus_info()
            );
        }


        private void sendBinaryPayload() throws IOException {
            try {
                this.writer = new PrintWriter(this.out);
                File f = new File(file_static_path + this.request_path);
                long flen = f.length();
                this.response.putHeader("Content-Length", Long.toString(flen));
                this.response.putHeader("Cache-Control","no-cache");

                String res = this.response.set();
                this.writer.append(res);
                this.writer.flush();

                byte[] b = new byte[MAXLEN];
                FileInputStream fis = new FileInputStream(file_static_path + this.request_path);
                int len = fis.read(b,0,MAXLEN);
                while (len != -1) {
                    this.out.write(b, 0, len);
                    len = fis.read(b);
                }

                this.out.flush();
                this.writer.close();
                fis.close();
            } catch (FileNotFoundException e) {
                this.sendFobiddenMessage();
            }
        }

        private void sendFobiddenMessage(){
            this.writer = new PrintWriter(this.out);
            this.response = new HttpResponse(this.request_path);
            this.response.putStatusCode(404);
            String res = this.response.set();
            this.writer.print(res);
            this.writer.flush();
            this.writer.close();
        }

        private void sendPayload(String data){
            this.writer = new PrintWriter(this.out);
            this.response.setBody(data);
            String res = this.response.set();
            this.writer.print(res);
            this.writer.flush();
            this.writer.close();
        }

        private void internalError(){
            this.writer = new PrintWriter(this.out);
            this.response.putStatusCode(500);
            this.response.setBody(null);
            String res = this.response.set();
            this.writer.print(res);
            this.writer.flush();
            this.close();
            this.printLog();
        }
    }

    public void setStaticPath(String path){
        this.file_static_path = path;
    }

    public void route(String path, String method, RouteTask route_task){
        this.route.setRoute(path, method, route_task);
    }
}