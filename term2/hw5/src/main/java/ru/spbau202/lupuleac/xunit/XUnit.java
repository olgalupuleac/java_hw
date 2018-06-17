package ru.spbau202.lupuleac.xunit;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.jetbrains.annotations.NotNull;
import ru.spbau202.lupuleac.annotations.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Class which runs all tests in the given class.
 */
public class XUnit {
    private ArrayList<Method> beforeTestMethods = new ArrayList<>();
    private ArrayList<Method> afterTestMethods = new ArrayList<>();
    private ArrayList<Method> beforeClassTestMethods = new ArrayList<>();
    private ArrayList<Method> afterClassTestMethods = new ArrayList<>();
    private ArrayList<Method> tests = new ArrayList<>();
    private HashMap<String, TestResult> testResults = new HashMap<>();
    private HashSet<String> methodsWithXUnitAnnotations = new HashSet<>();
    private Class<?> testClass;
    private int failed;
    private int passed;
    private int ignored;

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
            InstantiationException, TestAnnotationException {
        clear();
        separateMethods(testClass.getMethods());
        launch();
        testResults.forEach((key, value) -> value.print());
        printStatistics();
    }

    /**
     * Separates methods according to their annotations (when the methods should be invoked).
     *
     * @param methods are methods to be separated
     */
    private void separateMethods(Method[] methods) throws TestAnnotationException {
        for (Method method : methods) {
            if (method.isAnnotationPresent(Before.class)) {
                markAsAnnotated(method);
                beforeTestMethods.add(method);
            }
            if (method.isAnnotationPresent(After.class)) {
                markAsAnnotated(method);
                afterTestMethods.add(method);
            }
            if (method.isAnnotationPresent(BeforeClass.class)) {
                markAsAnnotated(method);
                beforeClassTestMethods.add(method);
            }
            if (method.isAnnotationPresent(AfterClass.class)) {
                markAsAnnotated(method);
                afterClassTestMethods.add(method);
            }
            if (method.isAnnotationPresent(Test.class)) {
                markAsAnnotated(method);
                tests.add(method);
            }
        }
    }

    /**
     * Checks if the method wasn't annotated twice.
     *
     * @param method is a method to be checked
     * @throws TestAnnotationException if the method repeated twice
     */
    private void markAsAnnotated(@NotNull Method method) throws TestAnnotationException {
        if (methodsWithXUnitAnnotations.contains(method.toGenericString())) {
            throw new TestAnnotationException("Method " + method.getName() + " has several XUnit annotations");
        }
        methodsWithXUnitAnnotations.add(method.toGenericString());
    }

    /**
     * Clears resources.
     */
    private void clear() {
        methodsWithXUnitAnnotations.clear();
        beforeClassTestMethods.clear();
        afterClassTestMethods.clear();
        beforeTestMethods.clear();
        afterTestMethods.clear();
        tests.clear();
        passed = 0;
        failed = 0;
    }

    /**
     * Invokes all tests and methods which should be invoked before/after.
     *
     * @throws IllegalAccessException    if it occurs during method (not test) invocation
     * @throws InstantiationException    if it occurs during method (not test) invocation
     * @throws InvocationTargetException if it occurs during method (not test) invocation
     */
    private void launch() throws IllegalAccessException,
            InstantiationException, InvocationTargetException {
        Object instance = testClass.newInstance();
        for (Method method : beforeClassTestMethods) {
            method.invoke(instance);
        }
        for (Method method : tests) {
            Test annotation = method.getAnnotation(Test.class);
            long start = System.currentTimeMillis();
            if (!annotation.ignore().equals("")) {
                testResults.put(method.getName(), new TestResult(TestStatus.IGNORED, method.getName(), 0, annotation.ignore()));
                ignored++;
                continue;
            }
            for (Method beforeMethod : beforeTestMethods) {
                beforeMethod.invoke(instance);
            }
            Exception exception = null;
            boolean exceptionExpected = !annotation.expected().equals(Object.class);
            boolean testPassed = true;
            try {
                method.invoke(instance);
            } catch (Exception e) {
                exception = e;
                if (!e.getCause().getClass().equals(annotation.expected())) {
                    testPassed = false;
                }
            }
            if (exceptionExpected && exception == null) {
                testPassed = false;
            }
            long end = System.currentTimeMillis();
            for (Method afterMethod : afterTestMethods) {
                afterMethod.invoke(instance);
            }
            if (testPassed) {
                passed++;
                testResults.put(method.getName(), new TestResult(
                        TestStatus.PASSED, method.getName(), end - start, null));
            } else {
                failed++;
                String reason;
                if (exceptionExpected && exception == null) {
                    reason = annotation.expected().getName() + " was expected.\n";
                } else {
                    reason = exception.getCause().getMessage();
                }
                testResults.put(method.getName(), new TestResult(
                        TestStatus.FAILED, method.getName(), end - start, reason));
            }
        }
        for (Method method : afterClassTestMethods) {
            method.invoke(instance);
        }
    }

    /**
     * Prints final statistic of test invocation.
     */
    private void printStatistics() {
        System.out.printf("Total number of tests: %d\nPassed: %d\nFailed: %d\nIgnored: %d\n", tests.size(),
                passed, failed, ignored);
    }

    /**
     * Class which represents results of test invocation.
     */
    static class TestResult {
        private TestStatus result;
        private String methodName;
        private long time;
        private String reason;

        private TestResult(TestStatus status, String name,
                           long time, String reason) {
            this.result = status;
            this.methodName = name;
            this.time = time;
            this.reason = reason;
        }

        long getTime() {
            return time;
        }

        String getMethodName() {
            return methodName;
        }

        TestStatus getResult() {
            return result;
        }


        String getReason() {
            return reason;
        }

        private void print() {
            if (result == TestStatus.IGNORED) {
                System.out.println("" +
                        "Method " + methodName + " is ignored.\nReason: " + reason);
            }
            if (result == TestStatus.FAILED) {
                System.out.printf("Test %s failed in %s.\n", methodName,
                        DurationFormatUtils.formatDurationWords(time,
                                true, true));
                System.out.println("Reason: " + reason);
            }
            if (result == TestStatus.PASSED) {
                System.out.printf("Test %s passed in %s.\n", methodName,
                        DurationFormatUtils.formatDurationWords(time,
                                true, true));
            }
        }
    }

    enum TestStatus {
        PASSED,
        FAILED,
        IGNORED
    }

    HashMap<String, TestResult> getTestResults() {
        return testResults;
    }
}
