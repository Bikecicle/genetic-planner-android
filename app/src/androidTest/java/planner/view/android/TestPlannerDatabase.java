package planner.view.android;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Calendar;

import planner.model.data.RoomDBAndroid;
import planner.model.item.Agenda;
import planner.model.item.Event;
import planner.model.item.Note;
import planner.model.item.Schedule;
import planner.model.item.Task;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class TestPlannerDatabase {
    private RoomDBAndroid dm;

    @Before
    public void createDb() {
        dm = new RoomDBAndroid(InstrumentationRegistry.getTargetContext(), true);
    }

    @After
    public void closeDb() throws IOException {
        dm.close();
    }

    @Test
    public void readWriteItemTest() throws Exception {
        Calendar c = Calendar.getInstance();
        Agenda a = null;
        c.add(Calendar.DAY_OF_YEAR, 1);
        Event e1 = new Event(10,"e1", "event1", c.getTimeInMillis(), 60);
        c.add(Calendar.HOUR, 2);
        Task t1 = new Task(11, "t1", "task1", c.getTimeInMillis(), 30);
        dm.addItem(e1);
        a = dm.getAgenda();
        assertFalse(a.isEmpty());
        assertTrue(a.hasItem(e1));
        dm.addItem(t1);
        a = dm.getAgenda();
        assertTrue(a.hasItem(e1));
        assertTrue(a.hasItem(t1));
    }

    @Test
    public void readWriteNoteTest() throws Exception {
        Calendar c1 = Calendar.getInstance();
        Agenda a = null;
        c1.add(Calendar.DAY_OF_YEAR, 1);
        Event e1 = new Event(10,"e1", "event1", c1.getTimeInMillis(), 60);
        c1.add(Calendar.HOUR, 2);
        Task t1 = new Task(11, "t1", "task1", c1.getTimeInMillis(), 30);
        dm.addItem(e1);
        dm.addItem(t1);

        Calendar c2 = Calendar.getInstance();
        Schedule s1 = dm.getSchedule();
        c2.add(Calendar.DAY_OF_YEAR, 1);
        Note n1 = new Note(10, "n1", "note1", c2.getTimeInMillis(), 60, e1);
        c2.add(Calendar.HOUR, 2);
        Note n2 = new Note(11, "n2", "note2", c2.getTimeInMillis(), 30, t1);
        s1.add(n1);
        s1.add(n2);

        dm.setSchedule(s1);
        assertEquals(n1, dm.getNoteById(10));
        assertEquals(n2, dm.getNoteById(11));
    }
}