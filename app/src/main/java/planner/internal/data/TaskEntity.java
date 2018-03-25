package planner.internal.data;

import android.arch.persistence.room.Entity;

/**
 * Created by Griffin Page on 3/25/2018
 * griffinpage9@gmail.com
 */

@Entity
public class TaskEntity extends ItemEntity {

    public long deadline;
    public long duration;

    public TaskEntity(){}
}
