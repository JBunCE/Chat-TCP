package org.example.utilities;

import org.example.utilities.interfaces.IServerResponseSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerResponseSocketImpl implements IServerResponseSocket {

    private PrintWriter out;
    private BufferedReader in;
    private Socket client;

    public void startConnection(String ip, Integer port) throws IOException {
        client = new Socket(ip, port);
        out = new PrintWriter(client.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
    }

    public void sendMessage(String msg) throws IOException {
        out.println(msg);
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        client.close();
    }


}
