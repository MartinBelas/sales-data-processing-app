package org.example.csvapp.service;

import static org.example.csvapp.App.DEFAULT_RESOURCE_PATH;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.example.csvapp.domain.SortBy;
import org.junit.jupiter.api.Test;

class CsvToExcelServiceTest {

  final String resourcePath = DEFAULT_RESOURCE_PATH;
  final String resultPath = Paths.get(resourcePath).getParent().resolve("report.html").toString();
  final DataService service = new CsvToExcelService(resourcePath); // under test

  @Test
  void getReportRecord_should_throwUnsupportedOperationException_when_itemInFileDoesNotExist() {

    assertThrows(
        UnsupportedOperationException.class,
        () -> {
          Path result = service.generateReport("2010 Q4", SortBy.VENDOR, resultPath);
        });
  }
}
