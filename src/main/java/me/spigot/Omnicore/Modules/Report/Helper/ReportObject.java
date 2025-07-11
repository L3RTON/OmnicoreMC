package me.spigot.Omnicore.Modules.Report.Helper;

public class ReportObject {
    private final String reporterName;
    private final String message;
    private final long timestamp;

    public ReportObject(String reporterName, String message) {
        this.reporterName = reporterName;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public String getReporterName() {
        return reporterName;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
