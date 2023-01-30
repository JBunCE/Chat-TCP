package org.example.configurations.logbackFilters;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class LogErrorFIlter extends Filter<ILoggingEvent> {
    @Override
    public FilterReply decide(ILoggingEvent iLoggingEvent) {
        return (iLoggingEvent.getLevel() == Level.ERROR) ? FilterReply.ACCEPT : FilterReply.DENY;
    }
}
