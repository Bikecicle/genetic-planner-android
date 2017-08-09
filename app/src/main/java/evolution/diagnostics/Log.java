package evolution.diagnostics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import evolution.core.Genome;
import evolution.core.Population;

public class Log implements Serializable {

	private static final long serialVersionUID = 5378570329356066060L;

	private List<Population> generations;
	private FamilyTree tree;

	public Log() {
		generations = new ArrayList<Population>();
		tree = new FamilyTree();
	}

	public void addGeneration(Population pop) {
		generations.add(pop);
	}

	public void addRelationship(Genome parent1, Genome parent2, Genome child) {
		tree.add(new Relationship(parent1, parent2, child));
	}

	public double maxScore() {
		double max = Double.NEGATIVE_INFINITY;
		for (Population pop : generations) {
			double score = pop.get(0).getScore();
			if (score > max)
				max = score;
		}
		return max;
	}

	public double minScore() {
		double min = Double.POSITIVE_INFINITY;
		for (Population pop : generations) {
			double score = pop.get(pop.size() - 1).getScore();
			if (score < min)
				min = score;
		}
		return min;
	}

	public int generationCount() {
		return generations.size();
	}

	public List<Population> getGenerations() {
		return generations;
	}

	public FamilyTree getTree() {
		return tree;
	}
}
