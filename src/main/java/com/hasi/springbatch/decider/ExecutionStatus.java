package com.hasi.springbatch.decider;

public enum ExecutionStatus {
    EVEN("EVEN"),
    ODD("ODD"),
    ANY("*");

    private String value;

    private ExecutionStatus(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value; //will return , or ' instead of COMMA or APOSTROPHE
    }
}
