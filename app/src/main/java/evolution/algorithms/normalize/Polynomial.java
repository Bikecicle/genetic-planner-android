package evolution.algorithms.normalize;

import evolution.core.Genome;
import evolution.core.Population;

public class Polynomial implements Normalizer {

	private double order;

	public Polynomial(double order) {
		this.order = order;
	}

	@Override
	public double[] normalize(Population current) {
		int size = current.size();
		double[] rates = new double[size];
		double min = current.get(size - 1).getScore();
		double sum = 0;
		for (Genome genome : current) {
			sum += Math.pow(genome.getScore() - min, order);
		}
		if (sum == 0.0) {
			double p = 1.0 / size;
			for (int i = 0; i < size; i++) {
				rates[i] = p;
			}
		} else {
			for (int i = 0; i < size; i++) {
				rates[i] = Math.pow(current.get(i).getScore() - min, order) / sum;
			}
		}
		return rates;
	}
}
