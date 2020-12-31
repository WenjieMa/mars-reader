package com.constants;

public enum ContractPreviewConstant {
    DEFAULT_TEMPLATE_CODE("0");

    private String name;


    ContractPreviewConstant(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
