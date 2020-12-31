package com.constants;

public enum ContractPreviewVOParamDef {
    contractCode("contractCode"),
    contractName("contractName"),
    contractTextCode("contractTextCode"),
    contractType("contractType"),
    contractPromoter("contractPromoter"),
    contractPartyCompanyNameA("contractPartyCompanyNameA"),
    contractPartyAddressA("contractPartyAddressA"),
    contractChargePersonA("contractChargePersonA"),
    contractPartyCompanyNameB("contractPartyCompanyNameB"),
    contractPartyAddressB("contractPartyAddressB"),
    contractChargePersonB("contractChargePersonB"),
    contractBankNameA("contractBankNameA"),
    contractBankAddressA("contractBankAddressA"),
    contractBankUserA("contractBankUserA"),
    contractBankAccountA("contractBankAccountA"),
    contractTaxPayerA("contractTaxPayerA"),
    contractAddressA("contractAddressA"),
    contractPhoneA("contractPhoneA"),
    contractBankNameB("contractBankNameB"),
    contractBankAddressB("contractBankAddressB"),
    contractBankUserB("contractBankUserB"),
    contractBankAccountB("contractBankAccountB"),
    contractTaxPayerB("contractTaxPayerB"),
    contractAddressB("contractAddressB"),
    contractPhoneB("contractPhoneB"),
    contractPromiseMoney("contractPromiseMoney"),
    contractBreakRoleResponsibility("contractBreakRoleResponsibility"),
    contractSum("contractSum"),
    contractSumWithoutTax("contractSumWithoutTax"),
    contractIsExtraTax("contractIsExtraTax"),
    contractTax("contractTax");

    String name;



    ContractPreviewVOParamDef(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
