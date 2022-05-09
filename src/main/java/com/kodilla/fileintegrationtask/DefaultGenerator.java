package com.kodilla.fileintegrationtask;

import org.springframework.integration.file.DefaultFileNameGenerator;
import org.springframework.messaging.Message;

public class DefaultGenerator extends DefaultFileNameGenerator {
    private final String HISTORY_FILE_NAME = "history.txt";
    public DefaultGenerator() {
        super();
    }

    @Override
    public String generateFileName(Message<?> message) {
        return HISTORY_FILE_NAME;
    }
}