package planner.internal.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import evolution.algorithms.normalize.Polynomial;
import evolution.algorithms.select.SingleGenNorm;
import evolution.core.EvolutionManager;
import evolution.core.Population;
import planner.internal.item.Agenda;
import planner.internal.item.Event;
import planner.internal.item.Task;
import planner.internal.recurrence.Recurrence;

public class Test {

	public static void main(String[] args) {
		
		/**Agenda a = new Agenda();
		Calendar c = Calendar.getInstance();
		String startDate = "2017-07-7 00:00";
		try {
			c.setTime(new SimpleDateFormat(C.DATE_TIME_FORMAT).parse(startDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long now = c.getTimeInMillis();
		
		c.add(Calendar.WEEK_OF_MONTH, 1);
		long later = c.getTimeInMillis();
		Recurrence r = new Recurrence(Calendar.DAY_OF_WEEK, 1, null, now, later);
		a.addEvent(new Event("Sleep", "...", startDate, 8 * 60, r));
		a.addTask(new Task("Clean Room", "just do it", "2017-07-13 12:00", 120));
		System.out.println(a);

		Population ip = new Population(0);
		for (int i = 0; i < 100; i++) {
			ip.add(new ScheduleGenome(a, 0.2));
		}
		EvolutionManager em = new EvolutionManager(ip, new SingleGenNorm(new Polynomial(2)), true);
		em.runGenerations(1000);
		em.saveLog("data/test.log");
		ScheduleGenome result = (ScheduleGenome) em.getResult();
		System.out.println(result);
		System.out.println(result.generateSchedule());
		
		/**
		Calendar c = Calendar.getInstance();
		long start = c.getTimeInMillis();
		c.add(Calendar.MONTH, 1);
		c.add(Calendar.WEEK_OF_MONTH, 1);
		long end = c.getTimeInMillis();
		List<Marker> m = new ArrayList<Marker>();
		Recurrence r = new Recurrence(Calendar.DAY_OF_WEEK, 5, m, start, end);
		List<Long> l = r.getInstances();
		for (long i : l) {
			c.setTimeInMillis(i);
			System.out.println(c.getTime());
		}
		*/
	}
}
