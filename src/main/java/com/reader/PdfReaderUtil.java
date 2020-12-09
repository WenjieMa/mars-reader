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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class PdfReaderUtil {

    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\Windows10\\Desktop\\a.pdf";
        File file = new File(path);
        PDDocument document = PDDocument.load(file);
        // 获取页码
        int pageNum = document.getNumberOfPages();

        // 读文本内容
        PDFTextStripper stripper = new PDFTextStripper();
        // 设置按顺序输出
        stripper.setSortByPosition(true);
        stripper.setStartPage(1);
        stripper.setEndPage(pageNum);
        String content = stripper.getText(document);

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
                        FileOutputStream out = new FileOutputStream("D:\\" + UUID.randomUUID() + ".jpg");
                        try {
                            ImageIO.write(image, "jpg", out);
                        } catch (IOException e) {
                        } finally {
                            try {
                                out.close();
                            } catch (IOException e) {
                            }
                        }
                    }
                }
            }

        }


        System.out.println(content);

    }

    public void pdfBoxReadPdf() {
/*
        File pdffile = new File("C:\\Users\\Windows10\\Desktop\\实验室设备维保服务合同.pdf");
        String content = "";
        PDDocument document = null;
        try {
            // 方式二：
            document = PDDocument.load(pdffile);
            // 获取页码
            int pages = document.getNumberOfPages();
            for (int i = 0; i < pages; i++) {
                PDPage page = document.getPages().get(i);

            }

            // 读文本内容
            PDFTextStripper stripper = new PDFTextStripper();
            // 设置按顺序输出
            stripper.getArticleStart();
            stripper.setSortByPosition(true);
            stripper.setStartPage(1);
            stripper.setEndPage(pages);
            content = stripper.getText(document);
            System.out.println(content);
        } catch (
                Exception e) {
            System.out.println(e);
        }
        */
    }
}

