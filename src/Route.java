package December;

import java.util.ArrayList;

public class Route {
    private ArrayList<RouteTable> router;

    public Route(){
        router = new ArrayList<RouteTable>();
    }

    public boolean isRegistedRoutePath(String path, String method) {
        for(int i=0; i<router.size(); i++){
            if(router.get(i).getPath().equals(path)
                    && router.get(i).getMethod().equals(method))
                return true;
        }
        return false;
    }

    private class RouteTable{
        private String path;
        private String method;
        private RouteTask route_task;

        public RouteTable(String path, String method, RouteTask route_task){
            this.path = path;
            this.method = method;
            this.route_task = route_task;
        }

        public String getMethod() { return this.method; }
        public String getPath() { return this.path;}
        public RouteTask getRouteTask() {return this.route_task; }
    }

    public void setRoute(String path, String method, RouteTask route_task){
        this.router.add(new RouteTable(path, method, route_task));
    }

    public RouteTask getRouteTask(String path, String method){
        for(int i=0; i<this.router.size(); i++)
            if(this.router.get(i).getPath().equals(path)
                    && this.router.get(i).getMethod().equals(method))
                return this.router.get(i).getRouteTask();

        return null;
    }
}
