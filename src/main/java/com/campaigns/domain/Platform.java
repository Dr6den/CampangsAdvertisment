package com.campaigns.domain;

/**
 *
 * @author andrew
 */
public enum Platform {
    WEB(0),
    ANDROID(1),
    IOS(2);
    private final int value;
    private Platform(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
