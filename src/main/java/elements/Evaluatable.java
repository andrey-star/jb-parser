package main.java.elements;

import main.java.exceptions.EvaluatingException;

import java.util.List;
import java.util.Map;

public interface Evaluatable {
	
	int evaluate(List<Integer> argValues, Map<String, Function> functions) throws EvaluatingException;
	
}
