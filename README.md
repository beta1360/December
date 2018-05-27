# MyWebFramework

This repository is made to practice my code skill. I don't want to make perfect web framework, but I want to make practical web framework. Therefore, This web-framework will be added some functions.

1. Routing
2. Serve static file IO
3. Rendering
4. Proxy - Load balancing, Redirection

## how to use 

### Library Synopsis
```java
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
```

1. Set port number : 
```java 
Server server = new Server(8000);
```
2. Set route : 
```java
server.route(URL, Method, Overrided Func)
```
3. Write RouteTask()
4. Run this server : 
```java
server.run();
```


## update list

### 2018-05-25

1. HTTP parser - continue ( exists some bugs)
2. Server core part - continue ( Current, multi-thread )
3. HTTP response - complete


###  2018-05-28

1. HTTP parser - complete
2. Server core part - continue ( Current, multi-thread )
3. Route - complete
4. Support JSONObject - continue
