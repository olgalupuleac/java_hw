package ru.spbau202.lupuleac.ThreadPool.LightFuture;

/**
 *A special exception to be thrown when the execution
 *  of the LightFuture ends with any other exception.
 */
public class LightExecutionException extends RuntimeException{
    public LightExecutionException(String msg){
        super(msg);
    }

    public LightExecutionException(){}
}
