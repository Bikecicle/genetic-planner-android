package planner.internal.item;

/**
 * Created by Griffin on 3/6/2018.
 */

public enum ItemType {

    event("Event", "Start Date"),
    task("Task", "Deadline"),
    routine("Routine", "Start Date"),
    quota("Quota", "Start Date");

    public String name;
    public String dateName;

    ItemType(String name, String dateName) {
        this.name = name;
        this.dateName = dateName;
    }

}
