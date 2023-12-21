package io.github.kennethfan.compiler;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.security.SecureClassLoader;

/**
 * Created by kenneth on 2023/5/28.
 */
public class ClassFileManager extends ForwardingJavaFileManager<JavaFileManager> {

    private JavaClassObject javaClassObject;

    public ClassFileManager(JavaFileManager fileManager) {
        super(fileManager);
    }

    @Override
    public ClassLoader getClassLoader(Location location) {
        return new SecureClassLoader() {
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                byte[] bytes = javaClassObject.getBytes();
                return super.defineClass(name, bytes, 0, bytes.length);
            }
        };
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        this.javaClassObject = new JavaClassObject(className);
        return this.javaClassObject;
    }
}
