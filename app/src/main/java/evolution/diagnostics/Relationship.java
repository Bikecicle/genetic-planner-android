package evolution.diagnostics;

import java.io.Serializable;

import evolution.core.Genome;

public class Relationship implements Serializable{

	private static final long serialVersionUID = 5875379157772352578L;
	
	public Genome parent1;
	public Genome parent2;
	public Genome child;
	
	public Relationship(Genome parent1, Genome parent2, Genome child) {
		this.parent1 = parent1;
		this.parent2 = parent2;
		this.child = child;
	}
	
	public boolean isParent(Genome genome) {
		if (genome == parent1)
			return true;
		if (genome == parent2)
			return true;
		return false;
	}
}
