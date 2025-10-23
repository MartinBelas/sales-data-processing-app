package org.example.csvapp.dataexport;

import java.nio.file.Path;
import java.util.List;
import org.example.csvapp.domain.ReportRecord;

public class CsvExporter implements DataExporter {

  @Override
  public Path exportData(List<ReportRecord> reportRecords, String path) {
    throw new UnsupportedOperationException("Export to CSV is not yet implemented.");
  }
}
