package ga;

import java.util.BitSet;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import kNearestNeighbors.KNN;
import dataTypes.MNISTDataSet;
import dataTypes.MNISTImage;

public class MNISTFeatureChromosome extends Chromosome {

	public static KNN knn;
	public static MNISTDataSet validation;
	private static Random random = new Random();

	private BitSet selectedFeatures;
	private int fitness = -1;
	
	public BitSet getFeatures() {
		return selectedFeatures;
	}

	public MNISTFeatureChromosome() {
		selectedFeatures = new BitSet(MNISTImage.rows * MNISTImage.cols / 8);
	}

	@Override
	public void randomize() {
		byte[] randomBytes = new byte[MNISTImage.rows * MNISTImage.cols / 8];
		random.nextBytes(randomBytes);
		selectedFeatures = BitSet.valueOf(randomBytes);
	}

	public MNISTFeatureChromosome(MNISTFeatureChromosome chromosome) {
		selectedFeatures = (BitSet) chromosome.selectedFeatures.clone();
	}

	@Override
	public int fitness() {
		if (fitness < 0) {
			try {
				MNISTImage.usedPixels = selectedFeatures;
				fitness = knn.getSuccessCount(validation);
				//System.out.println("calc fitness " + fitness + this);
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return fitness;
	}

	@Override
	public Chromosome[] crossOver(Chromosome partner) {
		MNISTFeatureChromosome child1 = new MNISTFeatureChromosome();
		MNISTFeatureChromosome child2 = new MNISTFeatureChromosome();
		MNISTFeatureChromosome parent = (MNISTFeatureChromosome) partner;

		for (int i = 0; i < selectedFeatures.size(); i++) {
			if (random.nextBoolean()) {
				child1.selectedFeatures.set(i, selectedFeatures.get(i));
				child2.selectedFeatures.set(i, parent.selectedFeatures.get(i));
			} else {
				child2.selectedFeatures.set(i, selectedFeatures.get(i));
				child1.selectedFeatures.set(i, parent.selectedFeatures.get(i));
			}
		}

		return new MNISTFeatureChromosome[] { child1, child2 };
	}

	@Override
	public void mutate() {
		selectedFeatures.flip(random.nextInt(selectedFeatures.size()));
	}

}
