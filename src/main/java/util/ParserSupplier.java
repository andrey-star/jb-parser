package main.java.util;

import main.java.exception.ParserException;

@FunctionalInterface
public interface ParserSupplier<T> {
	
	T get() throws ParserException;
	
}
