package ru.spbau202.lupuleac.Reflector;

import com.google.common.base.Defaults;
import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import org.apache.commons.lang3.ClassUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Utility for printing class structure.
 * Takes class as a parameter and creates a valid file, which contains definition
 * of this class with all fields, methods, generic parameters and inner and nested classes.
 */
public class Reflector {
    /**
     * Takes the class and creates the .java file in the directory relevant to its package,
     * which contains the class definition with all methods, fields and inner classes.
     * @param someClass is a class which structure is to be printed
     */
    public static void printStructure(@NotNull Class<?> someClass) {
        String dirname = "src//main//java//" + getClassPath(someClass);
        File dir = new File(dirname);
        dir.mkdirs();
        File resultingClass = new File(dirname + "//"
                + someClass.getSimpleName() + ".java");
        StringBuilder code = new StringBuilder();
        appendPackage(someClass, code);
        appendClass(someClass, code);
        try (PrintStream out = new PrintStream(resultingClass)) {
            out.println(new Formatter().formatSource(code.toString()));
        } catch (IOException e) {
            System.err.println("Error while writing file.");
        }
        catch(FormatterException e) {
            System.err.println(e.diagnostics());
            System.out.println(code.toString());
        }
    }

    /**
     * Takes int as a parameter and appends the string containing
     * all modifiers relevant to the given parameter to the given StringBuilder.
     * @param mod is an integer relevant to the given modifiers.
     * @param code is a StringBuilder where the result will be appended
     */
    private static void appendModifiers(int mod, @NotNull StringBuilder code) {
        String modifiers = Modifier.toString(mod);
        code.append(modifiers);
        if (!modifiers.equals("")) {
            code.append(" ");
        }
    }

    /**
     * Transforms the array of generic parameters into the
     * string representation of generic declaration.
     * @param generics is an array of generic parameters
     * @return string representation of generic parameters
     */
    @NotNull
    private static String genericsToString(@NotNull TypeVariable<?>[] generics) {
        return Arrays.stream(generics).map(
                t -> t.getTypeName() + " extends " +
                        Arrays.stream(t.getBounds()).map(Type::getTypeName).filter(
                                x -> !x.equals("java.lang.Object")
                        ).collect(Collectors.joining(" & "))).map(x -> x.endsWith("extends ")
                ? x.replace(" extends ", "") : x).collect(
                Collectors.joining(", ", "<", ">"));
    }

    /**
     * Gets class, extracts its package and transforms it to the path.
     * @param someClass
     * @return
     */
    @NotNull
    private static String getClassPath(@NotNull Class<?> someClass) {
        return ClassUtils.getPackageCanonicalName(someClass).replace(".", "//");
    }

    /**
     *
     * @param field
     * @param code
     */
    private static void appendField(Field field, StringBuilder code) {
        appendModifiers(field.getModifiers(), code);
        Type genericFieldType = field.getGenericType();
        code.append(genericFieldType.getTypeName());
        if(genericFieldType.getTypeName().contains("$")){
            System.out.println(genericFieldType.getTypeName());
        }
        code.append(" ");
        code.append(field.getName());
        code.append(" =");
        code.append(getDefaultValue(field.getType()));
    }

    /**
     *
     * @param executable
     * @return
     */
    private static String parametersToString(Executable executable) {
        Type[] types = executable.getGenericParameterTypes();
        Parameter[] parameters = executable.getParameters();
        String[] args = new String[types.length];
        for (int i = 0; i < types.length; i++) {
            args[i] = types[i].getTypeName() + " " + parameters[i].getName();
        }
        return Arrays.stream(args).map(x -> x.replace("$", "$")).collect(
                Collectors.joining(", ", "(", ")")
        );
    }

    /**
     *
     * @param method
     * @param code
     */
    private static void appendMethod(Method method, StringBuilder code) {
        appendModifiers(method.getModifiers(), code);
        TypeVariable<Method>[] generics = method.getTypeParameters();
        if (generics.length != 0) {
            code.append(genericsToString(generics));
            code.append(" ");
        }
        code.append(method.getGenericReturnType()
                .getTypeName().replace("$", "."));
        code.append(" ");
        code.append(method.getName());
        code.append(parametersToString(method));
        Type[] exceptions = method.getGenericExceptionTypes();
        if (exceptions.length != 0) {
            code.append(" throws ");
            code.append(joinTypes(exceptions));
        }
        code.append("{\n");
        code.append(methodBody(method));
        code.append("}\n");
    }

    /**
     *
     * @param constructor
     * @param someClass
     * @param code
     */
    private static void appendConstructor(Constructor<?> constructor,
                                          Class<?> someClass, StringBuilder code) {
        appendModifiers(constructor.getModifiers(), code);
        TypeVariable<? extends Constructor<?>>[] generics = constructor.getTypeParameters();
        if (generics.length != 0) {
            code.append(genericsToString(generics));
            code.append(" ");
        }
        code.append(someClass.getSimpleName());
        code.append(parametersToString(constructor));
        code.append("{\n}\n");
    }

    /**
     *
     * @param method
     * @return
     */
    private static String methodBody(Method method) {
        return "  return " + getDefaultValue(method.getReturnType());
    }

    /**
     *
     * @param classMember
     * @return
     */
    private static String getDefaultValue(Class<?> classMember){
        if (classMember.getName().equals("void")) {
            return ";";
        }
        if (classMember.getName().equals("float")){
            return " 0;";
        }
        return " " + Defaults.defaultValue(classMember) + ";\n";
    }

    /**
     *
     * @param someClass
     * @param code
     */
    private static void appendPackage(Class<?> someClass, StringBuilder code) {
        code.append("package ");
        code.append(ClassUtils.getPackageCanonicalName(someClass));
        code.append(";\n");
    }

    /**
     *
     * @param types
     * @return
     */
    private static String joinTypes(Type[] types) {
        return Arrays.stream(types).map(Type::getTypeName).collect(
                Collectors.joining(", ")
        );
    }

    /**
     *
     * @param someClass
     * @param code
     */
    private static void appendDeclaration(Class<?> someClass, StringBuilder code) {
        appendModifiers(someClass.getModifiers(), code);
        code.append("class ");
        code.append(someClass.getSimpleName());
        TypeVariable<? extends Class<?>>[] generics = someClass.getTypeParameters();
        if (generics.length != 0) {
            code.append(genericsToString(generics));
        }
        if (!someClass.getSuperclass().equals(Object.class)) {
            code.append(" extends ");
            code.append(someClass.getGenericSuperclass());
            System.out.println(someClass.getGenericSuperclass());
        }
        Type[] interfaces = someClass.getGenericInterfaces();
        if (interfaces.length != 0) {
            code.append(" implements ");
            code.append(joinTypes(interfaces));
        }
        code.append(" {\n");
    }

    /**
     *
     * @param someClass
     * @param code
     */
    private static void appendClass(Class<?> someClass, StringBuilder code) {
        appendDeclaration(someClass, code);
        Field[] fields = someClass.getDeclaredFields();
        for (Field field : fields) {
            appendField(field, code);
        }
        Constructor<?>[] constructors = someClass.getConstructors();
        for (Constructor<?> constructor : constructors) {
            appendConstructor(constructor, someClass, code);
        }
        Method[] methods = someClass.getDeclaredMethods();
        for (Method method : methods) {
            appendMethod(method, code);
        }
        Class<?>[] classes = someClass.getDeclaredClasses();
        for (Class<?> declaredClass : classes) {
            appendClass(declaredClass, code);
        }
        code.append("}\n");
    }
}
