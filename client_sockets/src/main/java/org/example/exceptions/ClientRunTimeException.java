package org.example.exceptions;

import org.example.vistas.alerts.ApplicationAlert;

public class ClientRunTimeException extends RuntimeException{
    public ClientRunTimeException(String msg){
        ApplicationAlert applicationAlert = new ApplicationAlert();
        applicationAlert.setTittle("Run time error");
        applicationAlert.setDesc(msg);
    }
}
