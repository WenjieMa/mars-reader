package com.reader;


import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class WordReaderUtil {

    public static List<String>  readDocxFile(InputStream is) throws IOException {
        List<String> contentList  = new ArrayList<>();
        XWPFDocument document = new XWPFDocument(is);
        XWPFWordExtractor extractor = new XWPFWordExtractor(document);

        // word文档所有的文本
        contentList.add(extractor.getText());
        // 页眉
        List<XWPFHeader> headerList = document.getHeaderList();
        StringBuffer headBuffer = new StringBuffer();
        for (XWPFHeader xwpfHeader: headerList){
            headBuffer.append(xwpfHeader.getText());
        }
        contentList.add(headBuffer.toString());

        // 页脚
        List<XWPFFooter> footerList = document.getFooterList();
        StringBuffer footBuffer = new StringBuffer();
        for (XWPFFooter xwpfFooter: footerList){
            footBuffer.append(xwpfFooter.getText());
        }
        contentList.add(footBuffer.toString());

        // 输出当前word文档的元数据信息，包括作者、文档的修改时间等。
        contentList.add(extractor.getMetadataTextExtractor().getText());

        return contentList;
    }

    public static List<String>  readDocFile(InputStream is) throws IOException {
        List<String> contentList  = new ArrayList<>();
        WordExtractor extractor = new WordExtractor(is);

        HWPFDocument document = new HWPFDocument(is);
        // word文档所有的文本
        contentList.add(extractor.getText());

        // 页眉
        contentList.add(extractor.getHeaderText());
        // 页脚
        contentList.add(extractor.getFooterText());

        // 输出当前word文档的元数据信息，包括作者、文档的修改时间等。
        contentList.add(extractor.getMetadataTextExtractor().getText());

        /*
        // 获取各个段落的文本
        System.out.println("=======================解析流程：每个段落信息=========================");
        String paraTexts[] = extractor.getParagraphText();
        for (int i = 0; i < paraTexts.length; i++) {
            System.out.println("------------------解析流程：段落" + (i + 1) + "----------------");
            System.out.println("Paragraph " + (i + 1) + " : " + paraTexts[i]);
        }


        // 当前word的一些信息
        printInfo(extractor.getSummaryInformation());

        // 当前word的一些信息
        printInfo(extractor.getDocSummaryInformation());
        */
        return contentList;
    }

    public static void closeStream(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 输出SummaryInfomation
     *
     * @param info
     */
    public static void printInfo(SummaryInformation info) {
        System.out.println("===================从getSummaryInformation中获取信息===============");
        // 作者
        System.out.println("---------------------作者----------------------");
        System.out.println(info.getAuthor());
        // 字符统计
        System.out.println("---------------------字符----------------------");
        System.out.println(info.getCharCount());
        // 页数
        System.out.println("---------------------页数----------------------");
        System.out.println(info.getPageCount());
        // 标题
        System.out.println("---------------------标题----------------------");
        System.out.println(info.getTitle());
        // 主题
        System.out.println("---------------------主题----------------------");
        System.out.println(info.getSubject());
    }

    /**
     * 输出DocumentSummaryInfomation
     *
     * @param info
     */
    public static void printInfo(DocumentSummaryInformation info) {
        System.out.println("===================从getDocSummaryInformation中获取信息===============");
        // 分类
        System.out.println("---------------------分类----------------------");
        System.out.println(info.getCategory());
        // 公司
        System.out.println("---------------------公司----------------------");
        System.out.println(info.getCompany());
    }


}

