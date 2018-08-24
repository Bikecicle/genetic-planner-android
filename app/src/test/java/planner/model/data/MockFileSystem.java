package planner.model.data;

import planner.model.item.Agenda;
import planner.model.item.Schedule;

/**
 * Created by Griffin on 3/20/2018.
 */

public class MockFileSystem extends FileSystem {

    @Override
    public void load() {
        agenda = new Agenda();
        schedule = new Schedule();
    }

    @Override
    public void save() {
    }

}
