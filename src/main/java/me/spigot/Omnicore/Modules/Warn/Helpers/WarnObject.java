package me.spigot.Omnicore.Modules.Warn.Helpers;

public class WarnObject {
    private final String reason;
    private final long timestamp;
    private final String staff;

    public WarnObject(String reason, long timestamp, String staff) {
        this.reason = reason;
        this.timestamp = timestamp;
        this.staff = staff;
    }

    public String getReason() {
        return reason;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getStaff() {
        return staff;
    }
}
