package micro.qa.microkafka.utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class MethodInvoker {

    private static URL classScanner(String basePackage) {
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        String path = basePackage.replaceAll("\\.", "/");
        return loader.getResource(path);
    }

    public static void run(String basePackage) throws URISyntaxException {
        traverseDirectory(Path.of(classScanner(basePackage).toURI()));
    }

//    private static void traverseDirectory(Path path) throws ClassNotFoundException {
//        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
//            for (Path entry : stream) {
//                if (!Files.isDirectory(entry)) {
//                    String fileName = entry.getFileName().toString();
//                    if (fileName.endsWith(".class")) {
//                        String filePath = entry.toAbsolutePath().toString();
//                        String pathToClass = filePath.replace(System.getProperty("user.dir").concat("\\target\\test-classes\\"), "")
//                                .replace(".class", "").replaceAll("\\\\", ".");
//                        Class classObject = Class.forName(pathToClass);
//                        for (Method method : classObject.getDeclaredMethods()) {
//                            if (method.isAnnotationPresent(PrintMyName.class)) {
//                                System.out.println("Метод помеченный аннотацией называется - " + method.getName());
//                            }
//                        }
//                    }
//                } else {
//                    traverseDirectory(entry);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private static void traverseDirectory(Path path) {
        try {
            Files.walk(path).forEach(MethodInvoker::execute);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void execute(Path entry) {
        String fileName = entry.getFileName().toString();
        if (fileName.endsWith(".class")) {
            String filePath = entry.toAbsolutePath().toString();
            String pathToClass = filePath.replaceAll((".*\\\\target\\\\test-classes\\\\"), "")
                    .replace(".class", "").replaceAll("\\\\", ".");
            Class classObject = null;
            try {
                classObject = Class.forName(pathToClass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (classObject != null) {
                for (Method method : classObject.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(Execute.class)) {
                        Parameter[] parameters = method.getParameters();
                        Object obj = null;
                        try {
                            obj = classObject.getDeclaredConstructor().newInstance();
                            if (parameters.length > 0) {
                                method.invoke(obj, parameters);
                            } else {
                                method.invoke(obj);
                            }
                        } catch (InstantiationException | IllegalAccessException
                                | NoSuchMethodException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
