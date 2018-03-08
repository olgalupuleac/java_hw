package ru.spbau202.lupuleac.Injector;

import org.junit.Test;
import ru.spbau202.lupuleac.AmbiguousImplementationException.AmbigousImplementationException;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;
import static ru.spbau202.lupuleac.Injector.Injector.*;

public class InjectorTest {

    public static class ClassWithoutDependencies {
        public ClassWithoutDependencies(){
        }
    }

    public static class ClassWithOneDependency {
        public ClassWithoutDependencies a;
        public ClassWithOneDependency(ClassWithoutDependencies b){
            a = b;
        }
    }

    public interface Interface {
    }

    public static class ImplInterface implements Interface {
    }

    public static class ClassWithOneInterfaceDependency {

        public final Interface dependency;

        public ClassWithOneInterfaceDependency(Interface dependency) {
            this.dependency = dependency;
        }
    }

    @Test
    public void initializeClassWithoutDependencies() throws Exception {
        assertTrue(initialize(ClassWithoutDependencies.class.getName(),
                Collections.singletonList(ClassWithoutDependencies.class))
                instanceof ClassWithoutDependencies);
    }

    @Test
    public void initializeClassWithOneDependency() throws Exception {
        ArrayList<Class<?>> classes = new ArrayList<>();
        classes.add(ClassWithoutDependencies.class);
        classes.add(ClassWithOneDependency.class);
        Object o = initialize(ClassWithOneDependency.class.getName(), classes);
        assertTrue(o instanceof ClassWithOneDependency);
    }

    @Test
    public void initializeInterface() throws Exception {
        //System.out.println(Interface.class.isAssignableFrom(ImplInterface.class));
        assertTrue(initialize(Interface.class.getName(),
                Collections.singletonList(ImplInterface.class))
                instanceof Interface);
    }

    //@Test
   // public




}