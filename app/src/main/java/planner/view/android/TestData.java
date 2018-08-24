package planner.view.android;

import java.util.Calendar;

import planner.model.core.PlanningAssistant;
import planner.model.item.Event;

/**
 * Created by Griffin on 3/2/2018.
 */

public class TestData {
    public static void simpleEvents(PlanningAssistant pa){
        Calendar c = Calendar.getInstance();
        System.out.println("Adding two events:");
        c.add(Calendar.HOUR, 1);
        Event e1 = new Event(10, "event1", "1 hr from now", c.getTimeInMillis(), 30);
        pa.addItem(e1);
        System.out.println(e1);
        c.add(Calendar.HOUR, 2);
        Event e2 = new Event(11, "event2", "3 hrs from now", c.getTimeInMillis(), 60);
        pa.addItem(e2);
        System.out.println(e2);
        pa.planSchedule();
        System.out.println(pa.getAll());
    }
}

