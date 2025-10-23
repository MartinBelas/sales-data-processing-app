package org.example.csvapp.domain;

import com.opencsv.bean.CsvBindByName;

public class SalesRecord {

    @CsvBindByName
    private String country;

    @CsvBindByName
    private String timescale;

    @CsvBindByName
    private String vendor;

    @CsvBindByName
    private double units;

    /**
     * Public no-arg constructor required by OpenCSV
     */
    public SalesRecord() {
    }

    /**
     * All-args constructor for convenience
     */
    public SalesRecord(String country, String timescale, String vendor, double units) {
        this.country = country;
        this.timescale = timescale;
        this.vendor = vendor;
        this.units = units;
    }

    public String getTimescale() {
        return timescale;
    }

    public String getVendor() {
        return vendor;
    }

    public double getUnits() {
        return units;
    }

    @Override
    public String toString() {
        return "SalesRecord{" +
                "country='" + country + '\'' +
                ", timescale='" + timescale + '\'' +
                ", vendor='" + vendor + '\'' +
                ", units=" + units +
                '}';
    }
}
