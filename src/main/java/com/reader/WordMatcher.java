package com.reader;

import com.constants.BooleanConstant;
import com.constants.MatcherType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordMatcher {

    public static String matcherValue(String content, String word, MatcherType MATCHER_TYPE) {
        String value = null;
        switch (MATCHER_TYPE) {
            case CONTRACT_NAME:
                value = contactNameMatcher(content, word);
                break;
            case CONTRACT_CODE_BAR:
                value = contractCodeBarMatcher(content, word);
                break;
            case CONTRACT_PARAM:
                value = contractParamMatcher(content, word);
                break;
            case CONTRACT_MONEY:
                value = contractMoneyMatcher(content, word);
                break;
            default:
                value = BooleanConstant.NO.getName();
                break;
        }
        if (BooleanConstant.NO.getName().equals(value)) {
            return "";
        } else {
            return value;
        }
    }

    private static String contractMoneyMatcher(String content, String word) {
        return contractParamMatcher(content, word).replaceAll(",", "").replaceAll("，", "");
    }

    private static String matcherData(String content, String pattern) {
        String paramName = "";
        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(content);
        if (matcher.find()) {
            paramName = matcher.group(0).replaceAll("\\[", "").replaceAll("\\]", "");
            return EncodeUtil.toNoSpaceAndDBC(paramName);
        }
        return BooleanConstant.NO.getName();
    }


    private static String contractParamMatcher(String content, String word) {
        String pattern = word;
        String value = matcherData(content, pattern);
        value = value.replaceAll("\\:", "").replaceAll("\\：", "");
        return value;
    }

    private static String contractCodeBarMatcher(String content, String word) {
        String pattern = word;
        String value = matcherData(content, pattern);
        return value;
    }

    private static String contactNameMatcher(String content, String word) {
        String pattern = ".*" + EncodeUtil.gbEncoding(word);
        if (content.contains(word)) {
            content = content.substring(0, content.indexOf(word) + word.length());
        }
        return matcherData(content, pattern);
    }

}
