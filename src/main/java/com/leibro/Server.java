package com.leibro;

import com.leibro.http.HttpRequest;
import com.leibro.http.HttpResponse;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(80);
            HttpRequestHandler requestHandler = new HttpRequestHandler();
            HttpResponseHandler responseHandler = new HttpResponseHandler();
            for(;;) {
                Socket socket = serverSocket.accept();
                HttpRequest httpRequest = requestHandler.parseHttpRequest(socket.getInputStream());
                if(httpRequest != null) {
                    HttpResponse httpResponse = requestHandler.handleRequest(httpRequest);
                    OutputStream out = socket.getOutputStream();
                    out.write(responseHandler.parseHttpResponse(httpResponse).getBytes("utf-8"));
                    out.flush();
                }
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
