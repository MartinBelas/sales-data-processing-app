package org.example.csvapp;

import org.example.csvapp.domain.ExtendedReportRecord;
import org.example.csvapp.domain.SortBy;
import org.example.csvapp.service.CsvToExcelService;
import org.example.csvapp.service.CsvToHtmlService;
import org.example.csvapp.service.DataService;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This file exists solely for the purpose of conveniently testing
 * all use cases described in the assignment in one place.
 */

public class App {

  public static final String DEFAULT_RESOURCE_PATH = "src/main/resources/data.csv";
  private static final String QUARTER_EXAMPLE = "2010 Q4";
  private static final String VENDOR_EXAMPLE = "Dell";
  private static final SortBy SORT_BY_EXAMPLE = SortBy.VENDOR;

  public static void main(String[] args) {

    final String resourcePath = args.length > 0 ? args[0] : DEFAULT_RESOURCE_PATH;

    // let's try some data processing
    final Path htmlResultPath = Paths.get(resourcePath).getParent().resolve("report.html");
    final CsvToHtmlService csvToHtmlService = new CsvToHtmlService(resourcePath);

    /* 1. I want to know
     * how many units Dell sold during the given quarter
     * and what its percentage share is.
     */
    final ExtendedReportRecord reportRecord =
        csvToHtmlService.getReportRecordWithShare(VENDOR_EXAMPLE, QUARTER_EXAMPLE);
    System.out.printf(
        "/---> 1. example: %s sold during the quarter %s: %d units, its percentage share is %.1f %%%n",
        VENDOR_EXAMPLE, QUARTER_EXAMPLE, reportRecord.units(), reportRecord.share() * 100);

    /* 2. I want to know which row contains information about Dell */
    final int rowNumber =
        csvToHtmlService.getRowNumber(VENDOR_EXAMPLE, QUARTER_EXAMPLE, SORT_BY_EXAMPLE);
    System.out.printf(
        "/---> 2. example: %s vendor in %s quarter, row number: %d%n",
        VENDOR_EXAMPLE, QUARTER_EXAMPLE, rowNumber);

    /* 3. Sort the rows alphabetically (by vendor). */
    final Path resultPathVendor = generateModifiedReportPath(csvToHtmlService, QUARTER_EXAMPLE, SortBy.VENDOR, htmlResultPath);
    System.out.printf("/---> 3. example: Sort the rows alphabetically (by vendor), exported file %s %n", resultPathVendor);

    /* 4. Sort the rows by unit values. */
    final Path resultPathUnits = generateModifiedReportPath(csvToHtmlService, QUARTER_EXAMPLE, SortBy.UNITS, htmlResultPath);
    System.out.printf("/---> 4. example: Sort the rows alphabetically (by vendor), exported file %s %n", resultPathUnits);

    /* 5. Export the object structure to HTML */
    final Path resultPath = csvToHtmlService.generateReport(QUARTER_EXAMPLE, null, htmlResultPath.toString());
    System.out.println("/---> 5. example: result html path: " + resultPath);

    /* 6. Export the object structure to Excel */
    Path excelResultPath = Paths.get(resourcePath).getParent().resolve("report.xls");
    final DataService csvToExcelService = new CsvToExcelService(resourcePath);
    try {
      excelResultPath = csvToExcelService.generateReport(QUARTER_EXAMPLE, SORT_BY_EXAMPLE, excelResultPath.toString()); // should throw the exception
    } catch (Exception e) {
      System.out.println("/---> 6. example: result excel export exception message: " + e.getMessage());
    }

    /* 7. Export the object structure to CSV */
    // todo

  }

  private static Path generateModifiedReportPath(CsvToHtmlService csvToHtmlService, String quarter, SortBy sortBy, Path htmlResultPath) {

    final String filename = htmlResultPath.toString();

    int dotIndex = filename.lastIndexOf('.');
    String suffix = "_by_" + sortBy.name().toLowerCase();

    String modifiedFilename = (dotIndex != -1)
            ? filename.substring(0, dotIndex) + suffix + filename.substring(dotIndex)
            : filename + suffix;

    return csvToHtmlService.generateReport(quarter, sortBy, modifiedFilename);
  }
}
