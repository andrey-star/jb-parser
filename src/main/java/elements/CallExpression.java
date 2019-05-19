package main.java.elements;

import main.java.exceptions.EvaluatingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CallExpression extends Expression {
	
	private final String functionName;
	private final List<Expression> args;
	private final int line;
	
	public CallExpression(String functionName, List<Expression> args, int line) {
		List<String> res = new ArrayList<>();
		for (Expression arg : args) {
			for (String param : arg.required) {
				if (!res.contains(param)) {
					res.add(param);
				}
			}
		}
		this.required = res;
		this.functionName = functionName;
		this.args = args;
		this.line = line;
	}
	
	@Override
	public int evaluate(List<Integer> argValues, Map<String, Function> functions) throws EvaluatingException {
		List<Integer> evaluated = new ArrayList<>();
		for (Expression expression : args) {
			evaluated.add(expression.evaluate(argValues, functions));
		}
		if (functions.containsKey(functionName)) {
			Function function = functions.get(functionName);
			if (args.size() != function.getParams().size()) {
				throw new EvaluatingException("ARGUMENT NUMBER MISMATCH", functionName, line);
			}
			return functions.get(functionName).call(evaluated, functions);
		} else {
			throw new EvaluatingException("FUNCTION NOT FOUND", functionName, line);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CallExpression) {
			return functionName.equals(((CallExpression) obj).functionName)
					&& args.equals(((CallExpression) obj).args);
		}
		return false;
	}
	
	@Override
	public String toString() {
		List<String> string = args.stream().map(Expression::toString).collect(Collectors.toList());
		return functionName + "(" + String.join(",", string) + ")";
	}
}
