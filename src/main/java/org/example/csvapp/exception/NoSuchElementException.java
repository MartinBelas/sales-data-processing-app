package org.example.csvapp.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NoSuchElementException extends Exception {

    private static final Logger LOGGER = Logger.getLogger(NoSuchElementException.class.getName());

    public NoSuchElementException(String vendor, String quarter) {
        if (LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.warning(String.format("Record not found, vendor: %s, quarter: %s", vendor, quarter));
        }

    }
}