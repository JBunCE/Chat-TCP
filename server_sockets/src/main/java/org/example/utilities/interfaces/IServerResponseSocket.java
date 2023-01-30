package org.example.utilities.interfaces;

import java.io.IOException;

public interface IServerResponseSocket {
    void startConnection(String ip, Integer port) throws IOException;
    void sendMessage(String msg) throws IOException;
    void stopConnection() throws IOException;
}
