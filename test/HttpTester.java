package test;
import December.HttpRequest;
import December.HttpResponse;
import December.RouteTask;
import December.Server;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class HttpTester {
    public static void main(String[] args) throws IOException {
        Server server = new Server(8000);
        server.setStaticPath("C:\\Users\\user\\IdeaProjects\\MyWebFramework\\src\\test\\");

        server.route("/image1.jpg", "GET", new RouteTask() {
            @Override
            public String task(HttpRequest request, HttpResponse response) {
                return null;
            }
        });

        server.route("/message", "POST", new RouteTask() {
            @Override
            public String task(HttpRequest request, HttpResponse response) {
                JSONObject req = request.json();
                String message = (String)req.get("content");

                String res_msg ;
                if(message.equals("hello"))
                    res_msg = "hello:)";
                else res_msg = "hungry :(";

                JSONObject res = new JSONObject();
                JSONObject temp = new JSONObject();
                temp.put("text", res_msg);
                res.put("message", temp);

                return response.jsonify(res);
            }
        });

        server.run();
    }
}
