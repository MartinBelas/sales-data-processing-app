package org.example.csvapp.dataexport;

import java.nio.file.Path;
import java.util.List;
import org.example.csvapp.domain.ReportRecord;

public class ExcelExporter implements DataExporter {

  @Override
  public Path exportData(List<ReportRecord> reportRecords, String path) {
    throw new UnsupportedOperationException("Export to Excel is not yet implemented.");
  }
}
