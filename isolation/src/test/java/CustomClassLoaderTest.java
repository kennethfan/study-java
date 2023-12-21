import io.github.kennethfan.isolation.loader.CustomClassLoader;
import org.junit.Assert;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class CustomClassLoaderTest {

    @Test
    public void test() throws ClassNotFoundException, MalformedURLException {
        String className = "com.google.common.collect.Lists";
        URL[] urls = new URL[]{
                new URL("jar:file:///Users/kenneth/.m2/repository/com/google/guava/guava/31.0.1-jre/guava-31.0.1-jre.jar!/")
        };
        CustomClassLoader customClassLoader = new CustomClassLoader(urls);
        Class<?> clazz2 = customClassLoader.loadClass(className);
        Assert.assertNotNull(clazz2);

        Class<?> clazz1 = getClass().getClassLoader().loadClass(className);
        Assert.assertNotNull(clazz1);

        Assert.assertNotSame(clazz1, clazz2);
    }
}
