package ga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class GeneticAlgorithm {
	private static final double mutationRate = 0.05;
	private ArrayList<Chromosome> population;

	public void evolvePopulation() {
		ArrayList<Chromosome> newPopulation = new ArrayList<Chromosome>(
				population.size());
		for (int i = 0; i < population.size(); i++) {
			Chromosome first = select();
			Chromosome second = select();
			newPopulation.addAll(Arrays.asList(first.crossOver(second)));
		}

		for (int i = 0; i < population.size(); i++) {
			if (Math.random() <= mutationRate) {
				newPopulation.get(i).mutate();
			}
		}
		
		population = newPopulation;
	}

	private Chromosome select() {
		return population.get((int) (Math.random() * population.size()));
	}
}
