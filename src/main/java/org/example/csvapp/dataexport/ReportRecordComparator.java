package org.example.csvapp.dataexport;

import org.example.csvapp.domain.ReportRecord;

import java.util.Comparator;

public class ReportRecordComparator {

    private ReportRecordComparator() {}

    public static Comparator<ReportRecord> byField(String field) {
        return switch (field.toLowerCase()) {
            case "vendor" -> Comparator.comparing(ReportRecord::vendor, String.CASE_INSENSITIVE_ORDER);
            case "units" -> Comparator.comparingInt(ReportRecord::units);
            default -> throw new IllegalArgumentException("Unknown sort field: " + field);
        };
    }

    public static Comparator<ReportRecord> byField(String field, boolean ascending) {
        Comparator<ReportRecord> comparator = byField(field);
        return ascending ? comparator : comparator.reversed();
    }
}
