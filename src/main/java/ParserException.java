package main.java;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ParserException extends Throwable {
    private final int pos;
    private final int line;

    public ParserException(final int line, final int pos, final String message) {
        super(message);
        this.line = line;
        this.pos = pos;
    }

    public int getPosition() {
        return pos;
    }

    public int getLine() {
        return line;
    }
}
