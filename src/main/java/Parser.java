package main.java;

import main.java.element.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Parser {
	
	private ParserSource source;
	
	public Parser(ParserSource source) {
		this.source = source;
	}
	
	public Expression parseValue() throws ParserException {
		source.nextChar();
		Expression result = parseExpression();
		if (!test(StringParserSource.END)) {
			throw source.error("SYNTAX ERROR");
		}
		return result;
	}
	
	public Function parseFunctionDefinition() throws ParserException {
		source.nextChar();
		String name = parseIdentifier();
		List<String> args = parseParams();
		if (!testNext('=')) {
			throw source.error("SYNTAX ERROR");
		}
		if (!testNext('{')) {
			throw source.error("SYNTAX ERROR");
		}
		Expression body = parseExpression();
		if (!testNext('}')) {
			throw source.error("SYNTAX ERROR");
		}
		return new Function(name, args, body);
	}
	
	private Expression parseExpression() throws ParserException {
		if (Character.isDigit(source.getChar()) || test('-')) {
			return new Constant(parseNumber());
		} else if (isLetterOrUnderscore(source.getChar())) {
			String identifier = parseIdentifier();
			if (test('(')) {
				return new CallExpression(identifier, parseCallArgs());
			} else {
				return new Variable(identifier);
			}
		} else if (testNext('(')) {
			return parseBinaryOperation();
		} else if (testNext('[')) {
			return parseIfExpression();
		} else {
			throw source.error("SYNTAX ERROR");
		}
	}
	
	private List<String> parseParams() throws ParserException {
		List<String> strings = parseArgs(() -> {
			try {
				return parseIdentifier();
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
	
	private <T> List<T> parseArgs(Supplier<T> f) throws ParserException {
		if (!testNext('(')) {
			throw source.error("SYNTAX ERROR");
		}
		List<T> args = new ArrayList<>();
		while (true) {
			args.add(f.get());
			if (source.testNext(')')) {
				break;
			}
			if (source.testNext(',')) {
				continue;
			}
			throw source.error("SYNTAX ERROR");
		}
		return args;
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
			throw source.error("SYNTAX ERROR");
		}
	}
	
	private void readDigits(StringBuilder sb) throws ParserException {
		do {
			sb.append(source.getChar());
		} while (Character.isLetterOrDigit(source.nextChar()));
	}
	
	private boolean testNext(char c) throws ParserException {
		return source.testNext(c);
	}
	
	private boolean test(char c) {
		return source.test(c);
	}
	
}
