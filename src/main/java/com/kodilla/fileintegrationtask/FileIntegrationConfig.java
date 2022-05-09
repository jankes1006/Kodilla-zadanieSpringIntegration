package com.kodilla.fileintegrationtask;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileNameGenerator;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;

import java.io.File;

@Configuration
public class FileIntegrationConfig {

    private final String INPUT_DIRECTORY = "data/input";
    private final String OUTPUT_DIRECTORY = "data/output";
    private final int DELAY = 200;

    @Bean
    IntegrationFlow integrationFlow(FileReadingMessageSource fileReadingMessageSource, FileTransform fileTransform, FileWritingMessageHandler outputFileHandler){
        return IntegrationFlows.from(fileReadingMessageSource, config-> config.poller(Pollers.fixedDelay(DELAY)))
                .transform(fileTransform,"transformFile")
                .handle(outputFileHandler)
                .get();
    }

    @Bean
    FileReadingMessageSource fileReadingMessageSource(){
        FileReadingMessageSource source = new FileReadingMessageSource();
        source.setDirectory(new File(INPUT_DIRECTORY));
        return source;
    }

    @Bean
    FileWritingMessageHandler fileWritingMessageHandler(FileNameGenerator defaultGenerator){
        File directory = new File(OUTPUT_DIRECTORY);
        FileWritingMessageHandler handler = new FileWritingMessageHandler(directory);
        handler.setFileNameGenerator(defaultGenerator);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setExpectReply(false);
        return handler;
    }

    @Bean
    FileNameGenerator fileNameGenerator(){
        return new DefaultGenerator();
    }

    @Bean
    FileTransform fileTransform(){
        return new FileTransform();
    }
}
