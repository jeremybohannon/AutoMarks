package com.automarks.storage.storageService;

public class Routes {
    public static String getRoute(String service){

        String route = null;
        String port = "";

        switch (service){
            case "Assignment":
                route = System.getenv("ASSIGNMENT_HOST");
                port = "8060";
                break;
            case "Storage":
                route = System.getenv("STORAGE_HOST");
                port = "8090";
                break;
            case "Grader":
                route = System.getenv("GRADER_HOST");
                port = "3000";
                break;
            case "Gateway":
                route = System.getenv("GATEWAY_HOST");
                port = "8070";
                break;
        }

        if(route == null){
            route = "http://localhost:" + port;
        }
        return route;
    }
}
