package main.java.elements;

import java.util.List;

public abstract class Expression implements Evaluatable {
	
	@Override
	abstract public boolean equals(Object obj);
	
	@Override
	abstract public String toString();
}
