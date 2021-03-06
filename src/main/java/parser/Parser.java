package main.java.parser;

import main.java.element.*;
import main.java.exception.ParserException;
import main.java.util.ParserSupplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;

/**
 * The {@code Parser } class represents a utility, used for parsing
 * programs, function definitions and expressions.
 */
public class Parser {
	
	private ParserSource source;
	private final Map<String, BinaryOperator<Integer>> BINARY_OPERATIONS = Map.of(
			"+", Integer::sum,
			"-", (a, b) -> a - b,
			"*", (a, b) -> a * b,
			"/", (a, b) -> a / b,
			"%", (a, b) -> a % b,
			">", (a, b) -> (a > b ? 1 : 0),
			"<", (a, b) -> (a < b ? 1 : 0),
			"=", (a, b) -> (a.equals(b) ? 1 : 0)
	);
	private int line = 0;
	
	/**
	 *
	 * @param lines the definition of the program, according to the syntax.
	 *              First lines contains function definitions, the last line contains
	 *              an expression to be called on startup
	 * @return a {@code Program} object, parsed from the lines
	 * @throws ParserException if the program definition does not follow the syntax
	 */
	public Program parseProgram(List<String> lines) throws ParserException {
		Map<String, Function> functions = new HashMap<>();
		for (line = 0; line < lines.size() - 1; line++) {
			this.source = new StringParserSource(lines.get(line));
			Function f = parseFunctionDefinition();
			functions.put(f.getName(), f);
		}
		this.source = new StringParserSource(lines.get(lines.size() - 1));
		return new Program(functions, parseValue());
	}
	
	/**
	 * Parses an expression from the parser's source
	 * @return an {@code Expression} object, representing the parsed expression
	 * @throws ParserException if the expression is malformed
	 */
	private Expression parseValue() throws ParserException {
		source.nextChar();
		Expression result = parseExpression();
		if (!test(ParserSource.END)) {
			throw source.error();
		}
		return result;
	}
	
	/**
	 * Parses a function from the parser's source
	 * @return a {@code Function} object, representing the parsed function
	 * @throws ParserException if the function definition is malformed
	 */
	private Function parseFunctionDefinition() throws ParserException {
		source.nextChar();
		String name = parseIdentifier();
		List<String> params = parseParams();
		if (doesntMatch("={")) {
			throw source.error();
		}
		Expression body = parseExpression();
		if (!testNext('}')) {
			throw source.error();
		}
		if (!test(ParserSource.END)) {
			throw source.error();
		}
		return new Function(name, params, body, line);
	}
	
	private Expression parseExpression() throws ParserException {
		if (Character.isDigit(source.getChar()) || test('-')) {
			return new Constant(parseNumber());
		} else if (isLetterOrUnderscore(source.getChar())) {
			String identifier = parseIdentifier();
			if (test('(')) {
				return new CallExpression(identifier, parseCallArgs(), line);
			} else {
				return new Variable(identifier, line);
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
		return parseArgs(this::parseIdentifier);
	}
	
	private List<Expression> parseCallArgs() throws ParserException {
		return parseArgs(this::parseExpression);
	}
	
	private <T> List<T> parseArgs(ParserSupplier<T> argSupplier) throws ParserException {
		if (!testNext('(')) {
			throw source.error();
		}
		List<T> args = new ArrayList<>();
		if (testNext(')')) {
			return args;
		}
		while (true) {
			args.add(argSupplier.get());
			if (testNext(')')) {
				break;
			}
			if (testNext(',')) {
				continue;
			}
			throw source.error();
		}
		return args;
	}
	
	private Expression parseBinaryOperation() throws ParserException {
		Expression left = parseExpression();
		String operator = parseOperation();
		if (!BINARY_OPERATIONS.containsKey(operator)) {
			throw source.error();
		}
		
		BinaryOperation op = new BinaryOperation(BINARY_OPERATIONS.get(operator), operator);
		Expression right = parseExpression();
		if (!testNext(')')) {
			throw source.error();
		}
		return new BinaryExpression(left, right, op, line);
	}
	
	private Expression parseIfExpression() throws ParserException {
		Expression rule = parseExpression();
		if (doesntMatch("]?{")) {
			throw source.error();
		}
		Expression ifTrue = parseExpression();
		if (doesntMatch("}:{")) {
			throw source.error();
		}
		Expression ifFalse = parseExpression();
		if (!testNext('}')) {
			throw source.error();
		}
		return new IfExpression(rule, ifTrue, ifFalse);
	}
	
	private String parseOperation() {
		String op = Character.toString(source.getChar());
		source.nextChar();
		return op;
	}
	
	private String parseIdentifier() throws ParserException {
		if (!isLetterOrUnderscore(source.getChar())) {
			throw source.error();
		}
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
		if (source.getChar() == c) {
			source.nextChar();
			return true;
		} else {
			return false;
		}
	}
	
	private boolean doesntMatch(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (!testNext(s.charAt(i))) {
				return true;
			}
		}
		return false;
	}
	
	private boolean test(char c) {
		return source.getChar() == c;
	}
	
}
