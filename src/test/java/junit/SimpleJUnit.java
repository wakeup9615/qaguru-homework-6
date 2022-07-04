package junit;

import com.google.common.reflect.ClassPath;
import org.junit.jupiter.api.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Collectors;

public class SimpleJUnit {
    public static void main(String[] args) throws Exception {
        Set<? extends Class<?>> classes = ClassPath.from(ClassLoader.getSystemClassLoader())
                .getAllClasses()
                .stream()
                .filter(classInfo -> classInfo.getPackageName().contains("simpleTests"))
                .map(ClassPath.ClassInfo::load)
                .collect(Collectors.toSet());
        for (Class<?> aClass : classes) {
            runBeforeAll(aClass);
            Method[] methods = aClass.getDeclaredMethods();
            for (Method method : methods) {
                Test testAnnotation = method.getAnnotation(Test.class);
                if (testAnnotation != null) {
                    method.setAccessible(true);
                    try {
                        runBeforeEach(aClass);
                        method.invoke(aClass.getDeclaredConstructor().newInstance());
                    } catch (Throwable t) {
                        if (AssertionError.class.isAssignableFrom(t.getCause().getClass())) {
                            System.out.println("Тест: " + method.getName() + " желтый");
                        } else {
                            System.out.println("Тест: " + method.getName() + " красный");
                        }
                        continue;
                    }
                    System.out.println("Тест :" + method.getName() + " зеленый");
                    runAfterEach(aClass);
                }
            }
            runAfterAll(aClass);
        }
    }
    
    public static void runBeforeAll(Class<?> aClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods) {
            BeforeAll beforeAllAnnotation = method.getAnnotation(BeforeAll.class);
            if (beforeAllAnnotation != null) {
                method.setAccessible(true);
                method.invoke(aClass.getDeclaredConstructor().newInstance());
            }
        }
    }

    public static void runAfterAll(Class<?> aClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods) {
            AfterAll afterAllAnnotation = method.getAnnotation(AfterAll.class);
            if (afterAllAnnotation != null) {
                method.setAccessible(true);
                method.invoke(aClass.getDeclaredConstructor().newInstance());
            }
        }
    }

    public static void runBeforeEach(Class<?> aClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods) {
            BeforeEach beforeEachAnnotation = method.getAnnotation(BeforeEach.class);
            if (beforeEachAnnotation != null) {
                method.setAccessible(true);
                method.invoke(aClass.getDeclaredConstructor().newInstance());
            }
        }
    }

    public static void runAfterEach(Class<?> aClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods) {
            AfterEach afterEachEachAnnotation = method.getAnnotation(AfterEach.class);
            if (afterEachEachAnnotation != null) {
                method.setAccessible(true);
                method.invoke(aClass.getDeclaredConstructor().newInstance());
            }
        }
    }
}