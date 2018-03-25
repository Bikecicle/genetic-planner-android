package planner.internal.data;

import android.arch.persistence.room.Entity;

/**
 * Created by Griffin Page on 3/23/2018
 * griffinpage9@gmail.com
 */

@Entity
public class EventEntity extends ItemEntity {

    public long start;
    public long duration;

    public EventEntity(){}
}
