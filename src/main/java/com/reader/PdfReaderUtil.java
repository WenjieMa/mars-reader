package com.reader;


import com.encode.EncodeUtil;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PdfReaderUtil {

    public static List<String> readPdf(InputStream inputStream) throws IOException {
        List<String> data = new ArrayList<String>();
        PDDocument document = PDDocument.load(inputStream);
        // 获取页码
        int pageNum = document.getNumberOfPages();

        // 读文本内容
        PDFTextStripper stripper = new PDFTextStripper();
        // 设置按顺序输出
        stripper.setSortByPosition(true);
        stripper.setStartPage(1);
        stripper.setEndPage(pageNum);
        String content = stripper.getText(document);
        data.add(content);

        addImageData(data,pageNum,document,stripper);

        return data;

    }

    private static void addImageData(List<String> data, int pageNum, PDDocument document, PDFTextStripper stripper) throws IOException {
        for (int i = 0; i < pageNum; i++) {
            PDPage page = document.getPage(i);
            PDResources resources = page.getResources();
            Iterable<COSName> cosNames = resources.getXObjectNames();
            if (cosNames != null) {
                Iterator<COSName> cosNamesIter = cosNames.iterator();
                while (cosNamesIter.hasNext()) {
                    COSName cosName = cosNamesIter.next();
                    if (resources.isImageXObject(cosName)) {
                        PDImageXObject imageXObject = (PDImageXObject) resources.getXObject(cosName);
                        BufferedImage image = imageXObject.getImage();
                        String imageData = ImageReaderUtil.readImage(image);
                        data.add(imageData);
                    }
                }
            }

        }
    }

    public static List<String> readPdfTable(InputStream inputStream) throws IOException {
        List<String> data = new ArrayList<String>();

        PDDocument document = PDDocument.load(inputStream);
        // 获取页码
        int pageNum = document.getNumberOfPages();


        PDFTextStripperByArea stripper = new PDFTextStripperByArea();
        stripper.setSortByPosition(true);
        stripper.setWordSeparator("|");
        //	      stripper.setLineSeparator("#");
        //划定区域
        Rectangle rect= new Rectangle(0, 0, 10000, 10000);
        stripper.addRegion("area", rect);
        String tempData = null;
        String content = "";
        for (int i = 0; i < pageNum; i++) {
            PDPage page = document.getPage(i);
            stripper.extractRegions(page);
            i++;
            //获取区域的text
            tempData = stripper.getTextForRegion("area");
//	         data = data.trim();
            String[] datas = tempData.split("\r\n");
            //对文本进行分行处理
            for( i = 0; i<datas.length; ++i){
                String[] str = datas[i].split(" ");
                for( int j = 0; j<str.length; ++j) {
                    content+=EncodeUtil.toNoSpaceAndDBC(str[j])+"\n";
                }

            }
        }

        data.add(content);
        addImageData(data,pageNum,document,stripper);
        document.close();
        return data;

    }



}

