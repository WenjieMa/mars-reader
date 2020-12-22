package com.filter;

import com.reader.PdfReaderUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EncodeUtil {
    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\Windows10\\Desktop\\实验室设备维保服务合同.pdf";
        FileInputStream inputStream = new FileInputStream(path);
        List<String> dataList = PdfReaderUtil.readPdf(inputStream);
        //String pattern = ".*\\u8bbe\\u5907\\u7ef4\\u62a4\\u5408\\u540c";
        String pattern = ".*" + gbEncoding("设备维护合同");
        System.out.println(pattern);
        String paramName = "";
        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(dataList.get(0));
        if (matcher.find()) {
            paramName = matcher.group(0).replaceAll("\\[", "").replaceAll("\\]", "");
        }

        System.out.println(paramName);


    }

    public static String matherZHValue(String word){
        String value = "";
        return value;
    };

    public static String gbEncoding(final String gbString) {
        char[] utfBytes = gbString.toCharArray();
        String unicodeBytes = "";
        for (int i = 0; i < utfBytes.length; i++) {
            String hexB = Integer.toHexString(utfBytes[i]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }

    public static String decodeUnicode(final String dataStr) {
        int start = 0;
        int end = 0;
        final StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
            buffer.append(new Character(letter).toString());
            start = end;
        }
        return buffer.toString();
    }
}
