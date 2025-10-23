package org.example.csvapp.dataimport;

import org.example.csvapp.domain.ImportDataType;

public class DataImporterProvider {

  private DataImporterProvider() {}

  public static DataImporter getDataImporter(ImportDataType importDataType) {

    return switch (importDataType) {
      case CSV -> new CsvImporter();
    };
  }
}
