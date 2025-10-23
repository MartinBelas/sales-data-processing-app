package org.example.csvapp.dataexport;

import org.example.csvapp.domain.ReportRecord;

import java.nio.file.Path;
import java.util.List;

public interface DataExporter {

    Path exportData(List<ReportRecord> reportRecords, String path);
}
