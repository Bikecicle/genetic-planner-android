package planner.internal.data;

import planner.internal.core.ScheduleGenome;
import planner.internal.item.Agenda;

/**
 * Created by Griffin on 8/11/2017.
 */

public interface DataManager {

    boolean saveAgenda(Agenda agenda);

    Agenda loadAgenda();

    boolean saveScheduleGenome(ScheduleGenome scheduleGenome);

    ScheduleGenome loadScheduleGenome();

}
