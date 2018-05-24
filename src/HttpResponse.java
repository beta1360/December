package December;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class HttpResponse {
    private String path;
    private int status_code;
    private String status_info;
    private String version;
    private HashMap<String, String> headers;
    private String body;
    private Utility utility;

    public HttpResponse(String body, String path){
        this.path = path;
        this.status_code = 200;
        this.utility = new Utility();
        this.headers = new HashMap<String, String>();
        this.version = "HTTP/1.1";
        this.body = body;
    }

    public String set(){
        StringBuffer response = new StringBuffer();
        testData();
        this.putStatusCode(this.status_code);
        this.utility.getContentInfo(this.path);
        this.setDefaultHeaders();
        response.append(this.version).append(" ").append(this.status_code)
                .append(" ").append(this.status_info).append("\r\n");

        Set keys = headers.keySet();
        for(Iterator iterator = keys.iterator();iterator.hasNext();){
            String key = (String) iterator.next();
            String value = (String)this.headers.get(key);
            response.append(key).append(": ").append(value).append("\r\n");
            System.out.println(key + ": " + value);
        }
        response.append("\r\n\r\n").append(this.body);
        return response.toString();
    }

    public void setDefaultHeaders(){
        if(headers.get("Content-type") == null) this.setMIMEType(this.path);
        System.out.println("hello2");
        if(headers.get("Content-Length") == null) getContentBody(this.body);
        System.out.println("hello3");
        getDate();
        getLastModifiedDate();
    }

    public void putHeader(String key, String value){
        this.headers.put(key, value);
    }

    public void putStatusCode(int status_code) {
        try {
            this.status_code = status_code;
            this.status_info = utility.getStatusInfo(this.status_code);
        } catch (Exception e){
            System.out.println("Dont support this status :" + this.status_code + " - " + this.status_info);
        }
    }

    private void setMIMEType(String path){
        if(headers.get("Content-type") == null)
            headers.put("Content-type",this.utility.getContentInfo(path) +";charset=utf-8");
    }

    public void getContentBody(String body){
        this.body = body;
        headers.put("Content-Length", Integer.toString(this.body.length()));
    }

    private void getDate(){
        Date now = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss zzz");
        headers.put("Date",ft.format(now));
    }

    private void getLastModifiedDate(){
        Date now = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss zzz");
        headers.put("Last Modified",ft.format(now));
    }

    private void testData(){
        StringBuilder build = new StringBuilder();
        build.append("<HTML><HEAD>");
        build.append("<TITLE>KeonHee WebServer</TITLE>");
        build.append("</HEAD><BODY>");
        build.append("hello World!!");
        build.append("</BODY></HTML>");
        this.body = build.toString();

        putHeader("Content-type",utility.getContentInfo("html"));
    }
}
