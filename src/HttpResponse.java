package December;
import org.json.simple.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

public class HttpResponse {
    private String path;
    private int status_code;
    private String status_info;
    private String content_info;
    private String version;
    private HashMap<String, String> headers;
    private Utility utility;
    private String body;

    public HttpResponse(String path){
        this.path = path;
        this.status_code = 200;
        this.utility = new Utility();
        this.headers = new HashMap<String, String>();
        this.version = "HTTP/1.1";
    }

    public String set(){
        StringBuffer response = new StringBuffer();
        this.putStatusCode(this.status_code);
        this.content_info = this.utility.getContentInfo(this.path);
        this.fillHeaders(response);

        if(this.body != null)
            response.append("\r\n").append(this.body);
        else
            response.append("\r\n");
        return response.toString();
    }

    private void fillHeaders(StringBuffer response){
        this.setDefaultHeaders();
        response.append(this.version).append(" ").append(this.status_code)
                .append(" ").append(this.status_info).append("\r\n");

        Set keys = headers.keySet();
        for(Iterator iterator = keys.iterator();iterator.hasNext();){
            String key = (String) iterator.next();
            String value = (String)this.headers.get(key);
            response.append(key).append(": ").append(value).append("\r\n");
        }
    }

    private void setDefaultHeaders(){
        if(headers.get("Content-Type") == null) this.setMIMEType();
        if(headers.get("Content-Length") == null) getDefaultContentLength(this.body);
        if(headers.get("Server") == null) this.putHeader("Server","December/0.0.3");
        getDefaultDate();
        getDefaultLastModifiedDate();
    }

    public void putStatusCode(int status_code) {
        try {
            this.status_code = status_code;
            this.status_info = utility.getStatusInfo(this.status_code);
        } catch (Exception e){
            System.out.println("Dont support this status :" + this.status_code + " - " + this.status_info);
        }
    }

    public void setMIMEType(){
        if(headers.get("Content-Type") == null)
            headers.put("Content-Type", this.content_info);
    }

    private void getDefaultContentLength(String body){
        this.body = body;
        if(!(this.body == null))
            headers.put("Content-Length", Integer.toString(this.body.length()));
    }

    private void getDefaultDate(){
        Date now = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss zzz", Locale.ENGLISH);
        headers.put("Date",ft.format(now));
    }

    private void getDefaultLastModifiedDate(){
        Date now = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss zzz");
        headers.put("Last Modified",ft.format(now));
    }

    public String jsonify(JSONObject json){
        this.putHeader("Content-Type","application/json;charset=utf-8");
        return json.toString();
    }

    public void setBody(String body){ this.body = body; }
    public void putHeader(String key, String value){
        this.headers.put(key, value);
    }
    public int getStatus_code(){ return this.status_code; }
    public String getStatus_info() { return this.status_info; }
    public String getDate(){ return this.headers.get("Date"); }
}
