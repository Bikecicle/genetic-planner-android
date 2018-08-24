package planner.model.item;

/**
 * Created by Griffin on 3/6/2018.
 */

public enum ItemType {

    event("Event", "Start Date/Time"),
    task("Task", "Deadline"),
    routine("Routine", "Start Date/Time"),
    quota("Quota", "Start Date/Time");

    public String name;
    public String dateName;

    ItemType(String name, String dateName) {
        this.name = name;
        this.dateName = dateName;
    }

}
