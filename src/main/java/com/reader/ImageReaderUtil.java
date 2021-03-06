package com.reader;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageReaderUtil {

    public static final String OCR_PATH = "src/main/resources/tessdata";
    //chi_sim 中文  eng英文
    public static final String ZH_LANGUAGE = "chi_sim";

    public static final String EN_LANGUAGE = "eng";

    public static ITesseract instance = new Tesseract();

    public static volatile ImageReaderUtil imageReaderUtil;

    private ImageReaderUtil(){

    }

    public static ImageReaderUtil getInstance() {
        if(imageReaderUtil==null){
            synchronized (ImageReaderUtil.class){
                if(imageReaderUtil==null){
                    imageReaderUtil = new ImageReaderUtil();
                }
            }
        }
        return imageReaderUtil;
    }

    public  String readImage(byte[] imageData) throws IOException {
        BufferedImage img = ImageIO.read(new ByteArrayInputStream((imageData)));
        return readImage(img);
    }

    public  String readImage(byte[] imageData, String language) throws IOException {
        BufferedImage img = ImageIO.read(new ByteArrayInputStream((imageData)));
        return readImage(img,language);
    }

    public  String readImage(FileInputStream fileInputStream) throws IOException {
        byte[] bytes = EncodeUtil.copyToByteArray(fileInputStream);
        BufferedImage img = ImageIO.read(new ByteArrayInputStream((bytes)));
        return readImage(img);
    }

    public  String readImage(FileInputStream fileInputStream, String language) throws IOException {
        byte[] bytes = EncodeUtil.copyToByteArray(fileInputStream);
        BufferedImage img = ImageIO.read(new ByteArrayInputStream((bytes)));
        return readImage(img,language);
    }

    public  String readImage(BufferedImage img, String language) throws IOException {
        instance.setLanguage(language);
        String result = readResult(instance, img);
        return result;
    }

    public String readImage(BufferedImage img) throws IOException {
        instance.setLanguage(EN_LANGUAGE);//英文识别
        String result = readResult(instance, img);
        return result;
    }

    private  String readResult(ITesseract instance, BufferedImage img) {
        // 设置训练库的位置
        String fileName = this.getClass().getClassLoader().getResource("../../").getPath()+"resources/tessdata";
        fileName = fileName.substring(1,fileName.length()).replaceAll("/","//");
        instance.setDatapath(fileName);
        String result = null;
        try {
            // 识别开始获取时间戳
            //long startTime = System.currentTimeMillis();
            // 图片识别
            result = instance.doOCR(img);
            // 识别结束时间戳
            //long endTime = System.currentTimeMillis();
            //System.out.println("图片识别时间：" + (endTime - startTime) + " 毫秒");
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        // 识别信息
        return result;
    }
}
