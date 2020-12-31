package com.constants;

public enum EffectiveFileType {
    DOC("doc"),
    DOCX("docx"),
    PDF("pdf"),
    TXT("txt");

    private String name;

    EffectiveFileType() {
    }

    EffectiveFileType(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public static boolean isValid(String name) {
        boolean include = false;
        for (EffectiveFileType type : values()) {
            if (type.getName().intern().equals(name)) {
                include = true;
                break;
            }
        }
        return include;
    }


    public static EffectiveFileType getByValue(String value) {
        for (EffectiveFileType type : values()) {
            if (type.getName() == value) {
                return type;
            }
        }
        return null;
    }

}
