package ru.spbau202.lupuleac.Injector;

import ru.spbau202.lupuleac.AmbiguousImplementationException.AmbigousImplementationException;
import ru.spbau202.lupuleac.ImplementationNotFoundException.ImplementationNotFoundException;
import ru.spbau202.lupuleac.InjectionCycleException.InjectionCycleException;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Class with one static method which gets class name
 * and all dependencies including this class and returns the instance of this class.
 */
public class Injector {
    public static Object initialize(String rootClassName, Collection<Class<?>> classes) throws Exception {
        //System.out.println(rootClassName);
        HashMap<Class<?>, Object> instances = new HashMap<>();
        HashSet<Class<?>> visited = new HashSet<>();
        for (Class<?> cl : classes) {
            //System.out.println(cl);
            //System.out.println(Class.forName(rootClassName));
            if(Class.forName(rootClassName).isAssignableFrom(cl)){
                //System.out.println("hello");
                return initializeUsingExcitedClasses(cl, classes, instances, visited);
            }
        }
        throw new ImplementationNotFoundException();
    }

    private static Object initializeUsingExcitedClasses(Class<?> cl,
                                                        Collection<Class<?>> classes,
                                                        HashMap<Class<?>, Object> instances,
                                                        HashSet<Class<?>> visited) throws Exception {
        if (visited.contains(cl)) {
            throw new InjectionCycleException();
        }
        boolean isInDependencies = false;
        for(Class<?> anotherClass : classes){
            if(cl.isAssignableFrom(anotherClass)){
                isInDependencies = true;
            }
        }
        if(!isInDependencies){
            throw new ImplementationNotFoundException();
        }
        visited.add(cl);
        if (cl.getDeclaredConstructors().length > 1) {
            throw new AmbigousImplementationException();
        }
        Constructor<?> constructor = cl.getDeclaredConstructors()[0];
        Class<?>[] types = constructor.getParameterTypes();
        Object[] parameters = new Object[types.length];
        for (int i = 0; i < types.length; i++) {
            Object instance = null;
            for(Map.Entry<Class<?>, Object>  exitingClass : instances.entrySet()){
                if(cl.isAssignableFrom(exitingClass.getKey())){
                    if(instance != null){
                        throw new AmbigousImplementationException();
                    }
                    instance = exitingClass.getValue();
                }
            }
            if (instance != null) {
                parameters[i] = instance;
            } else {
                parameters[i] = initializeUsingExcitedClasses(types[i], classes, instances, visited);
            }
        }
        instances.put(cl, constructor.newInstance(parameters));
        return instances.get(cl);
    }
}
