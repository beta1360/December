package test;
import December.HttpRequest;
import December.HttpResponse;
import December.RouteTask;
import December.Server;
import org.json.simple.JSONObject;

import java.io.IOException;

public class HttpTester {
    public static void main(String[] args) throws IOException {
        Server server = new Server(8000);

        server.route("/", "GET", new RouteTask() {
            @Override
            public String task(HttpRequest request, HttpResponse response) {
                JSONObject json = new JSONObject();
                json.put("hello", "world!");
                String res = response.jsonify(json);
                return res;
            }
        });

        server.route("/home", "GET", new RouteTask() {
            @Override
            public String task(HttpRequest request, HttpResponse response) {
                JSONObject json = new JSONObject();
                json.put("hello", "world! get!");
                String res = response.jsonify(json);
                return res;
            }
        });
        server.run();
    }
}
