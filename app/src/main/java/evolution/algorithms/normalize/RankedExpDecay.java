package evolution.algorithms.normalize;

import evolution.core.Population;

public class RankedExpDecay implements Normalizer {

	private double decay;

	public RankedExpDecay(double decay) {
		this.decay = decay;
	}

	@Override
	public double[] normalize(Population current) {
		int size = current.size();
		double[] rates = new double[size];
		rates[0] = decay;
		for (int i = 1; i < size - 1; i++)
			rates[i] = rates[i - 1] * decay;
		if (size > 1)
			rates[size - 1] = rates[size - 2];
		return rates;
	}

}
