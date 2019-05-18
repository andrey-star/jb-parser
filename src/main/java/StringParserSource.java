package main.java;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class StringParserSource extends ParserSource {
    private final String data;

    public StringParserSource(final String data) throws ParserException {
        this.data = data + END;
    }

    @Override
    protected char readChar() {
        return data.charAt(pos);
    }
}
