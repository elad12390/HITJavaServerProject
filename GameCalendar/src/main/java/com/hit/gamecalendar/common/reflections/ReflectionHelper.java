package main.java.com.hit.gamecalendar.common.reflections;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ReflectionHelper {
    public static Class<?>[] getClassesWithAnnotation(String packageName, Class<? extends Annotation> a) {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = null;
        try {
            resources = classLoader.getResources(path);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            try {
                URI resource = resources.nextElement().toURI();
                dirs.add(new File(resource));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        List<Class<?>> classes = new ArrayList<Class<?>>();
        for (File directory : dirs)
            classes.addAll(findClasses(directory, packageName, a));

        return classes.toArray(new Class[classes.size()]);

    }

    private static List<Class<?>> findClasses(File directory, String packageName, Class<? extends Annotation> annotation) {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists())
            return classes;

        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, (!packageName.equals("") ? packageName + "." : packageName) + file.getName(), annotation));
            } else if (file.getName().endsWith(".class"))
                try {
                    var c = Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6));
                    if (c.isAnnotationPresent(annotation))
                        classes.add(c);
                } catch (ClassNotFoundException e) {
                    System.err.println(e.getMessage());
                }
        }
        return classes;
    }

}
