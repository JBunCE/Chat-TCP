package org.example.exceptions;

import org.example.utilities.LoggingImpl;

public class ServerRunTimeException extends RuntimeException{
    public ServerRunTimeException(String message){
        LoggingImpl logging = new LoggingImpl();
        logging.logError(message);
    }
}
