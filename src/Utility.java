package December;

import java.util.HashMap;

public class Utility {
    private HashMap<Integer, String> status_codes;
    private HashMap<String, String> data_types;
    private final String NONE_SUPPROTED_TYPE = "text/plain";

    public Utility(){
        status_codes = new HashMap<Integer, String>();
        data_types = new HashMap<String, String>();

        status_codes.put(100, "Continue");
        status_codes.put(101, "Switching Protocols");
        status_codes.put(102, "Processing");
        status_codes.put(103, "Early Hints");

        status_codes.put(200, "OK");
        status_codes.put(201, "Created");
        status_codes.put(202, "Accepted");
        status_codes.put(203, "Non-Authoritative Information");
        status_codes.put(204, "No Content");
        status_codes.put(205, "Reset Content");
        status_codes.put(206, "Partial Content");
        status_codes.put(207, "Multi-Status");
        status_codes.put(208, "Already Reported");
        status_codes.put(226, "IM Used");

        status_codes.put(300, "Multiple Choices");
        status_codes.put(301, "Moved Permantly");
        status_codes.put(302, "Found");
        status_codes.put(303, "See Other");
        status_codes.put(304, "Not Modified");
        status_codes.put(305, "Use Proxy");
        status_codes.put(306, "Switch Proxy");
        status_codes.put(307, "Temporary Redirect");
        status_codes.put(308, "Permanent Redirect");

        status_codes.put(400, "Bad Request");
        status_codes.put(401, "Unauthorized");
        status_codes.put(402, "Payment Required");
        status_codes.put(403, "Forbidden");
        status_codes.put(404, "Not Found");
        status_codes.put(405, "Method Not Allowed");
        status_codes.put(406, "Not Acceptable");
        status_codes.put(407, "Proxy Authentication Required");
        status_codes.put(408, "Request Timeout");
        status_codes.put(409, "Conflict");
        status_codes.put(410, "Gone");
        status_codes.put(411, "Length Required");
        status_codes.put(412, "Precondition Failed");
        status_codes.put(413, "Payload Too Large");
        status_codes.put(414, "URI Too Long");
        status_codes.put(415, "Unsupported Media Type");
        status_codes.put(416, "Range Not Satisfiable");
        status_codes.put(417, "Expectation Failed");
        status_codes.put(418, "I\'m a teapot");
        status_codes.put(421, "Misdirected Request");
        status_codes.put(422, "Unprocessable Entity");
        status_codes.put(423, "Locked");
        status_codes.put(424, "Failed Dependency");
        status_codes.put(426, "Upgrade Required");
        status_codes.put(428, "Precondition Required");
        status_codes.put(429, "Too Many Requests");
        status_codes.put(431, "Bad Request");
        status_codes.put(451, "Bad Request");

        status_codes.put(500, "Internal Server Error");
        status_codes.put(501, "Not Implemented");
        status_codes.put(502, "Bad Gateway");
        status_codes.put(503, "Service Unavailable");
        status_codes.put(504, "Gateway Timeout");
        status_codes.put(505, "HTTP Version Not Supported");
        status_codes.put(506, "Variant Also Negotiates");
        status_codes.put(507, "Insufficient Storage");
        status_codes.put(508, "Loop Detected");
        status_codes.put(510, "Not Extended");
        status_codes.put(511, "Network Authentication Required");

        data_types.put("aac", "audio/aac");
        data_types.put("abw","application/x-abiword");
        data_types.put("arc","application/octet-stream");
        data_types.put("avi","video/x-msvideo");
        data_types.put("azw","application/vnd.amazon.ebook");
        data_types.put("bin","application/octet-stream");
        data_types.put("bz","application/x-bzip");
        data_types.put("bz2","application/x-bzip2");
        data_types.put("csh","application/x-csh");
        data_types.put("css","text/css");
        data_types.put("csv","text/csv");
        data_types.put("doc","application/msword");
        data_types.put("docx","application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        data_types.put("eot","application/vnd.ms-fontobject");
        data_types.put("epub","application/epub+zip");
        data_types.put("es","application/ecmascript");
        data_types.put("gif","image/gif");
        data_types.put("htm","text/html");
        data_types.put("html","text/html");
        data_types.put("ico","image/x-icon");
        data_types.put("ics","text/calendar");
        data_types.put("jar","application/java-archive");
        data_types.put("jpg","image/jpeg");
        data_types.put("jpeg","image/jpeg");
        data_types.put("js","application/javascript");
        data_types.put("json","application/json");
        data_types.put("mid","audio/midi");
        data_types.put("midi","audio/midi");
        data_types.put("mpeg","video/mpeg");
        data_types.put("mpkg","application/vnd.apple.installer+xml");
        data_types.put("odp","application/vnd.oasis.opendocument.presentation");
        data_types.put("ods","application/vnd.oasis.opendocument.spreadsheet");
        data_types.put("odt","application/vnd.oasis.opendocument.text");
        data_types.put("oga","audio/ogg");
        data_types.put("ogv","video/ogg");
        data_types.put("ogx","application/ogg");
        data_types.put("otf","font/otf");
        data_types.put("png","image/png");
        data_types.put("pdf","application/pdf");
        data_types.put("ppt","application/vnd.ms-powerpoint");
        data_types.put("pptx","application/vnd.openxmlformats-officedocument.presentationml.presentation");
        data_types.put("rar","application/x-rar-compressed");
        data_types.put("rtf","application/rtf");
        data_types.put("sh","application/x-sh");
        data_types.put("svg","image/svg+xml");
        data_types.put("swf","application/x-shockwave-flash");
        data_types.put("tar","application/x-tar");
        data_types.put("tif","image/tiff");
        data_types.put("tiff","image/tiff");
        data_types.put("ts","application/typescript");
        data_types.put("ttf","font/ttf");
        data_types.put("vsd","application/vnd.util/visio");
        data_types.put("wav","audio/wav");
        data_types.put("weba","audio/webm");
        data_types.put("webm","video/webm");
        data_types.put("webp","image/webp");
        data_types.put("woff","font/woff");
        data_types.put("woff2","font/woff2");
        data_types.put("whml","application/xhtml+xml");
        data_types.put("xls","application/vnd.ms-excel");
        data_types.put("xlsx","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        data_types.put("xml","application/xml");
        data_types.put("xul","application/vnd.mozilla.xul+xml");
        data_types.put("zip","application/zip");
        data_types.put("3gp","video/3gpp");
        data_types.put("3g2","video/3gpp2");
        data_types.put("7z","application/x-7z-compressed");
    }

    public String getStatusInfo(int key){ return status_codes.get(key); }

    public String getContentInfo(String path) {
        String[] path_token = path.split("\\.");
        String extension = path_token[path_token.length - 1];

        if (this.data_types.get(extension) == null)
            return NONE_SUPPROTED_TYPE;
        else
            return data_types.get(extension);
    }

    public static final int TEXT = 0;
    public static final int APPLICATION = 1;
    public static final int IMAGE = 2;
    public static final int VIDEO = 3;
    public static final int FONT = 4;

    public int getType(String path){
        String info = this.getContentInfo(path);
        if(info.contains("application"))    return this.APPLICATION;
        else if(info.contains("image"))     return this.IMAGE;
        else if(info.contains("video"))     return this.VIDEO;
        else if(info.contains("font"))      return this.FONT;
        else
            return this.TEXT;
    }
}
