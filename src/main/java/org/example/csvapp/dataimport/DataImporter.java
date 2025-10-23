package org.example.csvapp.dataimport;

import org.example.csvapp.domain.SalesRecord;
import org.example.csvapp.exception.ImportDataException;

import java.util.stream.Stream;

public interface DataImporter {

    Stream<SalesRecord> importData(String path) throws ImportDataException;
}
