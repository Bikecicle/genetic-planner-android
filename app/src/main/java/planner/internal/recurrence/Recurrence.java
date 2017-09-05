package planner.internal.recurrence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Recurrence implements Serializable {

	private static final long serialVersionUID = -3994701701484893544L;
	public int scale;
	public int period;
	public List<Marker> markers;

	public Recurrence(int scale, int period, List<Marker> markers) {
		this.scale = scale;
		this.period = period;
		this.markers = markers;
	}

	public List<Long> getInstances(long start, long end) {
		List<Long> instances = new ArrayList<Long>();
		long current = start;
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(current);

		// Daily
		if (scale == Calendar.DAY_OF_WEEK) {
			while (current <= end) {
				instances.add(current);
				cal.add(Calendar.DAY_OF_WEEK, period);
				current = cal.getTimeInMillis();
			}
		}

		// Weekly
		else if (scale == Calendar.WEEK_OF_MONTH) {
			while (current <= end) {
				for (int i = 1; i <= period; i++) {
					for (Marker marker : markers) {
						if (marker.index == i && marker.type == Calendar.DAY_OF_WEEK) {
							cal.set(Calendar.DAY_OF_WEEK, marker.value);
							long instance = cal.getTimeInMillis();
							if (instance >= start)

								instances.add(instance);

						}
					}
					cal.add(Calendar.WEEK_OF_MONTH, 1);
					current = cal.getTimeInMillis();
				}
			}
		}

		// Monthly
		else if (scale == Calendar.MONTH) {
			while (current <= end) {
				for (int i = 1; i <= period; i++) {
					for (Marker marker : markers) {
						if (marker.index == i) {
							if (marker.type == Calendar.DAY_OF_MONTH) {
								cal.set(Calendar.DAY_OF_MONTH, marker.value);
								long instance = cal.getTimeInMillis();
								instances.add(instance);
							} else if (marker.type == Calendar.DAY_OF_WEEK_IN_MONTH) {
								cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, marker.value);
								long instance = cal.getTimeInMillis();
								if (instance >= start)
									instances.add(instance);
							}
						}
					}
					cal.add(Calendar.MONTH, 1);
					current = cal.getTimeInMillis();
				}
			}
		}

		// Yearly
		else if (scale == Calendar.YEAR) {
			while (current <= end) {
				for (int i = 1; i <= period; i++) {
					for (Marker marker : markers) {
						if (marker.index == i && marker.type == Calendar.DAY_OF_YEAR) {
							cal.set(Calendar.DAY_OF_YEAR, marker.value);
							long instance = cal.getTimeInMillis();
							if (instance >= start)
								instances.add(instance);
						}
					}
					cal.add(Calendar.YEAR, 1);
					current = cal.getTimeInMillis();
				}
			}
		}
		return instances;
	}

	@Override
	public String toString() {
		String str = "Repeats every " + period + " ";
		if (scale == Calendar.DAY_OF_WEEK) {
			str += "days";
		} else if (scale == Calendar.WEEK_OF_MONTH) {
			str += "weeks";
		} else if (scale == Calendar.MONTH) {
			str += "months";
		} else if (scale == Calendar.YEAR) {
			str += "years";
		}
		return str;
	}
}
