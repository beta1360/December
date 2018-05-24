package December;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

public class HttpRequestUrl {
    private String path;
    private HashMap<String, String> arguements;
    private String fragment;

    public HttpRequestUrl(String url){
        this.arguements = new HashMap<>();
        this.parseUrl(url);
    }

    private void parseUrl(String url){
        String[] path_args = url.split("\\?");
        this.path = path_args[0];

        parseFrament(url);
        if(path_args.length <= 1) return;

        String[] args = path_args[1].split("&");

        for(int i = 0; i < args.length; i++){
            String[] arg = args[i].split("=");
            try {
                arguements.put(URLDecoder.decode(arg[0], "utf-8"), URLDecoder.decode(arg[1], "utf-8"));
            } catch (UnsupportedEncodingException e) {
                System.out.printf("-Dont decode this : %s\n", arg[0]);
            }
        }
    }

    private void parseFrament(String url){
        try { this.fragment = url.split("#")[1]; }
        catch(ArrayIndexOutOfBoundsException e){ this.fragment = null; }
    }

    public String getPath(){ return this.path; }
    public String getFragment() { return this.fragment; }
    String getQSArg(String key){ return arguements.get(key); }
}
