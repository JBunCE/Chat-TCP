package org.example.utilities;

import org.example.vistas.alerts.ApplicationAlert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientRequestSocket{

    private PrintWriter out;
    private BufferedReader in;
    private Socket client;

    public Boolean startConnection(String ip, Integer port, String username) throws IOException {
      client = new Socket(ip, port);
      out = new PrintWriter(client.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(client.getInputStream()));
      out.println(username);

      boolean conection = false;
      try {
          String response;
          while ((response = in.readLine()) != null) {
              if (response.equals("INVALID_USERNAME")) {
                  ApplicationAlert applicationAlert = new ApplicationAlert();
                  applicationAlert.setVisible(true);
                  applicationAlert.setSize(500, 200);
                  applicationAlert.setTittle(response);
                  applicationAlert.setDesc("El nombre de usuario que ha ingresado ya existe");
                  break;
              }
              if (response.equals("VALID_USERNAME")) {
                  conection = true;
                  break;
              }
          }
      } catch (IOException e) {
          // Manejar excepción de E/S
      }
      return conection;
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
