package main.java;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class EvaluatingException extends Throwable {

    public EvaluatingException(final String error, String name) {
        super(String.format("%s %s", error, name));
    }

}
