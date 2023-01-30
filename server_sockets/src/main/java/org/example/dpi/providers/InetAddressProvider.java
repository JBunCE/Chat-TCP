package org.example.dpi.providers;

import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.example.exceptions.ServerRunTimeException;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressProvider implements Provider<InetAddress> {

    @Provides
    @Singleton
    @Override
    public InetAddress get() {
        try{
            return InetAddress.getLocalHost();
        }catch (UnknownHostException e) {
            throw new ServerRunTimeException(e.getMessage());
        }
    }

}
