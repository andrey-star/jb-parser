package main.java.elements;

import main.java.exceptions.EvaluatingException;

import java.util.Map;

public interface Evaluatable {
	
	int evaluate(Map<String, Integer> scope, Map<String, Function> functions) throws EvaluatingException;
	
}
