package io.github.kennethfan.compiler;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

/**
 * Created by kenneth on 2023/5/28.
 */
class JavaClassObject extends SimpleJavaFileObject {

    private final String className;
    private final ByteArrayOutputStream byteArrayOutputStream;

    public JavaClassObject(String className) {
        super(URI.create("String:///" + className.replaceAll("\\.", "/") + Kind.CLASS.extension), Kind.CLASS);
        this.className = className;
        this.byteArrayOutputStream = new ByteArrayOutputStream();
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        return this.byteArrayOutputStream;
    }

    public String getClassName() {
        return className;
    }

    public byte[] getBytes() {
        return this.byteArrayOutputStream.toByteArray();
    }
}
