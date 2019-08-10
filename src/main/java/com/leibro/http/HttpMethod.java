package com.leibro.http;

public enum HttpMethod {
    GET("GET"),POST("POST");

    private String method;

    HttpMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public HttpMethod setMethod(String method) {
        this.method = method;
        return this;
    }

    public static HttpMethod getHttpMethod(String method) {
        for(HttpMethod httpMethod:HttpMethod.values()) {
            if(httpMethod.getMethod().equals(method)) {
                return httpMethod;
            }
        }
        return null;
    }


}
