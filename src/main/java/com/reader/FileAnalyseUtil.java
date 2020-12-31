package com.reader;

import com.constants.ContractPreviewVOParamDef;
import com.constants.ContractPreviewVOUtil;
import com.constants.EffectiveFileType;
import com.constants.MatcherType;


import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

public class FileAnalyseUtil {
    //已存在的合同文本编码
    public Set<String> contractTextCodeSet = new HashSet<String>();

    //key1:MatcherType枚举,1:合同名称;2:合同编码条形码;3:合同属性
    //key2:1：普通合同；2：订单合同
    //key3:合同预览实体属性，和数据库存的解析属性名称一致，用field自动注入
    //List:pattern数组
    public Map<String, Map<String, Map<String, List<String>>>> patternMap = new HashMap<String, Map<String, Map<String, List<String>>>>();

    //合同名称映射合同类型
    public Map<String, String> name2TypeMap = new HashMap<String, String>();

    //合同文本编码映射合同类型
    public Map<String, String> textCode2TypeMap = new HashMap<String, String>();

    public FileAnalyseUtil(Set<String> contractTextCodeSet,
                           Map<String, Map<String, Map<String, List<String>>>> patternMap,
                           Map<String, String> name2TypeMap, Map<String, String> textCode2TypeMap) {
        this.contractTextCodeSet = contractTextCodeSet;
        this.patternMap = patternMap;
        this.name2TypeMap = name2TypeMap;
        this.textCode2TypeMap = textCode2TypeMap;
    }

    public Object readPdfObject(Object object, InputStream inputStream) throws Exception {
        List<String> pdfDataList = PdfReaderUtil.readPdf(inputStream);
        String headAndFootContent = null;
        object = readObjectFromInputStream(object, inputStream, pdfDataList, headAndFootContent, EffectiveFileType.PDF);
        return object;
    }

    public Object readDocObject(Object object, InputStream inputStream) throws Exception {
        List<String> wordDataList = WordReaderUtil.readDocFile(inputStream);
        String headAndFootContent = wordDataList.get(1) + wordDataList.get(2);
        object = readObjectFromInputStream(object, inputStream, wordDataList, headAndFootContent, EffectiveFileType.DOC);
        return object;
    }

    public Object readTxtObject(Object object, InputStream inputStream) throws Exception {
        List<String> txtDataList = new ArrayList<>();
        txtDataList.add(TxtReaderUtil.readTxt(inputStream));
        String headAndFootContent = null;
        object = readObjectFromInputStream(object, inputStream, txtDataList, headAndFootContent, EffectiveFileType.TXT);
        return object;
    }

    public Object readDocxObject(Object object, InputStream inputStream) throws Exception {
        List<String> wordDataList = WordReaderUtil.readDocxFile(inputStream);
        String headAndFootContent = wordDataList.get(1) + wordDataList.get(2);
        object = readObjectFromInputStream(object, inputStream, wordDataList, headAndFootContent, EffectiveFileType.DOCX);
        return object;
    }

