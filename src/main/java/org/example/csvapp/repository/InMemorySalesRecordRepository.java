package org.example.csvapp.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.example.csvapp.dataimport.DataImporter;
import org.example.csvapp.dataimport.DataImporterProvider;
import org.example.csvapp.domain.ImportDataType;
import org.example.csvapp.domain.SalesRecord;
import org.example.csvapp.exception.ImportDataException;

/** In-memory repository for SalesRecord using composite key (vendor + quarter). */
public class InMemorySalesRecordRepository implements Repository<SalesRecord> {

  private static final Logger LOGGER =
      Logger.getLogger(InMemorySalesRecordRepository.class.getName());

  private static DataImporter dataImporter;
  private static String resourcePath;

  // Singleton instance
  private static final InMemorySalesRecordRepository INSTANCE = new InMemorySalesRecordRepository();

  // Thread-safe storage: key = the SalesRecord object
  private final Map<SalesRecord, SalesRecord> storage =
          Collections.synchronizedMap(new LinkedHashMap<>());
  private final AtomicBoolean loaded = new AtomicBoolean(false);

  private InMemorySalesRecordRepository() {}

  /**
   * Initializes the singleton with a DataImporter dependency. This method must be called once
   * before getInstance() is used.
   */
  public static synchronized void init(ImportDataType importerType, String resourcePath) {
    if (dataImporter == null) {
      ImportDataType importDataType =
          Objects.requireNonNull(importerType, "importDataType cannot be null");
      InMemorySalesRecordRepository.dataImporter = DataImporterProvider.getDataImporter(importDataType);
      InMemorySalesRecordRepository.resourcePath = Objects.requireNonNull(resourcePath, "resourcePath cannot be null");
    } else {
      LOGGER.fine("InMemorySalesRecordRepository already initialized - ignoring new attempt.");
    }
  }

  public static InMemorySalesRecordRepository getInstance() {
    return INSTANCE;
  }

  @Override
  public List<SalesRecord> findAll() {
    loadData();
    return new ArrayList<>(storage.values());
  }

  /** Loads data from file only once. */
  private void loadData() {
    if (loaded.compareAndSet(false, true)) {
      try (Stream<SalesRecord> stream = dataImporter.importData(resourcePath)) {
        stream.forEach(i -> storage.put(i, i));
        LOGGER.info(() -> String.format("Data loaded from: %s (%d records)", resourcePath, storage.size()));
      } catch (ImportDataException e) {
        LOGGER.severe(
            String.format(
                "Can't load data with %s from %s!",
                dataImporter.getClass().getSimpleName(), resourcePath));
        throw new RuntimeException(e);
      }
    }
  }
}
