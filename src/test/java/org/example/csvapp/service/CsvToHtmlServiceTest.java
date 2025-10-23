package org.example.csvapp.service;

import org.example.csvapp.domain.ReportRecord;
import org.example.csvapp.exception.NoSuchElementException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.example.csvapp.App.DEFAULT_RESOURCE_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CsvToHtmlServiceTest {

  final String resourcePath = DEFAULT_RESOURCE_PATH;
  final DataService service = new CsvToHtmlService(resourcePath); // under test

  @Nested
  class getRowNumber {

    @Test
    void getRowNumber_should_Succeed() {

      final int expected = 5; // according to resources/data.csv file

      int result = service.getRowNumber("Fujitsu Siemens", "2010 Q4", null);
      assertEquals(expected, result);
    }

    @Test
    void getRowNumber_should_returnMinusOne_when_itemInFileDoesNotExist() {

      final int expected = -1; // no 'IQ151'in resources/data.csv file

      int result = service.getRowNumber("IQ151", "2010 Q4", null);
      assertEquals(expected, result);
    }
  }

  @Nested
  class getReportRecord {

    @Test
    void getReportRecord_should_Succeed() throws NoSuchElementException {

      final ReportRecord expected =
          new ReportRecord("Dell", 2525); // according to recorces/data.csv file

      ReportRecord result = service.getReportRecord("Dell", "2010 Q4");
      assertEquals(expected, result);
    }

    @Test
    void getReportRecord_should_throwNoSuchElementException_when_itemInFileDoesNotExist() {

      assertThrows(
          NoSuchElementException.class,
          () -> {
            ReportRecord result = service.getReportRecord("Dell", "2250 Q4");
          });
    }
  }
}
