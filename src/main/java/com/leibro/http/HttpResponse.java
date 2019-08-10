package com.leibro.http;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private String protocal;

    private Integer code;

    private String status;

    private Map<String,String> headers;

    private String body;

    public String getProtocal() {
        return protocal;
    }

    public HttpResponse setProtocal(String protocal) {
        this.protocal = protocal;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public HttpResponse setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public HttpResponse setStatus(String status) {
        this.status = status;
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public HttpResponse setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public String getBody() {
        return body;
    }

    public HttpResponse setBody(String body) {
        this.body = body;
        if(getHeaders() == null) {
            this.headers = new HashMap<String, String>();
        }
        getHeaders().put("Content-Length",String.valueOf(body.getBytes().length));
        return this;
    }

}
