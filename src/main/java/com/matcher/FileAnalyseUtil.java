package com.matcher;

import com.constant.BooleanConstant;
import com.constant.MatcherType;
import com.encode.EncodeUtil;
import com.reader.PdfReaderUtil;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class FileAnalyseUtil {
    public static Set<String> contractTextCodeCodeSet = new HashSet<String>();
    public static WordMatcher wordMatcher = new WordMatcher();
    public static Map<String, List<String>> patternNormalMap = new HashMap<String, List<String>>();
    public static Map<String, List<String>> patternOrderMap = new HashMap<String, List<String>>();
    public static Map<String, String> name2TypeMap = new HashMap<String, String>();
    public static Map<String, String> textCode2TypeMap = new HashMap<String, String>();


    public static void main(String[] args) throws IOException {
        init();
        String path = "C:\\Users\\Windows10\\Desktop\\";
        path += "实验室设备维保服务合同.pdf";
        FileInputStream inputStream = new FileInputStream(new File(path));
        List<String> pdfDataList = PdfReaderUtil.readPdf(inputStream);
        //List<String> pdfDataList =  PdfReaderUtil.readPdfTable(inputStream);

        String content = EncodeUtil.toDBC(pdfDataList.get(0));
        String first200Content = content.substring(0, 200);

        System.out.println(content);
        Boolean isOrder = false;
        String contractCode = null;
        String contractName = null;
        String contractTextCode = null;
        String contractType = null;
        List<String> contractTypeList = new ArrayList<String>();
        String contractPromoter = null;
        String contractPartyCompanyNameA = null;
        String contractPartyAddressA = null;
        String contractChargePersonA = null;
        String contractPartyCompanyNameB = null;
        String contractPartyAddressB = null;
        String contractChargePersonB = null;
        String contractBankNameA = null;
        String contractBankAddressA = null;
        String contractBankUserA = null;
        String contractBankAccountA = null;
        String contractTaxPayerA = null;
        String contractAddressA = null;
        String contractPhoneA = null;
        String contractBankNameB = null;
        String contractBankAddressB = null;
        String contractBankUserB = null;
        String contractBankAccountB = null;
        String contractTaxPayerB = null;
        String contractAddressB = null;
        String contractPhoneB = null;
        String contractPromiseMoney = null;
        String contractBreakRoleResponsibility = null;
        String contractSum = null;
        String contractSumWithoutTax = null;
        String contractIsExtraTax = null;
        String contractTax = null;

        for (String contractTextCodeCode : contractTextCodeCodeSet) {
            contractTextCode = wordMatcher.matcherValue(first200Content, contractTextCodeCode, MatcherType.CONTRACT_PARAM);
            if (!"".equals(contractTextCode)) {
                System.out.println("----------合同文本编号:" + contractTextCode + "----------");
                contractType = textCode2TypeMap.get(contractTextCode);
                if (!StringUtils.isEmpty(contractType)) {
                    System.out.println("----------合同类型:" + contractType + "----------");
                }
            }
        }
        if ("".equals(contractTextCode)) {
            System.out.println("----------合同文本编号:" + contractTextCode + "----------");
        }

        contractName = wordMatcher.matcherValue(first200Content, "订单", MatcherType.CONTRACT_NAME);
        System.out.println("----------合同名称:" + contractName + "----------");
        for (String key : name2TypeMap.keySet()) {
            if (contractName.contains(key)) {
                contractType = name2TypeMap.get(key);
                System.out.println("----------合同类型:" + contractType + "----------");
                contractTypeList.add(contractType);
            }
        }


        contractPromoter = wordMatcher.matcherValue(content, "(?<=采购方[\\s\\S]{0,500}联系人).{0,500}", MatcherType.CONTRACT_PARAM);
        System.out.println("----------合同发起人:" + contractPromoter + "----------");

        contractPartyCompanyNameA = wordMatcher.matcherValue(content, "(?<=采购方.{0,500}:).{0,500}", MatcherType.CONTRACT_PARAM);
        System.out.println("----------甲方公司名称:" + contractPartyCompanyNameA + "----------");

        contractPartyAddressA = wordMatcher.matcherValue(content, "(?<=采购方[\\s\\S]{0,500}地址.{0,500}:).{0,500}", MatcherType.CONTRACT_PARAM);
        System.out.println("----------甲方地址:" + contractPartyAddressA + "----------");


        contractChargePersonA = wordMatcher.matcherValue(content, "(?<=采购方[\\s\\S]{0,500}(法定代表人|负责人).{0,500}:).{0,500}", MatcherType.CONTRACT_PARAM);
        System.out.println("----------甲方负责人:" + contractChargePersonA + "----------");


        contractPartyCompanyNameB = wordMatcher.matcherValue(content, "(?<=乙方信息.{0,500}:[\\s\\S]{0,500}服务商名称.{0,500}:).{0,500}", MatcherType.CONTRACT_PARAM);
        System.out.println("----------乙方公司名称:" + contractPartyCompanyNameB + "----------");


        contractPartyAddressB = wordMatcher.matcherValue(content, "(?<=乙方信息[\\s\\S]{0,500}(^银行)地址.{0,500}:).{0,500}", MatcherType.CONTRACT_PARAM);
        System.out.println("----------乙方地址:" + contractPartyAddressB + "----------");

        contractChargePersonB = wordMatcher.matcherValue(content, "(?<=乙方[\\s\\S]{0,500}(法定代表人|负责人).{0,500}:).{0,500}", MatcherType.CONTRACT_PARAM);
        System.out.println("----------乙方负责人:" + contractChargePersonB + "----------");

        contractBankNameA = wordMatcher.matcherValue(content, "(?<=采购方[\\s\\S]{0,500}开户行.{0,500}:).{0,500}", MatcherType.CONTRACT_PARAM);
        System.out.println("----------甲方财务信息:开户行:" + contractBankNameA + "----------");

        contractBankAddressA = wordMatcher.matcherValue(content, "(?<=供应商[\\s\\S]{0,500}银行地址.{0,500}:).{0,500}", MatcherType.CONTRACT_PARAM);
        System.out.println("----------甲方财务信息:银行地址:" + contractBankAddressA + "----------");

        contractBankUserA = wordMatcher.matcherValue(content, "(?<=(^乙).{0,500}户名.{0,500}:).{0,500}", MatcherType.CONTRACT_PARAM);
        System.out.println("----------甲方财务信息:户名:" + contractBankUserA + "----------");

        contractBankAccountA = wordMatcher.matcherValue(content, "(?<=(^乙).{0,500}账号.{0,500}:).{0,500}", MatcherType.CONTRACT_PARAM);
        System.out.println("----------甲方财务信息:账号:" + contractBankAccountA + "----------");

        //甲方不识别纳税人识别号
        //contractTaxPayerA = wordMatcher.matcherValue(content, "(?<=采购方.{0,500}:).{1,500}", MatcherType.CONTRACT_PARAM);
        //System.out.println("----------甲方财务信息:纳税人识别号:" + contractTaxPayerA + "----------");

        contractAddressA = wordMatcher.matcherValue(content, "(?<=采购方[\\s\\S]{0,500}(^银行)地址.{0,500}:).{0,500}", MatcherType.CONTRACT_PARAM);
        System.out.println("----------甲方财务信息:地址:" + contractAddressA + "----------");

        contractPhoneA = wordMatcher.matcherValue(content, "(?<=采购方[\\s\\S]{0,500}电话.{0,500}:).{0,500}", MatcherType.CONTRACT_PARAM);
        System.out.println("----------甲方财务信息:电话:" + contractPhoneA + "----------");

        contractBankNameB = wordMatcher.matcherValue(content, "(?<=乙方[\\s\\S]{0,500}开户行.{0,500}:).{0,500}", MatcherType.CONTRACT_PARAM);
        System.out.println("----------乙方财务信息:开户行:" + contractBankNameB + "----------");

        contractBankAddressB = wordMatcher.matcherValue(content, "(?<=(乙|卖|供应商)[\\s\\S]{0,500}银行地址.{0,500}:).{0,500}", MatcherType.CONTRACT_PARAM);
        System.out.println("----------乙方财务信息:银行地址:" + contractBankAddressB + "----------");

        contractBankUserB = wordMatcher.matcherValue(content, "(?<=(乙|卖|供应商)[\\s\\S]{0,500}户名.{0,500}:).{0,500}", MatcherType.CONTRACT_PARAM);
        System.out.println("----------乙方财务信息:户名:" + contractBankUserB + "----------");

        contractBankAccountB = wordMatcher.matcherValue(content, "(?<=(乙|卖|供应商)[\\s\\S]{0,500}账号.{0,500}:).{0,500}", MatcherType.CONTRACT_PARAM);
        System.out.println("----------乙方财务信息:账号:" + contractBankAccountB + "----------");

        contractTaxPayerB = wordMatcher.matcherValue(content, "(?<=(乙|卖|供应商)[\\s\\S]{0,500}纳税人识别号.{0,500}:).{0,500}", MatcherType.CONTRACT_PARAM);
        System.out.println("----------乙方财务信息:纳税人识别号:" + contractTaxPayerB + "----------");

        contractAddressB = wordMatcher.matcherValue(content, "(?<=(乙|卖|供应商)[\\s\\S]{0,500}(^银行)地址.{0,500}:).{0,500}", MatcherType.CONTRACT_PARAM);
        System.out.println("----------乙方财务信息:地址:" + contractAddressB + "----------");

        contractPhoneB = wordMatcher.matcherValue(content, "(?<=(乙|卖|供应商)[\\s\\S]{0,500}电话.{0,500}:).{0,500}", MatcherType.CONTRACT_PARAM);
        System.out.println("----------乙方财务信息:电话:" + contractPhoneB + "----------");


        //contractPromiseMoney = wordMatcher.matcherValue(content, "(?<=采购方.{0,500}:).{1,500}", MatcherType.CONTRACT_PARAM);

        //contractBreakRoleResponsibility = wordMatcher.matcherValue(content, "(?<=采购方.{0,500}:).{1,500}", MatcherType.CONTRACT_PARAM);

        contractSum = wordMatcher.matcherValue(content, "(?<=合同.{0,500}总额.{0,500}\\(含税.{0,500}\\).{1,500}[\\n]{0,500}.{0,500})[\\d\\.\\,]{2,500}", MatcherType.CONTRACT_PARAM);
        System.out.println("----------合同总价:" + contractSum + "----------");

        contractSumWithoutTax = wordMatcher.matcherValue(content, "(?<=合同.{0,500}总额.{0,500}含税[\\s\\S]{0,500}[\\d\\.\\,]{2,500}[\\s\\S]{0,500}价款[\\s\\S]{0,500})[\\d\\.\\,]{2,500}", MatcherType.CONTRACT_PARAM);
        System.out.println("----------合同不含税价:" + contractSumWithoutTax + "----------");

        //contractIsExtraTax = wordMatcher.matcherValue(content, "(?<=采购方.{0,500}:).{1,500}", MatcherType.CONTRACT_PARAM);

        contractTax = wordMatcher.matcherValue(content, "(?<=合同.{0,500}总额.{0,500}含税[\\s\\S]{0,500}[\\d\\.\\,]{2,500}[\\s\\S]{0,500}税款[\\s\\S]{0,500})[\\d\\.\\,]{2,500}", MatcherType.CONTRACT_PARAM);
        System.out.println("----------税额:" + contractTax + "----------");


        if (pdfDataList.size() > 1) {
            for (int i = 1; i < pdfDataList.size(); i++) {
                if (!"".equals(pdfDataList.get(i))) {
                    if (pdfDataList.get(i).length() > 16) {
                        contractCode = pdfDataList.get(i);
                        System.out.println("----------合同编码:" + contractCode + "----------");
                        break;
                    }
                }
            }
        }


    }


    private static void init() {
        contractTextCodeCodeSet.add("ZGDX2020122");
        contractTextCodeCodeSet.add("ZGDX2020115");

        contractTextCodeCodeSet.add("ZGDX2020116");
        contractTextCodeCodeSet.add("ZGDX2020117");
        contractTextCodeCodeSet.add("ZGDX2020118");
        contractTextCodeCodeSet.add("ZGDX2020079");
        contractTextCodeCodeSet.add("ZGDX2020080");
        contractTextCodeCodeSet.add("ZGDX2020081");
        contractTextCodeCodeSet.add("ZGDX2020082");
        contractTextCodeCodeSet.add("ZGDX2020085");
        contractTextCodeCodeSet.add("ZGDX2020086");
        contractTextCodeCodeSet.add("ZGDX2020087");
        contractTextCodeCodeSet.add("ZGDX2020088");
        contractTextCodeCodeSet.add("ZGDX2020070");
        contractTextCodeCodeSet.add("ZGDX2020071");
        contractTextCodeCodeSet.add("ZGDX2020072");
        contractTextCodeCodeSet.add("ZGDX2020073");
        contractTextCodeCodeSet.add("ZGDX2020074");
        contractTextCodeCodeSet.add("ZGDX2020075");
        contractTextCodeCodeSet.add("ZGDX2020076");
        contractTextCodeCodeSet.add("ZGDX2020077");
        contractTextCodeCodeSet.add("ZGDX2020089");
        contractTextCodeCodeSet.add("ZGDX2020090");
        contractTextCodeCodeSet.add("ZGDX2020091");
        contractTextCodeCodeSet.add("ZGDX2020078");
        contractTextCodeCodeSet.add("ZGDX2020110");
        contractTextCodeCodeSet.add("ZGDX2020083");
        contractTextCodeCodeSet.add("ZGDX2020084");
        contractTextCodeCodeSet.add("ZGDX2020141");
        contractTextCodeCodeSet.add("ZGDX2020145");
        contractTextCodeCodeSet.add("ZGDX2020169");
        contractTextCodeCodeSet.add("ZGDX2020120");
        contractTextCodeCodeSet.add("ZGDX2020166");
        contractTextCodeCodeSet.add("ZGDX2020167");
        contractTextCodeCodeSet.add("ZGDX2020168");
        contractTextCodeCodeSet.add("ZGDX2020027");
        contractTextCodeCodeSet.add("ZGDX2020028");
        contractTextCodeCodeSet.add("ZGDX2020029");
        contractTextCodeCodeSet.add("ZGDX2020041");
        contractTextCodeCodeSet.add("ZGDX2020030");
        contractTextCodeCodeSet.add("ZGDX2020031");
        contractTextCodeCodeSet.add("ZGDX2020032");
        contractTextCodeCodeSet.add("ZGDX2020033");
        contractTextCodeCodeSet.add("ZGDX2020034");
        contractTextCodeCodeSet.add("ZGDX2020035");
        contractTextCodeCodeSet.add("ZGDX2020036");
        contractTextCodeCodeSet.add("ZGDX2020040");
        contractTextCodeCodeSet.add("ZGDX2020126");
        contractTextCodeCodeSet.add("ZGDX2020123");
        contractTextCodeCodeSet.add("ZGDX2020124");
        contractTextCodeCodeSet.add("ZGDX2020125");
        contractTextCodeCodeSet.add("ZGDX2020113");
        contractTextCodeCodeSet.add("ZGDX2020003");
        contractTextCodeCodeSet.add("ZGDX2020004");
        contractTextCodeCodeSet.add("ZGDX2020005");
        contractTextCodeCodeSet.add("ZGDX2020006");
        contractTextCodeCodeSet.add("ZGDX2020007");
        contractTextCodeCodeSet.add("ZGDX2020008");
        contractTextCodeCodeSet.add("ZGDX2020009");
        contractTextCodeCodeSet.add("ZGDX2020010");
        contractTextCodeCodeSet.add("ZGDX2020011");
        contractTextCodeCodeSet.add("ZGDX2020012");
        contractTextCodeCodeSet.add("ZGDX2020013");
        contractTextCodeCodeSet.add("ZGDX2020014");
        contractTextCodeCodeSet.add("ZGDX2020015");
        contractTextCodeCodeSet.add("ZGDX2020016");
        contractTextCodeCodeSet.add("ZGDX2020017");
        contractTextCodeCodeSet.add("ZGDX2020018");
        contractTextCodeCodeSet.add("ZGDX2020019");
        contractTextCodeCodeSet.add("ZGDX2020114");
        contractTextCodeCodeSet.add("ZGDX2020111");
        contractTextCodeCodeSet.add("ZGDX2020112");


        textCode2TypeMap.put("ZGDX2020122", "A-4");
        textCode2TypeMap.put("ZGDX2020115", "A-8");

        textCode2TypeMap.put("ZGDX2020116", "A-8");
        textCode2TypeMap.put("ZGDX2020117", "A-8");
        textCode2TypeMap.put("ZGDX2020118", "A-8");
        textCode2TypeMap.put("ZGDX2020079", "B-4");
        textCode2TypeMap.put("ZGDX2020080", "B-4");
        textCode2TypeMap.put("ZGDX2020081", "B-4");
        textCode2TypeMap.put("ZGDX2020082", "B-4");
        textCode2TypeMap.put("ZGDX2020085", "B-4");
        textCode2TypeMap.put("ZGDX2020086", "B-4");
        textCode2TypeMap.put("ZGDX2020087", "B-4");
        textCode2TypeMap.put("ZGDX2020088", "B-4");
        textCode2TypeMap.put("ZGDX2020070", "B-5");
        textCode2TypeMap.put("ZGDX2020071", "B-6");
        textCode2TypeMap.put("ZGDX2020072", "B-6");
        textCode2TypeMap.put("ZGDX2020073", "B-6");
        textCode2TypeMap.put("ZGDX2020074", "B-6");
        textCode2TypeMap.put("ZGDX2020075", "B-6");
        textCode2TypeMap.put("ZGDX2020076", "B-6");
        textCode2TypeMap.put("ZGDX2020077", "B-6");
        textCode2TypeMap.put("ZGDX2020089", "B-6");
        textCode2TypeMap.put("ZGDX2020090", "B-6");
        textCode2TypeMap.put("ZGDX2020091", "B-6");
        textCode2TypeMap.put("ZGDX2020078", "B-7");
        textCode2TypeMap.put("ZGDX2020110", "B-10");
        textCode2TypeMap.put("ZGDX2020083", "B-12");
        textCode2TypeMap.put("ZGDX2020084", "B-12");
        textCode2TypeMap.put("ZGDX2020141", "C-1");
        textCode2TypeMap.put("ZGDX2020145", "C-7");
        textCode2TypeMap.put("ZGDX2020169", "C-9");
        textCode2TypeMap.put("ZGDX2020120", "C-11");
        textCode2TypeMap.put("ZGDX2020166", "C-15");
        textCode2TypeMap.put("ZGDX2020167", "C-17");
        textCode2TypeMap.put("ZGDX2020168", "C-21");
        textCode2TypeMap.put("ZGDX2020027", "E-1");
        textCode2TypeMap.put("ZGDX2020028", "E-1");
        textCode2TypeMap.put("ZGDX2020029", "E-1");
        textCode2TypeMap.put("ZGDX2020041", "E-1");
        textCode2TypeMap.put("ZGDX2020030", "E-2");
        textCode2TypeMap.put("ZGDX2020031", "E-2");
        textCode2TypeMap.put("ZGDX2020032", "E-2");
        textCode2TypeMap.put("ZGDX2020033", "E-3");
        textCode2TypeMap.put("ZGDX2020034", "E-4");
        textCode2TypeMap.put("ZGDX2020035", "E-4");
        textCode2TypeMap.put("ZGDX2020036", "E-4");
        textCode2TypeMap.put("ZGDX2020040", "E-6");
        textCode2TypeMap.put("ZGDX2020126", "F-1");
        textCode2TypeMap.put("ZGDX2020123", "F-2");
        textCode2TypeMap.put("ZGDX2020124", "F-2");
        textCode2TypeMap.put("ZGDX2020125", "F-2");
        textCode2TypeMap.put("ZGDX2020113", "F-3");
        textCode2TypeMap.put("ZGDX2020003", "F-4");
        textCode2TypeMap.put("ZGDX2020004", "F-4");
        textCode2TypeMap.put("ZGDX2020005", "F-4");
        textCode2TypeMap.put("ZGDX2020006", "F-4");
        textCode2TypeMap.put("ZGDX2020007", "F-4");
        textCode2TypeMap.put("ZGDX2020008", "F-5");
        textCode2TypeMap.put("ZGDX2020009", "F-5");
        textCode2TypeMap.put("ZGDX2020010", "F-5");
        textCode2TypeMap.put("ZGDX2020011", "F-5");
        textCode2TypeMap.put("ZGDX2020012", "F-5");
        textCode2TypeMap.put("ZGDX2020013", "F-5");
        textCode2TypeMap.put("ZGDX2020014", "F-5");
        textCode2TypeMap.put("ZGDX2020015", "F-5");
        textCode2TypeMap.put("ZGDX2020016", "F-5");
        textCode2TypeMap.put("ZGDX2020017", "F-5");
        textCode2TypeMap.put("ZGDX2020018", "F-5");
        textCode2TypeMap.put("ZGDX2020019", "F-5");
        textCode2TypeMap.put("ZGDX2020114", "F-8");
        textCode2TypeMap.put("ZGDX2020111", "I-6");
        textCode2TypeMap.put("ZGDX2020112", "I-6");


        name2TypeMap.put("后勤类", "A-4");
        name2TypeMap.put("房屋租赁", "A-8");
        name2TypeMap.put("房屋", "A-8");
        name2TypeMap.put("场地租赁", "A-8");
        name2TypeMap.put("场地", "A-8");
        name2TypeMap.put("房屋屋面租赁", "A-8");
        name2TypeMap.put("委托代理", "B-4");
        name2TypeMap.put("招标代理", "B-4");
        name2TypeMap.put("招投标廉政", "B-4");
        name2TypeMap.put("采购代理", "B-4");
        name2TypeMap.put("系统集成", "B-5");
        name2TypeMap.put("系统集成合同", "B-5");
        name2TypeMap.put("软件许可", "B-6");
        name2TypeMap.put("软件开发", "B-7");
        name2TypeMap.put("室内分布", "B-10");
        name2TypeMap.put("分布系统", "B-10");
        name2TypeMap.put("设备租赁", "B-12");
        name2TypeMap.put("设备借用", "B-12");
        name2TypeMap.put("电路服务", "C-1");
        name2TypeMap.put("互联网", "C-7");
        name2TypeMap.put("接入业务", "C-7");
        name2TypeMap.put("渠道代理", "C-9");
        name2TypeMap.put("IT设备", "C-11");
        name2TypeMap.put("IT设备租赁", "C-11");
        name2TypeMap.put("云呼叫", "C-15");
        name2TypeMap.put("呼叫中心", "C-15");
        name2TypeMap.put("天翼云", "C-17");
        name2TypeMap.put("云主机", "C-17");
        name2TypeMap.put("物联网", "C-21");
        name2TypeMap.put("技术服务", "E-1");
        name2TypeMap.put("技术开发", "E-2");
        name2TypeMap.put("软件开发", "E-2");
        name2TypeMap.put("技术咨询", "E-3");
        name2TypeMap.put("设备维护", "E-4");
        name2TypeMap.put("安装维护", "E-6");
        name2TypeMap.put("维护服务", "E-6");
        name2TypeMap.put("法律", "F-1");
        name2TypeMap.put("法律服务", "F-1");
        name2TypeMap.put("法律服务委托", "F-1");
        name2TypeMap.put("审计", "F-2");
        name2TypeMap.put("审计业务", "F-2");
        name2TypeMap.put("咨询服务", "F-3");
        name2TypeMap.put("媒体广告", "F-4");
        name2TypeMap.put("广告发布", "F-4");
        name2TypeMap.put("广告设计", "F-4");
        name2TypeMap.put("设计制作", "F-4");
        name2TypeMap.put("宣传", "F-5");
        name2TypeMap.put("宣传品", "F-5");
        name2TypeMap.put("宣传品设计", "F-5");
        name2TypeMap.put("宣传品印制", "F-5");
        name2TypeMap.put("宣传品订购", "F-5");
        name2TypeMap.put("宣传活动", "F-5");
        name2TypeMap.put("培训服务", "F-8");

        name2TypeMap.put("委托培训", "F-8");
        name2TypeMap.put("保密", "I-6");


    }
}
