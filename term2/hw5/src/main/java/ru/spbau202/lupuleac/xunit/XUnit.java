package ru.spbau202.lupuleac.xunit;

import ru.spbau202.lupuleac.annotations.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class XUnit {
    private ArrayList<Method> beforeTestMethods = new ArrayList<>();
    private ArrayList<Method> afterTestMethods = new ArrayList<>();
    private ArrayList<Method> beforeClassTestMethods = new ArrayList<>();
    private ArrayList<Method> afterClassTestMethods = new ArrayList<>();
    private ArrayList<Method> tests = new ArrayList<>();
    private Class<?> testClass;
    private int failed;
    private int passed;

    public XUnit(){
    }

    public void runTests(Class<?> test){
        clear();
        separateMethods(test.getMethods());

    }

    private void separateMethods(Method[] methods){
        for(Method method : methods){
            if(method.isAnnotationPresent(Before.class)){
                beforeTestMethods.add(method);
            }
            if(method.isAnnotationPresent(After.class)){
                afterTestMethods.add(method);
            }
            if(method.isAnnotationPresent(BeforeClass.class)){
                beforeClassTestMethods.add(method);
            }
            if(method.isAnnotationPresent(AfterClass.class)){
                afterClassTestMethods.add(method);
            }
            if(method.isAnnotationPresent(Test.class)){
                tests.add(method);
            }
        }
    }

    private void clear(){
        beforeClassTestMethods.clear();
        afterClassTestMethods.clear();
        beforeTestMethods.clear();
        afterTestMethods.clear();
        tests.clear();
        passed = 0;
        failed = 0;
    }

    private void launch() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Object instance = testClass.newInstance();
        for(Method method : beforeClassTestMethods){
                method.invoke(instance);
        }
        for(Method method : tests){
            for(Method beforeMethod : beforeTestMethods){
                beforeMethod.invoke(instance);
            }
            Test annotation = method.getAnnotation(Test.class);
            if(!annotation.ignore().equals("")){
                System.err.println("Method " + method.getName() + " is ignored.\n" + annotation.ignore());
                continue;
            }
            Exception exception = null;
            boolean expectedException = false;
            boolean res = true;
            try {
                method.invoke(instance);
            }
            catch (InvocationTargetException e){

            }
            catch (Exception e){
                if(!e.getCause().getClass().equals(annotation.expected())){
                    res = false;
                }
                else {
                    expectedException = true;
                }
            }
            if(res && annotation.expected().equals(Object.class) && !expectedException){
                res = false;
            }
        }

    }
}
