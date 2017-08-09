package evolution.algorithms.select;

import evolution.core.Population;
import evolution.diagnostics.Log;

public interface Selector {

	public Population nextGeneration(Population pop);
	
	public void enableLogging(Log log);
}
