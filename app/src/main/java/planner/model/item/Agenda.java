package planner.model.item;

import java.util.List;

import planner.model.core.PlannerDatabase;

/**
 * Created by Griffin Page on 9/6/2018
 * griffinpage9@gmail.com
 */

public class Agenda {

    EventDAO eventDAO;
    TaskDAO taskDAO;

    public Agenda(PlannerDatabase database) {
        eventDAO = database.eventDAO();
        taskDAO = database.taskDAO();
    }

    public List<Task> getTasks() {
        return taskDAO.loadAll();
    }
}
