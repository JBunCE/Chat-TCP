package org.example.dpi;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import org.example.dpi.providers.InetAddressProvider;
import org.example.utilities.LoggingImpl;
import org.example.utilities.ServerResponseSocketImpl;
import org.example.utilities.interfaces.ILogging;
import org.example.utilities.interfaces.IServerResponseSocket;

import java.net.InetAddress;


public class Module extends AbstractModule {

    @Override
    protected void configure(){
        bind(ILogging.class).to(LoggingImpl.class);
        bind(InetAddress.class).toProvider(InetAddressProvider.class).in(Singleton.class);
        bind(IServerResponseSocket.class).to(ServerResponseSocketImpl.class);
    }
}
