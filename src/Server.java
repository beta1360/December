package December;

import December.HttpRequest;

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
                workers.add(worker);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ServWorker extends Thread{
        private Socket sock;
        private InputStream reader = null;
        private PrintWriter writer = null;
        private InetAddress inetAddr;

        private HttpRequest request;
        private HttpResponse response;

        public ServWorker(Socket worker_sock, InetAddress inetAddr){
            this.sock = worker_sock;
            this.inetAddr = inetAddr;
        }

        @Override
        public void run() {
            try {
                this.reader = sock.getInputStream();
                this.request = new HttpRequest(this.reader);
                String method = this.request.getMethod();
                String path = this.request.getPath();

                this.writer = new PrintWriter(sock.getOutputStream());

                if(!route.isRegistedRoutePath(path, method)) {
                    this.response = new HttpResponse(path);
                    this.response.putStatusCode(404);
                } else {
                    this.response = new HttpResponse(path);
                    String data = route.getRouteTask(path,method).task(this.request, this.response);
                    this.response.setBody(data);
                }
                String res = this.response.set();
                this.writer.print(res);
                this.writer.flush();

                this.close();
                this.printLog();
            } catch(Exception e){
                e.printStackTrace();
                this.close();
            }
        }

        private void close(){
            try {
                this.reader.close();
                this.writer.close();
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
    }

    public void route(String path, String method, RouteTask route_task){
        this.route.setRoute(path, method, route_task);
    }
}