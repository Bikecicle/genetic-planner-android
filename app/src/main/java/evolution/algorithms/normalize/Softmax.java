package evolution.algorithms.normalize;

import evolution.core.Genome;
import evolution.core.Population;

public class Softmax implements Normalizer {

	@Override
	public double[] normalize(Population current) {
		double sum = 0;
		int size = current.size();
		double[] rates = new double[size];
		for (Genome genome : current) {
			sum += Math.exp(genome.getScore());
		}
		
		for (int i = 0; i < size; i++) {
			rates[i] = Math.exp(current.get(i).getScore()) / sum;
		}
		return rates;
	}

}
