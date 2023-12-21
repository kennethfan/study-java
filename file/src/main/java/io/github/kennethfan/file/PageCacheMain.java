package io.github.kennethfan.file;

import java.io.*;

public class PageCacheMain {

    public static void main(String[] args) throws IOException {
        long begin, end;

        int[] sizes = {32, 64, 128, 256, 512, 1024};

        File file;
        for (int size : sizes) {
            file = new File("data2.txt");
            if (file.exists()) {
                file.delete();
            }
            begin = System.currentTimeMillis();
            stream(size);
            end = System.currentTimeMillis();
            System.out.println("stream perSize=" + size + " cost=" + (end - begin));

            file = new File("data4.txt");
            if (file.exists()) {
                file.delete();
            }
            begin = System.currentTimeMillis();
            pageCache(size);
            end = System.currentTimeMillis();
            System.out.println("pageCache perSize=" + size + " cost=" + (end - begin));
        }
    }

    private static void stream(int perSize) throws IOException {
        byte[] bytes = new byte[perSize];
        try (FileInputStream inputStream = new FileInputStream("data1.txt"); FileOutputStream outputStream = new FileOutputStream("data2.txt")) {
            while (inputStream.available() > 0) {
                inputStream.read(bytes);
                outputStream.write(bytes);
            }
        }
    }

    private static void pageCache(int perSize) throws IOException {
        byte[] bytes = new byte[perSize];
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream("data3.txt")); BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream("data4.txt"))) {
            while (inputStream.available() > 0) {
                inputStream.read(bytes);
                outputStream.write(bytes);
            }
        }
    }
}
