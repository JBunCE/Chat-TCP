package org.example.utilities;

import lombok.extern.slf4j.Slf4j;
import org.example.utilities.interfaces.ILogging;

@Slf4j
public class LoggingImpl implements ILogging {

    @Override
    public void logWarn(String message) { log.warn(message); }

    @Override
    public void logDebug(String message) {
        log.debug(message);
    }

    @Override
    public void logInfo(String message) { log.info(message); }

    @Override
    public void logError(String message) { log.error(message); }

}
