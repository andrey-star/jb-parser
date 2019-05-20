package main.java.parser;

import main.java.exceptions.ParserException;

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
	
	boolean test(char c) {
		return getChar() == c;
	}
	
	boolean testNext(char c) {
		if (getChar() == c) {
			nextChar();
			return true;
		} else {
			return false;
		}
	}
	
	ParserException error() {
		return new ParserException("SYNTAX ERROR");
	}
}
