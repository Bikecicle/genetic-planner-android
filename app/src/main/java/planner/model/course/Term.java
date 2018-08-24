package planner.model.course;

/**
 * Created by Griffin Page on 8/24/2018
 * griffinpage9@gmail.com
 */

public enum Term {

    fallSemester(1), springSemester(2);

    int id;

    Term(int id) {
        this.id = id;
    }
}
