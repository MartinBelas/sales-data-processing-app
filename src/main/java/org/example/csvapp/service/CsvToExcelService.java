package org.example.csvapp.service;

import org.example.csvapp.domain.ExportDataType;
import org.example.csvapp.domain.ImportDataType;

public class CsvToExcelService extends BaseDataService implements DataService {

  public CsvToExcelService(String resourcePath) {
    super(ImportDataType.CSV, resourcePath, ExportDataType.EXCEL);
  }
}
