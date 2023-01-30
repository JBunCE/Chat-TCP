package org.example.exceptions;

import org.example.utilities.LoggingImpl;


public class ThreadExceptionHandler implements Thread.UncaughtExceptionHandler{

    LoggingImpl logging = new LoggingImpl();

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        logging.logError(t.getName() + " the thread was stopped");
    }
}
