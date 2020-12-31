package com.constants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ContractPreviewVOUtil {
    public static String IS_ORDER= "2";
    public static String IS_NOT_ORDER= "1";

    public static ContractPreviewVOParamDef[] moneyNameArr = {ContractPreviewVOParamDef.contractSum, ContractPreviewVOParamDef.contractSumWithoutTax, ContractPreviewVOParamDef.contractTax};
    public static Set<ContractPreviewVOParamDef> moneyNameSet = new HashSet<ContractPreviewVOParamDef>(Arrays.asList(moneyNameArr));

    public static ContractPreviewVOParamDef[] getMoneyNameArr() {
        return moneyNameArr;
    }

    public static Set<ContractPreviewVOParamDef> getMoneyNameSet() {
        return moneyNameSet;
    }
}
