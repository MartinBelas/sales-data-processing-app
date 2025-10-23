package org.example.csvapp.service;

import org.example.csvapp.domain.ExportDataType;
import org.example.csvapp.domain.ImportDataType;

public class CsvToHtmlService extends BaseDataService implements DataService {

  public CsvToHtmlService(String resourcePath) {
    super(ImportDataType.CSV, resourcePath, ExportDataType.HTML);
  }
}
