package com.reader;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.io.IOException;

public class ImageReaderUtil {
    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\Windows10\\Desktop\\a.jpg";
        // 图片和语言库的存放路径
        String ocrPath = "E:\\InteliJWorkspace\\tessdata";
        // 图片路径
        File file = new File(path);
        System.out.println("本地文件路径：".concat(file.getPath()));
        // 创建ITesseract对象
        ITesseract instance = new Tesseract();
        // 设置训练库的位置
        instance.setDatapath(ocrPath);
        // 根据需求选择语言库 chi_sim ：简体中文， eng
        String result = null;
        try {
            // 识别开始获取时间戳
            long startTime = System.currentTimeMillis();
            // 图片识别
            result = instance.doOCR(file);
            // 识别结束时间戳
            long endTime = System.currentTimeMillis();
            System.out.println("Time is：" + (endTime - startTime) + " 毫秒");
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        // 识别信息
        System.out.println("result: ".concat(result));
    }
}
