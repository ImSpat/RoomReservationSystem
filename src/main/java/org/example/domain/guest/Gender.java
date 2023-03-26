package org.example.domain.guest;

import org.example.util.Properties;

public enum Gender {
    MALE(Properties.MALE),
    FEMALE(Properties.FEMALE);

    private String asStr;

    Gender(String asStr) {
        this.asStr = asStr;
    }

    @Override
    public String toString() {
        return this.asStr;
    }
}
