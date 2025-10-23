package org.example.csvapp.service;

import org.example.csvapp.dataexport.DataExporter;
import org.example.csvapp.dataexport.DataExporterProvider;
import org.example.csvapp.dataexport.ReportRecordComparator;
import org.example.csvapp.domain.ExportDataType;
import org.example.csvapp.domain.ExtendedReportRecord;
import org.example.csvapp.domain.ImportDataType;
import org.example.csvapp.domain.ReportRecord;
import org.example.csvapp.domain.SalesRecord;
import org.example.csvapp.domain.SortBy;
import org.example.csvapp.exception.NoSuchElementException;
import org.example.csvapp.repository.InMemorySalesRecordRepository;
import org.example.csvapp.repository.Repository;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

abstract class BaseDataService implements DataService {

  private static final Repository<SalesRecord> salesRecordRepo =
      InMemorySalesRecordRepository.getInstance();

  private final DataExporter dataExporter;

  BaseDataService(
      ImportDataType importDataType, String resourcePath, ExportDataType exportDataType) {
    InMemorySalesRecordRepository.init(importDataType, resourcePath);
    this.dataExporter = DataExporterProvider.getDataExporter(exportDataType);
  }

  @Override
  public ReportRecord getReportRecord(String vendor, String quarter) throws NoSuchElementException {
    return salesRecordRepo.findAll().stream()
        .filter(r -> quarter.equals(r.getTimescale()) && vendor.equals(r.getVendor()))
        .findFirst()
        .map(this::mapToOutputRecord)
        .orElseThrow(() -> new NoSuchElementException(vendor, quarter));
  }

  @Override
  public ExtendedReportRecord getReportRecordWithShare(String vendor, String quarter) {

    final double totalUnits =
        salesRecordRepo.findAll().stream()
            .filter(r -> quarter.equals(r.getTimescale()))
            .mapToDouble(SalesRecord::getUnits)
            .sum();

    return salesRecordRepo.findAll().stream()
        .filter(r -> quarter.equals(r.getTimescale()) && vendor.equals(r.getVendor()))
        .map(r -> new ExtendedReportRecord(vendor, (int) r.getUnits(), r.getUnits() / totalUnits))
        .reduce(
            (r1, r2) ->
                new ExtendedReportRecord(
                    r1.vendor(), r1.units() + r2.units(), r1.share() + r2.share()))
        .orElse(null);
  }

  @Override
  public int getRowNumber(String vendor, String quarter, SortBy sortBy) {

    int rowNumber = 1;

    for (SalesRecord item :
        salesRecordRepo.findAll().stream().filter(r -> quarter.equals(r.getTimescale())).toList()) {
      if (item.getVendor().equals(vendor)) {
        return rowNumber;
      }
      rowNumber++;
    }
    return -1; // not found
  }

  @Override
  public Path generateReport(String quarter, SortBy sortBy, String resultPath) {
    final List<ReportRecord> reportRecords = getReportRecords(quarter, sortBy);
    return exportToFile(reportRecords, resultPath);
  }

  private Path exportToFile(List<ReportRecord> reportRecords, String resultPath) {
    return dataExporter.exportData(reportRecords, resultPath);
  }

  protected List<ReportRecord> getReportRecords(String quarter, SortBy sortBy) {

    Stream<ReportRecord> stream = salesRecordRepo.findAll().stream()
            .filter(r -> quarter.equals(r.getTimescale()))
            .map(this::mapToOutputRecord);

    if (sortBy != null) {
      stream = stream.sorted(ReportRecordComparator.byField(sortBy.toString()));
    }

    return stream.toList();
  }

  private ReportRecord mapToOutputRecord(SalesRecord salesRecord) {
    return new ReportRecord(salesRecord.getVendor(), (int) salesRecord.getUnits());
  }
}
