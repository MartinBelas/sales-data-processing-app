package org.example.csvapp.exception;

public class ImportDataException extends Exception {

  public ImportDataException(String path, Exception e) {
    super(String.format("Error importing data from file: %s", path), e);
  }
}

