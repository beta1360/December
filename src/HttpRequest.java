package December;

import java.io.*;
import java.util.HashMap;

public class HttpRequest {
    private String method;
    private HttpRequestUrl url;
    private String version;
    private HashMap<String, String> headers;
    private StringBuffer body;

    public HttpRequest(InputStream reader){
        this.body = new StringBuffer();
        this.headers = new HashMap<>();
        parseHttpRequest(reader);
    }

    private void parseHttpRequest(InputStream reader) {
        String request_stream = this.getRequestStream(reader);
        String[] hdr_body = request_stream.split("\r\n\r\n");
        this.parseHeaders(hdr_body[0]);
        this.appendBody(hdr_body);
    }

    private String getRequestStream(InputStream reader){
        try {
            StringBuffer request = new StringBuffer();
            byte[] b = new byte[4096];
            for (int n; (n = reader.read(b)) != -1;) {
                request.append(new String(b, 0, n));
                if(reader.available() <= 0) break;
            }
            return request.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void parseHeaders(String header_stream){
        String[] header_line = header_stream.split("\r\n");

        String request_line = header_line[0];
        readRequestLine(request_line);

        for(int i=1; i<header_line.length; i++)
            readHeaderLine(header_line[i]);
    }

    private void readRequestLine(String line){
        String[] tokens = line.split("\\s");
        this.method = tokens[0];
        url = new HttpRequestUrl(tokens[1]);
        version = tokens[2];
    }

    private void readHeaderLine(String line){
        int cutline = line.indexOf(':');
        String key = line.substring(0, cutline).trim();
        String value = line.substring(cutline + 1, line.length()).trim();
        headers.put(key, value);
    }

    private void appendBody(String[] hdr_body){
        if((hdr_body.length > 1) && (headers.get("Content-Length") != null))
            for(int i=1; i<hdr_body.length; i++)
                this.body.append(hdr_body[i]);
    }

    public String getMethod(){ return this.method; }
    public String getUrlQSValue(String key) { return url.getQSArg(key); }
    public String getVersion() { return this.version; }
    public String getBody() { return this.body.toString(); }
    public String getPath() { return this.url.getPath(); }
}