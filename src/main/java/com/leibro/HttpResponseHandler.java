package com.leibro;

import com.leibro.http.HttpResponse;

import java.util.Map;

public class HttpResponseHandler {

    public String parseHttpResponse(HttpResponse httpResponse) {
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append(httpResponse.getProtocal());
        responseBuilder.append(" ");
        responseBuilder.append(httpResponse.getCode());
        responseBuilder.append(" ");
        responseBuilder.append(httpResponse.getStatus());
        responseBuilder.append("\r\n");
        for(Map.Entry<String,String> header:httpResponse.getHeaders().entrySet()) {
            String headerKey = header.getKey();
            String headerVal = header.getValue();
            responseBuilder.append(headerKey);
            responseBuilder.append(":");
            responseBuilder.append(headerVal);
            responseBuilder.append("\r\n");
        }
        responseBuilder.append("\r\n");
        responseBuilder.append(httpResponse.getBody());
        return responseBuilder.toString();
    }
}
