package com.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class TxtReaderUtil {

    public static String txt2String(File file) {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
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

    public static void main(String[] args) {
        String path= "C:\\Users\\Windows10\\Desktop\\a.txt";
        File file = new File(path);
        System.out.println(txt2String(file));
    }

}
