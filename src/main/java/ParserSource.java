package main.java;

import json.JsonException;

import java.io.IOException;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public abstract class ParserSource {
    public static char END = '\0';

    protected int pos;
    private char c;

    protected abstract char readChar() throws IOException;
    
    public char getChar() {
        return c;
    }

    public char nextChar() throws ParserException {
        try {
            c = readChar();
            pos++;
            return c;
        } catch (final IOException e) {
            throw error("Source read error", e.getMessage());
        }
    }
    
    public boolean test(final char c) {
        return getChar() == c;
    }
    
    public boolean testNext(final char c) throws ParserException {
        if (getChar() == c) {
            nextChar();
            return true;
        } else {
            return false;
        }
    }
    
    public ParserException error(String error, String expression) {
        return new ParserException(String.format("%s: %s", error, expression));
    }
    
    public ParserException error(String error) {
        return new ParserException(error);
    }
}
