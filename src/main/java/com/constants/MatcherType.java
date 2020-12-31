package com.constants;

public enum MatcherType {
    CONTRACT_NAME("1", "合同名称"),//值在前方
    CONTRACT_CODE_BAR("2", "合同编码_条形码"),//图片识别
    CONTRACT_PARAM("3", "合同属性"),//值在后方
    CONTRACT_MONEY("4", "合同金额");//值在后方，去掉中间的逗号

    private String type;
    private String name;

    MatcherType() {
    }

    MatcherType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

}
