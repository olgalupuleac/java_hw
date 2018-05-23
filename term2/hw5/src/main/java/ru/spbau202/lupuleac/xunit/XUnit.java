package ru.spbau202.lupuleac.xunit;

import org.apache.commons.lang3.time.DurationFormatUtils;
import ru.spbau202.lupuleac.annotations.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

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
            InstantiationException {
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
    }

    /**
     * Clears resources.
     */
    private void clear() {
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
