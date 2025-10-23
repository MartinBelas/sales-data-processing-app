package org.example.csvapp.dataexport;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.logging.Logger;

import org.example.csvapp.domain.ReportRecord;
import org.example.csvapp.exception.ExportDataException;

public class HtmlExporter implements DataExporter {

  private static final Logger LOGGER = Logger.getLogger(HtmlExporter.class.getName());

  @Override
  public Path exportData(List<ReportRecord> reportRecords, String path) {

    String htmlString = createHtml(reportRecords);
      try {
          return saveToFile(htmlString, path);
      } catch (ExportDataException e) {
        LOGGER.severe(String.format("Can't export to %s", path));
        return null;
      }
  }

  private String createHtml(List<ReportRecord> reportRecords) {
    final int totalUnits = reportRecords.stream()
            .mapToInt(ReportRecord::units)
            .sum();

    StringBuilder html = new StringBuilder();

    html.append("<table border='1' cellpadding='5'>\n");
    html.append("<tr><th>Vendor</th><th>Units</th><th>Share</th></tr>\n");

    double share;
    for (ReportRecord r : reportRecords) {
      html.append("<tr>");
      html.append("<td>").append(r.vendor()).append("</td>");
      html.append("<td>").append(r.units()).append("</td>");
      share = totalUnits == 0 ? 0 : (r.units() * 100.0 / totalUnits);
      html.append("<td>").append(String.format("%.2f%%", share)).append("</td>");
      html.append("</tr>\n");
    }
    html.append("<tr><th>Total</th><th>").append(totalUnits).append("</th><th>100%</th></tr>\n");
    html.append("</table>");

    return html.toString();
  }

  private Path saveToFile(String htmlString, String path) throws ExportDataException {
    try {
      return Files.writeString(Path.of(path), htmlString, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    } catch (IOException e) {
      throw new ExportDataException("Failed to save HTML to file: " + path, e);
    }
  }
}
