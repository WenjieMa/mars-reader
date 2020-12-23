package com.constant;

public enum MatcherType {
    CONTRACT_NAME(1, "合同名称"),//值在前方
    CONTRACT_CODE_BAR(2, "合同编码_条形码"),//图片识别
    CONTRACT_PARAM(3, "合同属性");//值在后方

    private Integer type;
    private String name;


    MatcherType(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

}
