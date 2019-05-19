package main.java;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class StringParserSource extends ParserSource {
	private String data;
	
	public StringParserSource(final String data) {
		this.data = data + END;
	}
	
	public void setData(String s) {
		data = s + END;
	}
	
	@Override
	protected char readChar() {
		return data.charAt(pos);
	}
}
