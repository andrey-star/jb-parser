package main.java.util;

import main.java.exceptions.ParserException;

@FunctionalInterface
public interface ParserSupplier<T> {
	T get() throws ParserException;
}
