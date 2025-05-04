package com.freelancex.notificationservice.enums;

public enum Score {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    private final int value;

    Score(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Score fromInt(int val) {
        for (Score score : values()) {
            if (score.value == val) return score;
        }
        throw new IllegalArgumentException("Invalid score value: " + val);
    }
}
