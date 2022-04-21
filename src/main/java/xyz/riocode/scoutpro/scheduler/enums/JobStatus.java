package xyz.riocode.scoutpro.scheduler.enums;

public enum JobStatus {

    CREATED("CREATED"),
    SCHEDULED("SCHEDULED"),
    PAUSED("PAUSED");

    private String statusValue;

    JobStatus(String statusValue) {
        this.statusValue = statusValue;
    }

}
