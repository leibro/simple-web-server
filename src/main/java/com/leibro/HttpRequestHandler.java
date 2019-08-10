package com.leibro;

import com.leibro.controller.Controller;
import com.leibro.controller.IndexController;
import com.leibro.controller.PageNotFoundController;
import com.leibro.http.HttpMethod;
import com.leibro.http.HttpRequest;
import com.leibro.http.HttpResponse;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestHandler {

    public Map<String, Controller> urlMappingControllers;


    public HttpRequestHandler() {
        this.urlMappingControllers = new HashMap<String, Controller>();
        registerDefaultMappingHandler();
    }

    public HttpResponse handleRequest(HttpRequest request) {
        Controller controller = urlMappingControllers.get(request.getUri());
        HttpResponse response = null;
        if(controller != null) {
            response = controller.doHandlerRequest(request);
        } else {
            response = urlMappingControllers.get("404").doHandlerRequest(request);
        }
        return response;
    }

    /**
     * 解析HTTP请求协议转化为HTTP请求对象
     * @param in
     * @return
     */
    public static HttpRequest parseHttpRequest(InputStream in) {
         HttpRequest httpRequest = null;
        try {
            httpRequest = new HttpRequest();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            //处理HTTP请求行
            String httpReqLine = reader.readLine();
            if(StringUtils.isBlank(httpReqLine)) {
                return null;
            }
            httpReqLine = StringUtils.chomp(httpReqLine);
            String[] httpReqParts = httpReqLine.split(" ");
            if(httpReqParts.length != HttpRequest.HTTP_REQ_LINE_PART_CNT) {
                return null;
            }
            HttpMethod httpMethod = HttpMethod.getHttpMethod(httpReqParts[HttpRequest.HTTP_REQ_METHOD_POS]);
            if(httpMethod == null) {
                return null;
            }
            httpRequest.setMethod(httpMethod);
            httpRequest.setUri(httpReqParts[HttpRequest.HTTP_REQ_URI_POS]);
            httpRequest.setProtocal(httpReqParts[HttpRequest.HTTP_REQ_VERSION_POS]);

            //处理HTTP请求Headers
            String httpReqHeaderLine = null;
            while ((httpReqHeaderLine = reader.readLine()) != null && !(StringUtils.chomp(httpReqHeaderLine).equals(""))) {
                String[] httpHeaderParts = httpReqHeaderLine.split(":",2);
                if(httpHeaderParts.length != HttpRequest.HTTP_HEADER_PARTS_CNT) {
                    return null;
                }
                String headerKey = httpHeaderParts[HttpRequest.HTTP_HEADER_KEY_POS];
                String headerVal = httpHeaderParts[HttpRequest.HTTP_HEADER_VAL_POS];
                if(httpRequest.getHeaders() == null) {
                    httpRequest.setHeaders(new HashMap<String, String>());
                }
                httpRequest.getHeaders().put(headerKey,headerVal);
            }

            //处理请求体
            if(httpRequest.getHeaders().get(HttpRequest.HTTP_HEADER_CONTENT_LENGTH_KEY) == null) {
                return httpRequest;
            }
            int contentLength = Integer.valueOf(httpRequest.getHeaders().get(HttpRequest.HTTP_HEADER_CONTENT_LENGTH_KEY));
            char[] bodyCharArr = new char[contentLength];
            reader.read(bodyCharArr);
            httpRequest.setBody(new String(bodyCharArr));
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return httpRequest;
    }

    private void registerDefaultMappingHandler() {
        this.urlMappingControllers.put("404",new PageNotFoundController());
        this.urlMappingControllers.put("/",new IndexController());
    }

}
