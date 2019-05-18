package main.java.element;

import main.java.EvaluatingException;

import java.util.List;
import java.util.Map;

public interface Evaluatable {
	
	int evaluate(Map<String, Integer> args) throws EvaluatingException;
	
}
