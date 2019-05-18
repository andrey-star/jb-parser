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
		if(!test(StringParserSource.END)) {
			throw source.error("SYNTAX ERROR");
		}
		return result;
	}
	
	private Expression parseExpression() throws ParserException {
		if (Character.isDigit(source.getChar()) || test('-')) {
			return new Constant(parseNumber());
		} else if (Character.isLetter(source.getChar())) {
			return new Variable(parseIdentifier());
		} else if (testNext('(')) {
			return parseBinaryOperation();
		} else if (testNext('[')) {
			return parseIfExpression();
//		} else if (isLetterOrUnderscore(source.getChar())) {
//			return parseFunctionOrCall();
		} else {
			throw source.error("SYNTAX ERROR");
		}
	}
	
	private Expression parseFunctionOrCall() {
		return new Constant(1);
	}
	
	private Expression parseBinaryOperation() throws ParserException {
		Expression left = parseExpression();
		BinaryOperation op = new BinaryOperation(parseOperation());
		Expression right = parseExpression();
		if (!testNext(')')) {
			throw source.error("SYNTAX ERROR");
		}
		return new BinaryExpression(left, right, op);
	}
	
	private Expression parseIfExpression() throws ParserException {
		Expression rule = parseExpression();
		if (!testNext(']')) {
			throw source.error("SYNTAX ERROR");
		}
		if (!testNext('?')) {
			throw source.error("SYNTAX ERROR");
		}
		if (!testNext('{')) {
			throw source.error("SYNTAX ERROR");
		}
		Expression ifTrue = parseExpression();
		if (!testNext('}')) {
			throw source.error("SYNTAX ERROR");
		}
		if (!testNext(':')) {
			throw source.error("SYNTAX ERROR");
		}
		if (!testNext('{')) {
			throw source.error("SYNTAX ERROR");
		}
		Expression ifFalse = parseExpression();
		if (!testNext('}')) {
			throw source.error("SYNTAX ERROR");
		}
		return new IfExpression(rule, ifTrue, ifFalse);
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
		} while (isLetterOrUnderscore(source.nextChar()));
		return sb.toString();
	}
	
	private boolean isLetterOrUnderscore(char c) {
		return c == '_' || Character.isLetter(c);
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
