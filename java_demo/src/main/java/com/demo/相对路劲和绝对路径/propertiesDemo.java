package com.demo.相对路劲和绝对路径;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 */

public class propertiesDemo {
    private static String PROPERTIES_NAME = "application.properties";

    public static void main(String[] args) throws FileNotFoundException {

        Properties pop = new Properties();
        System.out.println(" pop.getClass()==" + pop.getClass());
        InputStream resourceAsStream = propertiesDemo.class.getResourceAsStream("/");
        System.out.println("resourceAsStream==" + resourceAsStream);

        System.out.println("thread ==>" + Thread.currentThread().getContextClassLoader().getResource(""));
        System.out.println("propertiesDemo==" + propertiesDemo.class.getClassLoader().getResource(""));
        System.out.println("ClassLoader==" + ClassLoader.getSystemResource(""));
        System.out.println(propertiesDemo.class.getResource(""));
        System.out.println(propertiesDemo.class.getResource("/")); //Class文件所在路径
        System.out.println(new File("/").getAbsolutePath());
        System.out.println(System.getProperty("user.dir"));

        //相对路劲;
        String path = propertiesDemo.class.getResource("/").getPath() + PROPERTIES_NAME;
        System.out.println("path==" + path);

    }
}
