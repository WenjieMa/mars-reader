package com.reader;


import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;

import java.io.*;
import java.util.List;

public class WordReaderUtil {

    public static void main(String[] args) throws IOException {
        //        String path = "C:\\Users\\Windows10\\Desktop\\天翼移动通信业务合同.doc";
        String path = "C:\\Users\\Windows10\\Desktop\\a.docx";
        InputStream is = new FileInputStream(path);

        if (path.endsWith(".doc")) {
            readDocFile(is);
        }else if (path.endsWith(".docx")){
            readDocxFile(is);
        }
        closeStream(is);
    }

    private static void readDocxFile(InputStream is) throws IOException {
        XWPFDocument document = new XWPFDocument(is);
        XWPFWordExtractor extractor = new XWPFWordExtractor(document);

        // word文档所有的文本
        System.out.println("---------------解析流程：文档中所有文本----------------");
        System.out.println(extractor.getText());
        // 页眉
        System.out.println("-------------------解析流程：页眉-----------------");
        List<XWPFHeader> headerList = document.getHeaderList();
        for (XWPFHeader xwpfHeader: headerList){
            System.out.println(xwpfHeader.getText());//页眉
        }

        // 页脚
        System.out.println("------------------解析流程：页脚------------------");
        List<XWPFFooter> footerList = document.getFooterList();

        for (XWPFFooter xwpfFooter: footerList){
            System.out.println(xwpfFooter.getText());//页脚
        }

        // 输出当前word文档的元数据信息，包括作者、文档的修改时间等。
        System.out.println("------------------解析流程：元数据信息-------------------");
        System.out.println(extractor.getMetadataTextExtractor().getText());


    }

    private static void readDocFile(InputStream is) throws IOException {
        WordExtractor extractor = new WordExtractor(is);

        HWPFDocument document = new HWPFDocument(is);
        // word文档所有的文本
        System.out.println("---------------解析流程：文档中所有文本----------------");
        System.out.println(extractor.getText());

        // 页眉
        System.out.println("-------------------解析流程：页眉-----------------");
        System.out.println(extractor.getHeaderText());

        // 页脚
        System.out.println("------------------解析流程：页脚------------------");
        System.out.println(extractor.getFooterText());

        // 输出当前word文档的元数据信息，包括作者、文档的修改时间等。
        System.out.println("------------------解析流程：元数据信息-------------------");
        System.out.println(extractor.getMetadataTextExtractor().getText());

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
    }

    private static void closeStream(InputStream is) {
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
    private static void printInfo(SummaryInformation info) {
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
    private static void printInfo(DocumentSummaryInformation info) {
        System.out.println("===================从getDocSummaryInformation中获取信息===============");
        // 分类
        System.out.println("---------------------分类----------------------");
        System.out.println(info.getCategory());
        // 公司
        System.out.println("---------------------公司----------------------");
        System.out.println(info.getCompany());
    }


}

