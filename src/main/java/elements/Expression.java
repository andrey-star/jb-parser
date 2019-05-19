package main.java.elements;

import main.java.exceptions.EvaluatingException;

import java.util.ArrayList;
import java.util.List;

public abstract class Expression implements Evaluatable {
	
	List<String> required;
	
	private List<String> getRequired() {
		return required;
	}
	
	static List<Integer> generateArguments(List<Integer> argValues, Expression expression, List<String> scope) throws EvaluatingException {
		List<Integer> expArgs = new ArrayList<>();
		for (String parameter : expression.getRequired()) {
			int index = scope.indexOf(parameter);
			if (index == -1) {
				throw new EvaluatingException("PARAMETER NOT FOUND", parameter);
			} else {
				expArgs.add(argValues.get(index));
			}
		}
		return expArgs;
	}
	
	@Override
	abstract public boolean equals(Object obj);
	
	@Override
	abstract public String toString();
}
