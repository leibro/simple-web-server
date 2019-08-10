package com.leibro;

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
            for(;;) {
                Socket socket = serverSocket.accept();
                HttpRequest httpRequest = HttpRequestHandler.parseHttpRequest(socket.getInputStream());
                OutputStream out = socket.getOutputStream();
                if(httpRequest != null) {
                    out.write(httpRequest.toString().getBytes());
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
