# December
### An easily available Java framework

This repository is made to practice my code skill. I don't want to make perfect web framework, but I want to make practical web framework. Therefore, This web-framework will be added some functions.

1. Proxy - Load balancing, Redirection
2. TLS - Continue
3. Easy usage - continue

## how to use 

### Library Synopsis
```java
        Server server = new Server(8000);
        server.setStaticPath("C:\\Users\\user\\IdeaProjects\\MyWebFramework\\src\\test\\");

        server.route("/image.jpg", "GET", new RouteTask() {
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
                
                String res_msg = "Hello world!";
                JSONObject res = new JSONObject();
                res.put("message", temp);
                
                return response.jsonify(res);
            }
        });

        server.run();
```

1. Set port number : 
```java 
Server server = new Server(8000);
```
2. Set Server main root(Optional):
```java
server.setStaticPath("C:\\Users\\user\\IdeaProjects\\MyWebFramework\\src\\test\\");
```

3. Set route : 
```java
server.route(URL, Method, Overrided Func)
```
4. Write RouteTask()<br/>
5. Run this server : 
```java
server.run();
```


## update list

### 2018-05-25

1. HTTP parser - continue ( exists some bugs)
2. Server core part - continue ( Current, multi-thread )
3. HTTP response - complete


### 2018-05-28

1. HTTP parser - complete
2. Server core part - continue ( Current, multi-thread )
3. Route - complete
4. Support JSONObject - continue

### 2018-05-29

1. Serve static contents - complete
2. Serve dynamic generated contents - complete
3. Support JSONObject - continue(more easily)
4. @annotation - plan(more easily)

## LICENSE

- This library is based on [Apache License Version 2.0](https://www.apache.org/licenses/LICENSE-2.0)

```
Copyright 2018 KeonHee Lee

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
