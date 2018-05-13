package planner.external.android;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import planner.internal.data.RoomDBAndroid;
import planner.internal.item.Agenda;
import planner.internal.item.Event;
import planner.internal.item.Item;
import planner.internal.item.Task;

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

    }
}