package io.github.kennethfan.compiler;

import javax.tools.SimpleJavaFileObject;
import java.io.IOException;
import java.net.URI;

/**
 * Created by kenneth on 2023/5/28.
 */
class JavaSourceObject extends SimpleJavaFileObject {
    /**
     * 类名
     */
    private final String className;
    /**
     * 源代码
     */
    private final String sourceCode;

    public JavaSourceObject(String className, String sourceCode) {
        super(URI.create("String:///" + className.replaceAll("\\.", ".") + Kind.SOURCE.extension), Kind.SOURCE);
        this.className = className;
        this.sourceCode = sourceCode;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return this.sourceCode;
    }

    public String getClassName() {
        return className;
    }
}
