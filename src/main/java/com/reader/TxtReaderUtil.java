package com.reader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TxtReaderUtil {

    public static String readTxt(InputStream inputStream) {
        String result = "";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                result = result + "\n" + s;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
