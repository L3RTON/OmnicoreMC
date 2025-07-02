package me.spigot.Omnicore.Commands.Jail.Helpers;

import org.bukkit.Location;

public class JailData {
    public Location jailCenter;
    public long remainingMs;
    public long startTimeMs;
    public int taskId;

    public JailData(Location jailCenter, long remainingMs, long startTimeMs, int taskId) {
        this.jailCenter = jailCenter;
        this.remainingMs = remainingMs;
        this.startTimeMs = startTimeMs;
        this.taskId = taskId;
    }
}