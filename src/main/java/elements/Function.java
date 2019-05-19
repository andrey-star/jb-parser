package main.java.elements;

import main.java.exceptions.EvaluatingException;

import java.util.*;

public class Function {

	private final String name;
	private final List<String> params;
	private final Expression body;
	private final int line;
	
	public Function(String name, List<String> params, Expression body, int line) {
		this.name = name;
		this.params = params;
		this.body = body;
		this.line = line;
	}
	
	int call(List<Integer> argValues, Map<String, Function> functions) throws EvaluatingException {
		Set<String> arguments = new HashSet<>(params);
		if (arguments.size() != params.size()) {
			throw new EvaluatingException("DUPLICATE ARGUMENTS FOUND", name);
		}
		try {
			List<Integer> expArgs = Expression.generateArguments(argValues, body, params);
			return body.evaluate(expArgs, functions);
		} catch (EvaluatingException e) {
			throw new EvaluatingException(e.getError(), e.getName(), line);
		}
		
	}
	
	public String getName() {
		return name;
	}
	
	List<String> getParams() {
		return params;
	}
	
	public Expression getBody() {
		return body;
	}
	
	@Override
	public String toString() {
		return name + "(" + String.join(",", params) + ")={" + body + "}";
	}
}
