package com.leibro.http;


import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    public static final int HTTP_REQ_LINE_PART_CNT = 3;

    public static final int HTTP_REQ_METHOD_POS = 0;

    public static final int HTTP_REQ_URI_POS = 1;

    public static final int HTTP_REQ_VERSION_POS = 2;

    public static final int HTTP_HEADER_PARTS_CNT = 2;

    public static final int HTTP_HEADER_KEY_POS = 0;

    public static final int HTTP_HEADER_VAL_POS = 1;

    public static final String HTTP_HEADER_CONTENT_LENGTH_KEY = "Content-Length";

    private HttpMethod method;

    private String uri;

    private String protocal;

    private Map<String,String> headers;

    private String body;

    public HttpMethod getMethod() {
        return method;
    }

    public HttpRequest setMethod(HttpMethod method) {
        this.method = method;
        return this;
    }

    public String getUri() {
        return uri;
    }

    public HttpRequest setUri(String uri) {
        this.uri = uri;
        return this;
    }

    public String getProtocal() {
        return protocal;
    }

    public HttpRequest setProtocal(String protocal) {
        this.protocal = protocal;
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public HttpRequest setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public String getBody() {
        return body;
    }

    public HttpRequest setBody(String body) {
        this.body = body;
        return this;
    }

    /**
     * 解析请求的Http协议
     * @param request
     * @return
     */
    public static HttpRequest getHttpRequest(String request) {
        HttpRequest httpRequest = null;
        try {
            httpRequest = new HttpRequest();
            BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(request.getBytes())));

            //处理HTTP请求行
            String httpReqLine = reader.readLine();
            if(StringUtils.isBlank(httpReqLine)) {
                return null;
            }
            httpReqLine = StringUtils.chomp(httpReqLine);
            String[] httpReqParts = httpReqLine.split(" ");
            if(httpReqParts.length != HTTP_REQ_LINE_PART_CNT) {
                return null;
            }
            HttpMethod httpMethod = HttpMethod.getHttpMethod(httpReqParts[HTTP_REQ_METHOD_POS]);
            if(httpMethod == null) {
                return null;
            }
            httpRequest.setMethod(httpMethod);
            httpRequest.setUri(httpReqParts[HTTP_REQ_URI_POS]);
            httpRequest.setProtocal(httpReqParts[HTTP_REQ_VERSION_POS]);

            //处理HTTP请求Headers
            String httpReqHeaderLine = null;
            while ((httpReqHeaderLine = reader.readLine()) != null && !(StringUtils.chomp(httpReqHeaderLine).equals(""))) {
                String[] httpHeaderParts = httpReqHeaderLine.split(":");
                if(httpHeaderParts.length != HTTP_HEADER_PARTS_CNT) {
                    return null;
                }
                String headerKey = httpHeaderParts[HTTP_HEADER_KEY_POS];
                String headerVal = httpHeaderParts[HTTP_HEADER_VAL_POS];
                if(httpRequest.getHeaders() == null) {
                    httpRequest.setHeaders(new HashMap<String, String>());
                }
                httpRequest.getHeaders().put(headerKey,headerVal);
            }

            StringBuilder bodyBuilder = new StringBuilder();
            String bodyLine = null;
            while ((bodyLine = reader.readLine()) != null) {
                bodyBuilder.append(bodyLine);
            }
            httpRequest.setBody(bodyBuilder.toString());
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return httpRequest;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "method=" + method +
                ", uri='" + uri + '\'' +
                ", protocal='" + protocal + '\'' +
                ", headers=" + headers +
                ", body='" + body + '\'' +
                '}';
    }
}
