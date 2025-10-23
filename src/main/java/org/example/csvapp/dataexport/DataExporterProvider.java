package org.example.csvapp.dataexport;

import org.example.csvapp.domain.ExportDataType;

public class DataExporterProvider {

  private DataExporterProvider() {}

  public static DataExporter getDataExporter(ExportDataType exportDataType) {
    return switch (exportDataType) {
      case CSV -> new CsvExporter();
      case EXCEL -> new ExcelExporter();
      case HTML -> new HtmlExporter();
    };
  }
}
