package main.java;

import main.java.element.*;

public class Parser {
	
	private final ParserSource source;
	
	public Parser(ParserSource source) {
		this.source = source;
	}
	
	public Expression parse() throws ParserException {
		source.nextChar();
		Expression result = parseExpression();
		return result;
	}
	
	private Expression parseExpression() throws ParserException {
		if (Character.isDigit(source.getChar()) || test('-')) {
			return new Constant(parseNumber());
		} else if (Character.isLetter(source.getChar())) {
			return new Variable(parseIdentifier());
		} else if (testNext('(')) {
			Expression left = parseExpression();
			BinaryOperation op = new BinaryOperation(parseOperation());
			Expression right = parseExpression();
			if (!testNext(')')) {
				throw source.error("SYNTAX ERROR");
			}
			return new BinaryExpression(left, right, op);
//		} else if (testNext('[')) {
		
		} else {
			throw source.error("SYNTAX ERROR");
		}
	}
	
	private String parseOperation() throws ParserException {
		String op = "" + source.getChar();
		source.nextChar();
		return op;
	}
	
	private String parseIdentifier() throws ParserException {
		final StringBuilder sb = new StringBuilder();
		do {
			sb.append(source.getChar());
		} while (Character.isLetter(source.nextChar()));
		return sb.toString();
	}
	
	private int parseNumber() throws ParserException {
		final StringBuilder sb = new StringBuilder();
		readDigits(sb);
		try {
			return Integer.parseInt(sb.toString());
		} catch (final NumberFormatException e) {
			throw source.error("SYNTAX ERROR");
		}
	}
	
	private void readDigits(final StringBuilder sb) throws ParserException {
		do {
			sb.append(source.getChar());
		} while (Character.isLetterOrDigit(source.nextChar()));
	}
	
	private boolean testNext(final char c) throws ParserException {
		if (source.getChar() == c) {
			source.nextChar();
			return true;
		} else {
			return false;
		}
	}
	
	private boolean test(final char c) {
		return source.getChar() == c;
	}
	
}
