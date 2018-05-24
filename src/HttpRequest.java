package December;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.util.HashMap;

public class HttpRequest {
    private String method;
    private HttpRequestUrl url;
    private String version;
    private HashMap<String, String> headers;
    private StringBuffer body;

    public HttpRequest(BufferedReader br){
        this.body = new StringBuffer();
        this.headers = new HashMap<>();
        parseHttpRequest(br);
    }

    private void parseHttpRequest(BufferedReader br){
        try {
            String request_line = br.readLine();
            System.out.println(request_line);
            readRequestLine(request_line);

            String header_line = br.readLine();
            while(header_line.length() > 0){
                readHeaderLine(header_line);
                header_line = br.readLine();
            }
            //String body_line;
            //while((body_line = br.readLine())!= null){
            //    appendBodyLine(body_line);
            //}
            System.out.println("body:");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readRequestLine(String line){
        String[] tokens = line.split("\\s");
        this.method = tokens[0];
        url = new HttpRequestUrl(tokens[1]);
        version = tokens[2];
    }

    private void readHeaderLine(String line){
        int cutline = line.indexOf(':');
        headers.put(line.substring(cutline-1).trim(), line.substring(cutline+ 1, line.length()).trim());
    }

    private void appendBodyLine(String line){
        body.append(line).append("\r\n");
    }

    public String getMethod(){ return this.method; }
    public String getUrlQSValue(String key) { return url.getQSArg(key); }
    public String getVersion() { return this.version; }
    public String getBody() { return this.body.toString(); }
    public String getPath() { return this.url.getPath(); }
}
