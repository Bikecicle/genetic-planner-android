package planner.internal.core;

import evolution.algorithms.select.Selector;
import evolution.algorithms.select.SurvivalThreshold;

public class C {
	
	// General date/time constants
	public static final long DAY = 86400000; // Milliseconds in a day
	public static final long HOUR = 3600000; // Milliseconds in an hour
	public static final long MINUTE = 60000; // Milliseconds in a minute
	
	public static final String DATE_TIME_FORMAT = "MM/dd/yyyy HH:mm";
	public static final String DAY_FORMAT = "MM/dd/yyyy";
	public static final String TIME_FORMAT = "HH:mm";
	public static final String MONTH_FORMAT = "MM/yyyy";
	public static final String YEAR_FORMAT = "yyyy";
	
	// Schedule evolution parameters
	public static final int POPULATION_SIZE = 10;
	public static final int GENERATION_COUNT = 10;
	public static final double MUTATION_RATE = 0.5;
	//public static final Selector SELECTOR = new SingleGenNorm(new Polynomial(3));
	public static final Selector SELECTOR = new SurvivalThreshold(0.2);
	
	public static final long RESOLUTION = 15 * C.MINUTE;
	
	public static final String logFile = "data/test.log";
	
	// Scoring function parameters
	public static final double CONFLICT_CF = 100000.0;
	public static final double INITIATIVE_CF = 1000.0 / C.DAY;
	public static final double SEPARATION_CF = 1000.0 / C.HOUR;
}
