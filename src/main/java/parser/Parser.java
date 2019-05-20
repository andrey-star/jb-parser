package main.java.parser;

import main.java.elements.*;
import main.java.exceptions.ParserException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class Parser {
	
	private final ParserSource source;
	private final Set<String> binaryOperators = Set.of("+", "-", "*", "/", ">", "<", "=");
	private int line = 0;
	
	public Parser(ParserSource source) {
		this.source = source;
	}
	
	public Expression parseValue(int line) throws ParserException {
		this.line = line;
		source.nextChar();
		Expression result = parseExpression();
		if (!test(ParserSource.END)) {
			throw source.error();
		}
		return result;
	}
	
	public Function parseFunctionDefinition(int line) throws ParserException {
		source.nextChar();
		String name = parseIdentifier();
		List<String> args = parseParams();
		if (!testNext('=')) {
			throw source.error();
		}
		if (!testNext('{')) {
			throw source.error();
		}
		Expression body = parseExpression();
		if (!testNext('}')) {
			throw source.error();
		}
		return new Function(name, args, body, line);
	}
	
	private Expression parseExpression() throws ParserException {
		if (Character.isDigit(source.getChar()) || test('-')) {
			return new Constant(parseNumber());
		} else if (isLetterOrUnderscore(source.getChar())) {
			String identifier = parseIdentifier();
			if (test('(')) {
				return new CallExpression(identifier, parseCallArgs(), line);
			} else {
				return new Variable(identifier);
			}
		} else if (testNext('(')) {
			return parseBinaryOperation();
		} else if (testNext('[')) {
			return parseIfExpression();
		} else {
			throw source.error();
		}
	}
	
	private List<String> parseParams() throws ParserException {
		List<String> strings = parseArgs(this::parseIdentifier);
		if (strings == null) {
			throw new ParserException("SYNTAX ERROR");
		} else {
			return strings;
		}
	}
	
	private List<Expression> parseCallArgs() throws ParserException {
		List<Expression> strings = parseArgs(() -> {
			try {
				return parseExpression();
			} catch (ParserException e) {
				return null;
			}
		});
		if (strings == null) {
			throw new ParserException("SYNTAX ERROR");
		} else {
			return strings;
		}
	}
	
	private <T> List<T> parseArgs(Supplier<T> argument) throws ParserException {
		if (!testNext('(')) {
			throw source.error();
		}
		List<T> args = new ArrayList<>();
		while (true) {
			args.add(argument.get());
			if (source.testNext(')')) {
				break;
			}
			if (source.testNext(',')) {
				continue;
			}
			throw source.error();
		}
		return args;
	}
	
	private Expression parseBinaryOperation() throws ParserException {
		Expression left = parseExpression();
		String operator = parseOperation();
		if (!binaryOperators.contains(operator)) {
			throw source.error();
		}
		BinaryOperation op = new BinaryOperation(operator);
		Expression right = parseExpression();
		if (!testNext(')')) {
			throw source.error();
		}
		return new BinaryExpression(left, right, op);
	}
	
	private Expression parseIfExpression() throws ParserException {
		Expression rule = parseExpression();
		if (!testNext(']')) {
			throw source.error();
		}
		if (!testNext('?')) {
			throw source.error();
		}
		if (!testNext('{')) {
			throw source.error();
		}
		Expression ifTrue = parseExpression();
		if (!testNext('}')) {
			throw source.error();
		}
		if (!testNext(':')) {
			throw source.error();
		}
		if (!testNext('{')) {
			throw source.error();
		}
		Expression ifFalse = parseExpression();
		if (!testNext('}')) {
			throw source.error();
		}
		return new IfExpression(rule, ifTrue, ifFalse);
	}
	
	private String parseOperation() {
		String op = "" + source.getChar();
		source.nextChar();
		return op;
	}
	
	private String parseIdentifier() {
		StringBuilder sb = new StringBuilder();
		do {
			sb.append(source.getChar());
		} while (isLetterOrUnderscore(source.nextChar()));
		return sb.toString();
	}
	
	private boolean isLetterOrUnderscore(char c) {
		return c == '_' || Character.isLetter(c);
	}
	
	private int parseNumber() throws ParserException {
		StringBuilder sb = new StringBuilder();
		readDigits(sb);
		try {
			return Integer.parseInt(sb.toString());
		} catch (NumberFormatException e) {
			throw source.error();
		}
	}
	
	private void readDigits(StringBuilder sb) {
		do {
			sb.append(source.getChar());
		} while (Character.isLetterOrDigit(source.nextChar()));
	}
	
	private boolean testNext(char c) {
		return source.testNext(c);
	}
	
	private boolean test(char c) {
		return source.test(c);
	}
	
}
