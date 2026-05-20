package com.snow.event.manager.security.permissions;

public enum Role
{
    USER("USER"),
    ORGANIZER("ORGANIZER"),
    ADMIN("ADMIN");

    private final String value;

    Role(String value) { this.value = value; }

    public String getValue() { return value; }
}