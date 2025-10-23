package org.example.csvapp.exception;

public class ExportDataException extends Exception {

  public ExportDataException(String path, Exception e) {
    super(String.format("Error saving data to file: %s", path), e);
  }
}

