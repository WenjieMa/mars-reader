package com.vo;

public class ContractParamShowVo {
    public String paramCode;
    public String paramName;
    public String value;
    public String possibleValue;
    public Integer index;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPossibleValue() {
        return possibleValue;
    }

    public void setPossibleValue(String possibleValue) {
        this.possibleValue = possibleValue;
    }
}
