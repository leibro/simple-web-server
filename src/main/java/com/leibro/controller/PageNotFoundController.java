package com.leibro.controller;

import com.leibro.http.HttpRequest;
import com.leibro.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class PageNotFoundController implements Controller {
    public HttpResponse doHandlerRequest(HttpRequest request) {
        HttpResponse response = new HttpResponse();
        response.setProtocal("HTTP/1.1");
        response.setCode(404);
        response.setStatus("Not Found");
        Map<String,String> headers = new HashMap<String, String>();
        headers.put("Content-Type","text/plain");
        response.setHeaders(headers);
        String body = "404 not found";
        response.setBody(body);
        return response;
    }
}
