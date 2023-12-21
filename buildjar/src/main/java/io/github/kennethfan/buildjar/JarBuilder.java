package io.github.kennethfan.buildjar;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

/**
 * Created by kenneth on 2023/5/28.
 */
public class JarBuilder {

    public File createJar(Class<?> clazz) throws IOException, URISyntaxException {
        String className = clazz.getName();
        String base = className.substring(0, className.lastIndexOf("."));
        base = base.replaceAll("\\.", "/");
        URL root = clazz.getClassLoader().getResource("");

        final File jar = File.createTempFile("storm", ".jar", new File("/Users/kenneth"));

        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put("Manifest-Version", "1.0");
        try (JarOutputStream out = new JarOutputStream(new FileOutputStream(jar), manifest)) {
            File path = new File(root.toURI());
            writeBaseFile(out, path, base);
            out.flush();
        }

        return jar;
    }

    private void writeBaseFile(JarOutputStream out, File file, String base) throws IOException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (base.length() > 0) {
                base += "/";
            }

            for (File f : files) {
                writeBaseFile(out, f, base + f.getName());
            }

            return;
        }

        out.putNextEntry(new JarEntry(base));
        try (FileInputStream in = new FileInputStream(file)) {

            byte[] buffer = new byte[1024];
            int n = in.read(buffer);
            while (n != -1) {
                out.write(buffer, 0, n);
                n = in.read(buffer);
            }
        }
    }
}
