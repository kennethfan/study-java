import io.github.kennethfan.compiler.RuntimeCompiler;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RuntimeCompilerTest {


    @Test
    public void test_static() throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String classStream = "public class TestClass {\n" +
                "\n" +
                "    public int add(int a, int b) {\n" +
                "        return a + b;\n" +
                "    }\n" +
                "\n" +
                "    public static double pow(double a, double b) {\n" +
                "        return Math.pow(a, b);\n" +
                "    }\n" +
                "}\n";

        Class<?> clazz = new RuntimeCompiler().compileAndLoad("TestClass", classStream);
        Method staticMethod = clazz.getMethod("pow", double.class, double.class);
        double a = 2.0d;
        double b = 3.0d;
        Assert.assertEquals(8.0d, staticMethod.invoke(null, a, b));
    }

    @Test
    public void test_normal() throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        String classStream = "public class TestClass {\n" +
                "\n" +
                "    public int add(int a, int b) {\n" +
                "        return a + b;\n" +
                "    }\n" +
                "\n" +
                "    public static double pow(double a, double b) {\n" +
                "        return Math.pow(a, b);\n" +
                "    }\n" +
                "}\n";

        Class<?> clazz = new RuntimeCompiler().compileAndLoad("TestClass", classStream);
        Method staticMethod = clazz.getMethod("add", int.class, int.class);
        Assert.assertEquals(8, staticMethod.invoke(clazz.newInstance(), 5, 3));
    }
}
