package planner.external.android;

import java.util.Calendar;

import planner.internal.core.PlanningAssistant;
import planner.internal.item.Event;

/**
 * Created by Griffin on 3/2/2018.
 */

public class TestData {
    public static void simpleEvents(PlanningAssistant pa){
        Calendar c = Calendar.getInstance();
        System.out.println("Adding two events:");
        c.add(Calendar.HOUR, 1);
        Event e1 = new Event("event1", "1 hr from now", c.getTimeInMillis(), 30);
        pa.addItem(e1);
        System.out.println(e1);
        c.add(Calendar.HOUR, 2);
        Event e2 = new Event("event2", "3 hrs from now", c.getTimeInMillis(), 60);
        pa.addItem(e2);
        System.out.println(e2);
        pa.planSchedule();
        System.out.println(pa.getAll());
    }
}

