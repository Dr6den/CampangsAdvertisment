package com.campaigns.domain;

/**
 *
 * @author andrew
 */
public enum Status {
    ACTIVE(1),
    PAUSED(2),
    FINISHED(3);
    private final int value;
    private Status(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