    private Object readObjectFromInputStream(Object object, InputStream inputStream, List<String> dataList, String headAndFootContent, EffectiveFileType fileType) throws Exception {
        String content = EncodeUtil.toDBC(dataList.get(0));
        String first200Content = content.substring(0, 200);

        Map<String, Map<String, List<String>>> contractNamePattern = patternMap.get(MatcherType.CONTRACT_NAME.getType() + "");
        Map<String, List<String>> contractParamPattern = new HashMap<String, List<String>>();
        Map<String, List<String>> contractMoneyPattern = new HashMap<String, List<String>>();


        Field[] fields = object.getClass().getDeclaredFields();

        Map<String, List<String>> isOrderMapData = contractNamePattern.get(ContractPreviewVOUtil.IS_ORDER);
        Map<String, List<String>> isNotOrderMapData = contractNamePattern.get(ContractPreviewVOUtil.IS_NOT_ORDER);

        Boolean isOrderFlag = false;
        Boolean notMatchedFlag = true;

        String contractName = null;
        String contractTextCode = null;

        for (String key : isOrderMapData.keySet()) {
            for (String pattern : isOrderMapData.get(key)) {
                contractName = WordMatcher.matcherValue(first200Content, pattern, MatcherType.CONTRACT_NAME);
                if (!EncodeUtil.isEmpty(contractName)) {
                    contractParamPattern = patternMap.get(MatcherType.CONTRACT_PARAM.getType()).get(ContractPreviewVOUtil.IS_ORDER);
                    contractMoneyPattern = patternMap.get(MatcherType.CONTRACT_MONEY.getType()).get(ContractPreviewVOUtil.IS_ORDER);
                    try {
                        dataList = PdfReaderUtil.readPdfTable(inputStream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    content = EncodeUtil.toDBC(dataList.get(0));
                    first200Content = content.substring(0, 200);
                    isOrderFlag = true;
                    notMatchedFlag = false;
                    break;
                }
            }
        }
        if (!isOrderFlag) {
            for (String key : isNotOrderMapData.keySet()) {
                for (String pattern : isNotOrderMapData.get(key)) {
                    contractName = WordMatcher.matcherValue(first200Content, pattern, MatcherType.CONTRACT_NAME);
                    if (!EncodeUtil.isEmpty(contractName)) {
                        contractParamPattern = patternMap.get(MatcherType.CONTRACT_PARAM.getType()).get(ContractPreviewVOUtil.IS_NOT_ORDER);
                        contractMoneyPattern = patternMap.get(MatcherType.CONTRACT_MONEY.getType()).get(ContractPreviewVOUtil.IS_NOT_ORDER);
                        notMatchedFlag = false;
                        break;
                    }
                }
            }
        }
        if (notMatchedFlag) {
            throw new Exception("未匹配到合同名称类型。");
        }
        for (Field field : fields) {
            field.setAccessible(true);
            List<String> patternList;
            if (field.getName().equals(ContractPreviewVOParamDef.contractName.getName())) {
                setFieldValue(object, field, contractName);
            } else if (field.getName().equals(ContractPreviewVOParamDef.contractCode.getName())) {
                patternList = contractParamPattern.get(field.getName());
                switch (fileType) {
                    case DOC:
                        setFieldValueFromPattern(object, field, headAndFootContent, patternList, MatcherType.CONTRACT_PARAM);
                        break;
                    case PDF:
                        setFieldValueFromPdfDataList(object, field, dataList);
                        break;
                    case DOCX:
                        setFieldValueFromPattern(object, field, headAndFootContent, patternList, MatcherType.CONTRACT_PARAM);
                        break;
                    case TXT:
                        setFieldValueFromPattern(object, field, headAndFootContent, patternList, MatcherType.CONTRACT_PARAM);
                        break;
                    default:
                        break;
                }
            } else if (contractParamPattern.get(field.getName()) != null) {
                patternList = contractParamPattern.get(field.getName());
                setFieldValueFromPattern(object, field, content, patternList, MatcherType.CONTRACT_PARAM);
            } else if (contractMoneyPattern.get(field.getName()) != null) {
                patternList = contractMoneyPattern.get(field.getName());
                setFieldValueFromPattern(object, field, content, patternList, MatcherType.CONTRACT_MONEY);
            } else if (field.getName().equals(ContractPreviewVOParamDef.contractTextCode.getName())) {
                contractTextCode = setContractTextCode(contractTextCode, content, headAndFootContent);
                if (!EncodeUtil.isEmpty(contractTextCode)) {
                    setFieldValue(object, field, contractTextCode);
                }
            } else if (field.getName().equals(ContractPreviewVOParamDef.contractType.getName())) {
                String contractType = null;
                if (EncodeUtil.isEmpty(contractTextCode)) {
                    contractTextCode = setContractTextCode(contractTextCode, content, headAndFootContent);
                }
                if (!EncodeUtil.isEmpty(contractTextCode)) {
                    contractType = textCode2TypeMap.get(contractTextCode);
                    if (!EncodeUtil.isEmpty(contractType)) {
                        setFieldValue(object, field, name2TypeMap.get(contractType));
                    }
                }
                if (EncodeUtil.isEmpty(contractType)) {
                    for (String key : name2TypeMap.keySet()) {
                        if (contractName.contains(key)) {
                            setFieldValue(object, field, name2TypeMap.get(key));
                        }
                    }
                }
            } else {
                System.out.println("存在没有映射值的属性" + field.getName());
            }
        }
        return object;
    }

    private String setContractTextCode(String contractTextCode, String content, String headAndFootContent) {
        for (String contractTextCodeFromSet : contractTextCodeSet) {
            if (EncodeUtil.isEmpty(contractTextCode)) {
                contractTextCode = WordMatcher.matcherValue(content, contractTextCodeFromSet, MatcherType.CONTRACT_CODE_BAR);
            }
            if (EncodeUtil.isEmpty(contractTextCode) && !EncodeUtil.isEmpty(headAndFootContent)) {
                contractTextCode = WordMatcher.matcherValue(headAndFootContent, contractTextCodeFromSet, MatcherType.CONTRACT_CODE_BAR);
            }
        }
        return contractTextCode;
    }

    //从解析的pdf的其他内容获取属性，目前只获取contractCode
    private void setFieldValueFromPdfDataList(Object object, Field field, List<String> pdfDataList) {
        if (pdfDataList.size() > 1) {
            for (int i = 1; i < pdfDataList.size(); i++) {
                if (!EncodeUtil.isEmpty(pdfDataList.get(i))) {
                    if (pdfDataList.get(i).length() > 16) {
                        setFieldValue(object, field, pdfDataList.get(i));
                        break;
                    }
                }
            }
        }
    }

    private void setFieldValueFromPattern(Object object, Field field, String content, List<String> patternList, MatcherType contractParam) {
        for (String pattern : patternList) {
            String value = WordMatcher.matcherValue(content, pattern, MatcherType.CONTRACT_PARAM);
            if (!EncodeUtil.isEmpty(value)) {
                setFieldValue(object, field, value);
                break;
            }
        }
    }

    private void setFieldValue(Object object, Field field, String value) {
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}

