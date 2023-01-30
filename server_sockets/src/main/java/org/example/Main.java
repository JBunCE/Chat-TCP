package org.example;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.example.dpi.Module;
import org.example.server.Server;


import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Injector injector = Guice.createInjector(new Module());
        Server server = injector.getInstance(Server.class);
        server.start(3001);
    }


}