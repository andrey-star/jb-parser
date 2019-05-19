package main.java.parser;

public class StringParserSource extends ParserSource {
	private final String data;
	
	public StringParserSource(String data) {
		this.data = data + END;
	}
	
	@Override
	protected char readChar() {
		return data.charAt(pos);
	}
}
