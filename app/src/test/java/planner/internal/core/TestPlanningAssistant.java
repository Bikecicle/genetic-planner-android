package planner.internal.core;

import org.junit.Test;

import java.util.Calendar;

import planner.internal.data.MockFileSystem;
import planner.internal.item.Event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestPlanningAssistant {

    @Test
    public void getInstanceTest() throws Exception {
        MockFileSystem fs = new MockFileSystem();
        PlanningAssistant pa1 = PlanningAssistant.getInstance(fs);
        assertEquals(fs, pa1.getDataManager());
        PlanningAssistant pa2 = PlanningAssistant.getInstance();
        assertEquals(pa1, pa2);
        assertEquals(fs, pa2.getDataManager());
    }

    @Test
    public void planScheduleTest() throws Exception {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, 1);
        Event e1 = new Event("e1", "testEvent1", c.getTimeInMillis(), 60);
        c.add(Calendar.HOUR, 2);
        Event e2 = new Event("e2", "testEvent2", c.getTimeInMillis(), 60);

        PlanningAssistant pa = new PlanningAssistant(new MockFileSystem());
        pa.addItem(e1);
        pa.addItem(e2);
        assertEquals(2, pa.getAgenda().size());
        assertTrue(pa.getSchedule().isEmpty());
        pa.planSchedule();
        assertEquals(2, pa.getSchedule().size());
        assertEquals(e1, pa.getAll().get(0).parent);
        assertEquals(e2, pa.getAll().get(1).parent);
    }
}