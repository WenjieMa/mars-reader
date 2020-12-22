package com.reader;


import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
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
                        data.add(ImageReaderUtil.readImage(image));
                    }
                }
            }

        }

        return data;

    }
}

