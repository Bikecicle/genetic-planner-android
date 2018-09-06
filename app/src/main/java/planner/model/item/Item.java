package planner.model.item;

import java.io.Serializable;
import java.util.ArrayList;

public interface Item extends Serializable {
	
	public ArrayList<Note> generateNotes(long[] gene);

	public void complete(Note note);

	public void clean();
}
