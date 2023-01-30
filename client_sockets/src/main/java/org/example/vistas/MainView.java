package org.example.vistas;

import lombok.AllArgsConstructor;
import org.example.exceptions.ClientRunTimeException;
import org.example.utilities.ClientRequestSocket;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class MainView extends JFrame{
    private JPanel panel1;
    private JButton send;
    private JTextField messageField;
    private JTextField userNameField;
    private JTextPane textPane1;
    private JTextField ipHost;
    private JButton exitButton;
    private JButton startConectionButton;
    private JLabel conectionStatus;
    private JButton closeConectionButton;

    private ClientRequestSocket client;
    private Boolean serverStatus;

    public MainView() throws IOException {
        setContentPane(panel1);
        setVisible(true);
        setSize(500, 500);
        closeConectionButton.setBackground(Color.GRAY);
        closeConectionButton.setEnabled(false);

        LoopHandler loopHandler = new LoopHandler(new ServerSocket(3004));
        loopHandler.start();

        send.addActionListener(e -> {
            try {
                if (!messageField.getText().equals("STOP_SERVER_CONNECTION_FROM_CLIENT")){
                    sendToTheServer(userNameField.getText() + "> " + messageField.getText());
                }
            } catch (IOException ex) {
                throw new ClientRunTimeException(ex.getMessage());
            }
        });

        exitButton.addActionListener(e -> {
            try {
                if(Boolean.TRUE.equals(serverStatus)){
                    sendToTheServer("STOP_SERVER_CONNECTION_FROM_CLIENT");
                    startConectionButton.setBackground(null);
                    startConectionButton.setEnabled(true);
                    userNameField.setEnabled(true);
                    ipHost.setEnabled(true);
                    serverStatus = false;
                }
            } catch (IOException ex) {
                throw new ClientRunTimeException(ex.getMessage());
            }
            System.exit(0);
        });

        startConectionButton.addActionListener(e -> {
            try {
                setup();
                if(Boolean.TRUE.equals(serverStatus)){
                    startConectionButton.setBackground(Color.GRAY);
                    startConectionButton.setEnabled(false);
                    userNameField.setEnabled(false);
                    ipHost.setEnabled(false);
                    closeConectionButton.setEnabled(true);
                    closeConectionButton.setBackground(null);
                }
            } catch (IOException ex) {
                throw new ClientRunTimeException(ex.getMessage());
            }
        });

        closeConectionButton.addActionListener(e -> {
            try {
                sendToTheServer("STOP_SERVER_CONNECTION_FROM_CLIENT");
                startConectionButton.setBackground(null);
                startConectionButton.setEnabled(true);
                userNameField.setEnabled(true);
                ipHost.setEnabled(true);
                serverStatus = false;
            } catch (IOException ex) {
                throw new ClientRunTimeException(ex.getMessage());
            }
        });
    }


    public void setup() throws IOException {
        client = new ClientRequestSocket();
        Boolean status = client.startConnection(ipHost.getText(), 3001, userNameField.getText());
        conectionStatus.setText("connection status: " + status);
        serverStatus = status;
    }


    public void tearDown() throws IOException {
        client.stopConnection();
    }


    public void sendToTheServer(String msg) throws IOException {
        client.sendMessage(msg);
    }

    @AllArgsConstructor
    private class LoopHandler extends Thread {
        private ServerSocket serverSocket;

        @Override
        public void run() {
            while (true) {
                try {
                    ClientHandlerSocket handlerSocket = new ClientHandlerSocket(serverSocket.accept());
                    handlerSocket.start();
                } catch (IOException e) {
                    throw new ClientRunTimeException(e.getMessage());
                }
            }
        }
    }

    @AllArgsConstructor
    private class ClientHandlerSocket extends Thread {

        private Socket clientSocket;

        @Override
        public void run() {
            try{
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                while (true){
                    String inputLine = in.readLine();
                    if (Objects.equals(inputLine, "STOP_SERVER_CONNECTION_FROM_SERVER")) {
                        break;
                    }
                    if(Objects.equals(textPane1.getText(), "")) {
                        textPane1.setText(textPane1.getText() + inputLine);
                        continue;
                    }
                    inputLine = "\n" + inputLine;
                    textPane1.setText(textPane1.getText() + inputLine);
                }
            }catch (IOException e){
                throw new ClientRunTimeException(e.getMessage());
            }
        }

    }


}
