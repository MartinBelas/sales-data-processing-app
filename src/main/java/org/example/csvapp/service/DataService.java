package org.example.csvapp.service;

import org.example.csvapp.domain.ExtendedReportRecord;
import org.example.csvapp.domain.ReportRecord;
import org.example.csvapp.domain.SortBy;
import org.example.csvapp.exception.NoSuchElementException;

import java.nio.file.Path;

public interface DataService {

    Path generateReport(String quarter, SortBy sortBy, String resultPath);

    int getRowNumber(String vendor, String quarter, SortBy sortBy);

    ReportRecord getReportRecord(String vendor, String quarter) throws NoSuchElementException;

    ExtendedReportRecord getReportRecordWithShare(String vendor, String quarter);
}
