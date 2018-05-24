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

    public Server(int port){
        this.port = port;
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
        private Socket sock = null;
        private BufferedReader reader = null;
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
                this.reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                this.writer = new PrintWriter(sock.getOutputStream());
                System.out.println("new request is arrived. - " + inetAddr.getHostAddress());
                this.request = new HttpRequest(this.reader);
                String data = request.getBody();
                String path = request.getPath();
                this.response = new HttpResponse(data, path);
                String res = this.response.set();
                System.out.println(res);

                this.writer.print(res);
                this.writer.flush();
                this.close();
            } catch(Exception e){
                //e.printStackTrace();
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
    }
}
