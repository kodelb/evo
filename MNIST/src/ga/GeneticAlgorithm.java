package ga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class GeneticAlgorithm {
	private static final double mutationRate = 0.08;
	private static final int tournamentSize = 5;
	private static final int populationSize = 30;

	private ArrayList<Chromosome> population;
	private Random random = new Random();

	public GeneticAlgorithm() {
		population = new ArrayList<>(populationSize);
		for (int i = 0; i < populationSize; i++) {
			Chromosome c = new MNISTFeatureChromosome();
			c.randomize();
			population.add(c);
		}
	}
	
	public Chromosome getBest() {
		return Collections.max(population);
	}

	public void evolvePopulation() {
		ArrayList<Chromosome> newPopulation = new ArrayList<Chromosome>(
				population.size());
		for (int i = 0; i < population.size() / 2; i++) {
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
		//return population.get((int) (Math.random() * population.size()));
		return tournamentSelection();
	}

	private Chromosome tournamentSelection() {
		ArrayList<Chromosome> tournament = new ArrayList<Chromosome>();
		for (int i = 0; i < tournamentSize; i++) {
			tournament.add(population.get(random.nextInt(population.size())));
		}

		return Collections.max(tournament);
	}
}
