package ru.spbau202.lupuleac.Maybe;

/**
 * Exception to be throw if someone tries to get value from Maybe which is Nothing.
 *
 * @see Maybe#get()
 */
public class AccessToNothingException extends RuntimeException {
}
