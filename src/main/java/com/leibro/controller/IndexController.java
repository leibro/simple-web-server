package com.leibro.controller;

import com.leibro.http.HttpRequest;
import com.leibro.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class IndexController implements Controller {

    public HttpResponse doHandlerRequest(HttpRequest request) {
        HttpResponse response = new HttpResponse();
        response.setProtocal("HTTP/1.1");
        response.setCode(200);
        response.setStatus("OK");
        Map<String,String> headers = new HashMap<String, String>();
        headers.put("Content-Type","text/plain");
        response.setHeaders(headers);
        String body = "Hello,This is index";
        response.setBody(body);
        return response;
    }
}
