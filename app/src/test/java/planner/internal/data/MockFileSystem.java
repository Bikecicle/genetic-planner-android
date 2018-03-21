package planner.internal.data;

import planner.internal.item.Agenda;
import planner.internal.item.Schedule;

/**
 * Created by Griffin on 3/20/2018.
 */

public class MockFileSystem extends FileSystem {

    @Override
    public boolean load() {
        agenda = new Agenda();
        schedule = new Schedule();
        return true;
    }

    @Override
    public boolean save() {
        return true;
    }

}
