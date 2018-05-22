package ru.spbau202.lupuleac.xunit;

import org.apache.commons.lang3.time.DurationFormatUtils;
import ru.spbau202.lupuleac.annotations.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Class which runs all tests in the given class.
 */
public class XUnit {
    private ArrayList<Method> beforeTestMethods = new ArrayList<>();
    private ArrayList<Method> afterTestMethods = new ArrayList<>();
    private ArrayList<Method> beforeClassTestMethods = new ArrayList<>();
    private ArrayList<Method> afterClassTestMethods = new ArrayList<>();
    private ArrayList<Method> tests = new ArrayList<>();
    private Class<?> testClass;
    private int failed;
    private int passed;

    public XUnit(String name) throws ClassNotFoundException {
        this.testClass = Class.forName(name);
    }

    /**
     * Runs all tests in class.
     *
     * @throws IllegalAccessException    if it occurs during method invocation
     * @throws InvocationTargetException if method which should be invoked before or after test fails.
     * @throws InstantiationException    if it occurs during method invocation
     */
    public void runTests() throws IllegalAccessException, InvocationTargetException,
            InstantiationException {
        clear();
        separateMethods(testClass.getMethods());
        launch();
        printStatistics();
    }

    private void separateMethods(Method[] methods) {
        for (Method method : methods) {
            if (method.isAnnotationPresent(Before.class)) {
                beforeTestMethods.add(method);
            }
            if (method.isAnnotationPresent(After.class)) {
                afterTestMethods.add(method);
            }
            if (method.isAnnotationPresent(BeforeClass.class)) {
                beforeClassTestMethods.add(method);
            }
            if (method.isAnnotationPresent(AfterClass.class)) {
                afterClassTestMethods.add(method);
            }
            if (method.isAnnotationPresent(Test.class)) {
                tests.add(method);
            }
        }
        tests.sort(Comparator.comparing(Method::getName));
    }

    private void clear() {
        beforeClassTestMethods.clear();
        afterClassTestMethods.clear();
        beforeTestMethods.clear();
        afterTestMethods.clear();
        tests.clear();
        passed = 0;
        failed = 0;
    }

    private void launch() throws IllegalAccessException,
            InstantiationException, InvocationTargetException {
        Object instance = testClass.newInstance();
        for (Method method : beforeClassTestMethods) {
            method.invoke(instance);
        }
        for (Method method : tests) {
            for (Method beforeMethod : beforeTestMethods) {
                beforeMethod.invoke(instance);
            }
            Test annotation = method.getAnnotation(Test.class);
            long start = System.currentTimeMillis();
            if (!annotation.ignore().equals("")) {
                System.out.println("Method " + method.getName() + " is ignored.\nReason: " + annotation.ignore());
                continue;
            }
            Exception exception = null;
            boolean exceptionExpected = !annotation.expected().equals(Object.class);
            boolean testResult = true;
            try {
                method.invoke(instance);
            } catch (Exception e) {
                exception = e;
                if (!e.getCause().getClass().equals(annotation.expected())) {
                    testResult = false;
                }
            }
            if (exceptionExpected && exception == null) {
                testResult = false;
            }
            long end = System.currentTimeMillis();
            for (Method afterMethod : afterTestMethods) {
                afterMethod.invoke(instance);
            }
            if (testResult) {
                passed++;
                System.out.printf("Test %s passed in %s.\n", method.getName(),
                        DurationFormatUtils.formatDurationWords(end - start,
                                true, true));
            } else {
                failed++;
                System.out.printf("Test %s failed in %s.\n", method.getName(),
                        DurationFormatUtils.formatDurationWords(end - start,
                                true, true));
                if (exceptionExpected && exception == null) {
                    System.out.printf(annotation.expected().getName() + " was expected.\n");
                } else {
                    System.out.println("Reason: " + exception.getCause().getMessage());
                }
            }
        }
        for (Method method : afterClassTestMethods) {
            method.invoke(instance);
        }
    }

    private void printStatistics() {
        System.out.printf("Total number of tests: %d\nPassed: %d\nFailed: %d\n", tests.size(),
                passed, failed);
    }

}
