package org.example.server;


import com.google.inject.Inject;
import lombok.*;
import org.example.exceptions.ServerRunTimeException;
import org.example.exceptions.ThreadExceptionHandler;
import org.example.server.map.MessageAdministrationThreat;
import org.example.utilities.interfaces.ILogging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.*;


public class Server{

    private final ILogging logging;
    private final InetAddress address;
    private final HashMap<String, String> userNames = new HashMap<>();

    @Inject
    Server(ILogging logging,
           InetAddress address){

        this.logging = logging;
        this.address = address;
    }

    public void start(int port) throws IOException {
        LoopHandler loopHandler = new LoopHandler(new ServerSocket(port));
        loopHandler.start();
        logging.logInfo("ok");
        logging.logInfo("the server runs on: " + address.getHostAddress());
    }

    @AllArgsConstructor
    private class LoopHandler extends Thread {

        private ServerSocket serverSocket;

        @Override
        public void run() {
            while (true) {
                try {
                    logging.logInfo("port: " + serverSocket.getLocalPort());
                    ClientHandlerSocket handlerSocket = new ClientHandlerSocket(serverSocket.accept());
                    handlerSocket.setUncaughtExceptionHandler(new ThreadExceptionHandler());
                    handlerSocket.start();
                } catch (IOException e) {
                    throw new ServerRunTimeException(e.getMessage());
                }
            }
        }
    }

    @AllArgsConstructor
    private class ClientHandlerSocket extends Thread {

        private Socket clientSocket;

        @Override
        public void run() {
            try {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String apparentUser = in.readLine();

                boolean result = false;
                for (Map.Entry<String, String> entry : userNames.entrySet()) {
                    if (Objects.equals(entry.getValue(), apparentUser)) {
                        result = true;
                        break;
                    }
                }

                if (result){
                    out.println("INVALID_USERNAME");
                    logging.logWarn("disconnecting user because his username exist");
                    throw new ServerRunTimeException("INVALID_USERNAME - Stopping thread");
                }
                out.println("VALID_USERNAME");
                userNames.put(clientSocket.getInetAddress().getHostAddress(), apparentUser);
                this.setName("client: " + clientSocket.getInetAddress().getHostAddress());

                logging.logInfo("new client from: " + clientSocket.getInetAddress().getHostAddress());

                send(apparentUser + " is here");

                while (true){
                    String inputLine = in.readLine();
                    if (Objects.equals(inputLine, "STOP_SERVER_CONNECTION_FROM_CLIENT")) {
                        logging.logWarn("client disconnected");
                        userNames.remove(clientSocket.getInetAddress().getHostAddress());
                        send(clientSocket.getInetAddress().getHostAddress() + " disconnected");
                        break;
                    }
                    send(inputLine);
                }


            } catch (IOException e) {
                throw new ServerRunTimeException(e.getMessage());
            }
        }

        private void send(String message){
            MessageAdministrationThreat msgAdminAlert = new MessageAdministrationThreat();
            msgAdminAlert.sendMessage(message);
            msgAdminAlert.start();
        }


    }


}
