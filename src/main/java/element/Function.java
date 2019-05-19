package main.java.element;

import main.java.EvaluatingException;

import java.util.*;

public class Function {

	private final String name;
	private final List<String> params;
	private final Expression body;
	
	public Function(String name, List<String> params, Expression body) {
		Set<String> arguments = new HashSet<>(params);
		if (arguments.size() != params.size()) {
			throw new Error("DUPLICATE ARGUMENTS FOUND: " + name);
		}
		this.name = name;
		this.params = params;
		this.body = body;
	}
	
	public int call(List<Integer> argValues, Map<String, Function> functions) throws EvaluatingException {
		if (argValues.size() != params.size()) {
			throw new Error("ARGUMENT NUMBER MISMATCH: " + name);
		}
		List<Integer> expArgs = Expression.generateArguments(argValues, body, params);
		return body.evaluate(expArgs, functions);
	}
	
	public String getName() {
		return name;
	}
	
	public List<String> getParams() {
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
