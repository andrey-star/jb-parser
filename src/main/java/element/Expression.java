package main.java.element;

import java.util.ArrayList;
import java.util.List;

public abstract class Expression implements Evaluatable {
	
	protected List<String> required;
	
	public List<String> getRequired() {
		return required;
	}
	
	public static List<Integer> generateArguments(List<Integer> argValues, Expression expression, List<String> scope) {
		List<Integer> expArgs = new ArrayList<>();
		for (String parameter : expression.getRequired()) {
			int index = scope.indexOf(parameter);
			if (index == -1) {
				throw new Error("PARAMETER NOT FOUND: " + parameter);
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
