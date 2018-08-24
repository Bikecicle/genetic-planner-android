package planner.model.course;

import java.util.Calendar;

/**
 * Created by Griffin Page on 8/24/2018
 * griffinpage9@gmail.com
 */

public class Course {

    String subject;
    String id;

    int year;
    Term term;
    long start;
    long end;

    long time;
    boolean[] days;
    long duration;
}
