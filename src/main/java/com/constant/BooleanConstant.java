package com.constant;

public enum  BooleanConstant {
    YES("1"),
    NO("0");

    private String name;


    BooleanConstant(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
