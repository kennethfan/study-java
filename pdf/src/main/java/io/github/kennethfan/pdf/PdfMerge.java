package io.github.kennethfan.pdf;

import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.File;
import java.io.IOException;

public class PdfMerge {

    public static void main(String[] args) {
        File mergedFile = new File("merged.pdf"); // 输出文件
        File file1 = new File("/Users/kenneth/Downloads/44060124_8125621143.PDF"); // 第一个PDF文件
        File file2 = new File("/Users/kenneth/Downloads/44060124_8134044649.PDF"); // 第一个PDF文件
        File file3 = new File("/Users/kenneth/Downloads/44060124_8134044659.PDF"); // 第一个PDF文件

        try {
            // 使用PDFMergerUtility合并PDF文件
            PDFMergerUtility mergerUtility = new PDFMergerUtility();
            mergerUtility.addSource(file1);
            mergerUtility.addSource(file2);
            mergerUtility.addSource(file3);
            mergerUtility.setDestinationFileName(mergedFile.getAbsolutePath());
            mergerUtility.mergeDocuments(null); // 使用null作为参数可以合并所有页面
            System.out.println("PDF files were merged successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while merging PDF files.");
        }
    }
}
