package io.github.kennethfan.zip;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
public class ZipUtil {

    public static void main(String[] args) {
        String[] files = {"file1", "file2"};
        File zipFile = new File("archive.zip");

        try (ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(zipFile)) {
            for (String file : files) {
                ZipArchiveEntry entry = new ZipArchiveEntry(file);
                zaos.putArchiveEntry(entry);

                try (InputStream is = new ByteArrayInputStream(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8))) {
                    IOUtils.copy(is, zaos);
                }
                zaos.closeArchiveEntry();
            }
            zaos.finish();
            System.out.println("ZIP文件创建成功");
        } catch (Exception e) {
            log.error("ZIP文件创建失败", e);
        }
    }
}
