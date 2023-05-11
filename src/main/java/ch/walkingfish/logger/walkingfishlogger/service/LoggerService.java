package ch.walkingfish.logger.walkingfishlogger.service;

import java.io.IOException;
import java.util.logging.FileHandler;

import org.springframework.stereotype.Service;

import ch.walkingfish.logger.walkingfishlogger.model.log.SimpleLog;

public class LoggerService {

    public static void log(SimpleLog log) throws IOException {
        String file_name = log.getType().getLabel().toLowerCase() + ".log"; 

        FileHandler fileHandler = new FileHandler(file_name, true);

        fileHandler.setFormatter(new java.util.logging.SimpleFormatter());

        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(file_name);

        logger.addHandler(fileHandler);

        logger.info(log.toString());
    }
}
