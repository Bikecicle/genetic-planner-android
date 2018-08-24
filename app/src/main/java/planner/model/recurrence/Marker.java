package planner.model.recurrence;

import java.io.Serializable;

public class Marker implements Serializable{
	
	private static final long serialVersionUID = -3364558389989420769L;
	public int type;
	public int value;
	public int index;
	
	public Marker(int type, int value, int index) {
		this.type = type;
		this.value = value;
		this.index = index;
	}
	
	
	@Override
	public String toString() {
		return type + ":" + value + ":" + index;
	}

}
