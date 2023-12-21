package io.github.kennethfan.compiler;

import javax.tools.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by kenneth on 2023/5/28.
 */
public class RuntimeCompiler {

    private JavaCompiler javaCompiler;

    public RuntimeCompiler() {
        this.javaCompiler = ToolProvider.getSystemJavaCompiler();
    }

    public Class<?> compileAndLoad(String fullName, String sourceCode) throws ClassNotFoundException {
        DiagnosticCollector<? super JavaFileObject> diagnosticCollector = new DiagnosticCollector<>();
        JavaFileManager javaFileManager = new ClassFileManager(this.javaCompiler.getStandardFileManager(diagnosticCollector, null, null));

        List<JavaFileObject> javaFileObjectList = new ArrayList<>();
        javaFileObjectList.add(new JavaSourceObject(fullName, sourceCode));

        JavaCompiler.CompilationTask task = this.javaCompiler.getTask(null, javaFileManager, diagnosticCollector, null, null, javaFileObjectList);
        if (task.call()) {
            return javaFileManager.getClassLoader(null).loadClass(fullName);
        }

        System.out.println(diagnosticCollector.getDiagnostics().get(0).getLineNumber());
        System.out.println(diagnosticCollector.getDiagnostics().get(0).getColumnNumber());
        System.out.println(diagnosticCollector.getDiagnostics().get(0).getMessage(Locale.ENGLISH));
        System.out.println(diagnosticCollector.getDiagnostics().get(0).getSource());
        System.out.println(diagnosticCollector.getDiagnostics().get(0).getCode());

        return Class.forName(fullName);
    }
}
