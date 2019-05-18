package main.java;

import json.JsonException;

import java.io.IOException;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public abstract class ParserSource {
    public static char END = '\0';

    protected int pos;
    protected int line = 1;
    protected int posInLine;
    private char c;

    protected abstract char readChar() throws IOException;

    public char getChar() {
        return c;
    }

    public char nextChar() throws ParserException {
        try {
            if (c == '\n') {
                line++;
                posInLine = 0;
            }
            c = readChar();
            pos++;
            posInLine++;
            return c;
        } catch (final IOException e) {
            throw error("Source read error", e.getMessage());
        }
    }

    public ParserException error(String error, String expression) {
        return new ParserException(line, posInLine, String.format("%s %d:%s", error, line, expression));
    }
    
    public ParserException error(String error) {
        return new ParserException(line, posInLine, error);
    }
}
