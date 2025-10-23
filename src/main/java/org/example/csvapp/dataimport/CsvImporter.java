package org.example.csvapp.dataimport;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.example.csvapp.domain.SalesRecord;
import org.example.csvapp.exception.ImportDataException;

import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;

public class CsvImporter implements DataImporter {

  /**
   * Imports data from a CSV file. The CSV is expected to contain the following values, which
   * correspond to the fields in {@link SalesRecord}:
   * <ul>
   *   <li>country
   *   <li>timescale
   *   <li>vendor
   *   <li>units
   * </ul>
   *
   * @param path the path to the CSV file
   * @return a Stream of {@link SalesRecord} objects
   * @throws ImportDataException if an error occurs during import
   */
  @Override
  public Stream<SalesRecord> importData(String path) throws ImportDataException {

    try (FileReader reader = new FileReader(path)) {

      final HeaderColumnNameMappingStrategy<SalesRecord> strategy =
          new HeaderColumnNameMappingStrategy<>();
      strategy.setType(SalesRecord.class);

      CsvToBean<SalesRecord> csvToBean =
          new CsvToBeanBuilder<SalesRecord>(reader)
              .withMappingStrategy(strategy)
              .withSkipLines(0)
              .withIgnoreLeadingWhiteSpace(true)
              .build();

      return csvToBean.parse().stream();

    } catch (IOException e) {
      throw new ImportDataException(path, e);
    }
  }
}
