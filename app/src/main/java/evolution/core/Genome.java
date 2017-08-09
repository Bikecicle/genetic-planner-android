package evolution.core;

import java.io.Serializable;

public interface Genome extends Serializable {

	public Genome breed(Genome other);

	public double getScore();

	public int getId();

}
