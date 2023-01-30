package org.example.server.map;


import lombok.Setter;
import org.example.exceptions.ServerRunTimeException;
import org.example.utilities.ServerResponseSocketImpl;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Setter
public class MessageAdministrationThreat extends Thread {

    private String msg;

    public void sendMessage(String msg) {
        this.msg = msg;
    }

    @Override
    public void run(){
        getAllClients().forEach(client -> {
            try {
                ServerResponseSocketImpl responseSocket = new ServerResponseSocketImpl();
                responseSocket.startConnection(client, 3004);
                responseSocket.sendMessage(this.msg);
                responseSocket.sendMessage("STOP_SERVER_CONNECTION_FROM_SERVER");
            } catch (IOException ex) {
                throw new ServerRunTimeException(ex.getMessage());
            }
        });
    }


    private List<String> getAllClients(){
        Thread[] allThreads = new Thread[Thread.activeCount()];
        Thread.enumerate(allThreads);
        List<String> clients = new ArrayList<>();
        for (Thread t : allThreads) {
            if(t.getName().startsWith("client:")){
                clients.add(t.getName().replace("client: ", ""));
            }
        }
        return clients;
    }
}
