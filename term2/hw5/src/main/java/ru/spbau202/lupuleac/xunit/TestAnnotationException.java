package ru.spbau202.lupuleac.xunit;

/**
 * Exception which occurs if a method has several XUnit annotations.
 */
public class TestAnnotationException extends Exception {
    public TestAnnotationException(){
        super();
    }

    public TestAnnotationException(String msg){
        super(msg);
    }
}
