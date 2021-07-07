package com.zm.beans;

public enum Category {
    FOOD(1),
    ELECTRICITY(2),
    ELECTRONIC(3),
    RESTAURANT(4),
    VACATION(5),
    HOME(6),
    GARDEN(7);
    private int val;
    Category(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
