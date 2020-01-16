package main.java.parser;

import main.java.exception.ParserException;

abstract class ParserSource {
	static final char END = '\0';
	
	int pos;
	private char c;
	
	protected abstract char readChar();
	
	char getChar() {
		return c;
	}
	
	char nextChar() {
		c = readChar();
		pos++;
		return c;
	}
	
	ParserException error() {
		return new ParserException("SYNTAX ERROR");
	}
}
