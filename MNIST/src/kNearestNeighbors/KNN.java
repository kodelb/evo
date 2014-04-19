package kNearestNeighbors;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

import dataTypes.MNISTImage;

public class KNN {
	private ArrayList<MNISTImage> trainingSet;
	static int K = 5;

	public KNN(ArrayList<MNISTImage> trainingSet) {
		super();
		this.trainingSet = trainingSet;
	}

	public String classify(MNISTImage newExample) {
		// create map of class -> amount of witness
		Map<String, Integer> occuranceMap = new HashMap<>();
		for (MNISTImage neighbor : findTopK(newExample, K)) {
			Integer count = occuranceMap.get(neighbor.getClassification());

			if (count == null) {
				count = 0;
			}

			occuranceMap.put(neighbor.getClassification(), count + 1);
		}

		// majority vote of neighbors
		String classfication = null;
		int maxCount = 0;
		for (Entry<String, Integer> entry : occuranceMap.entrySet()) {
			int currentValue = entry.getValue();
			if (maxCount < currentValue) {
				maxCount = currentValue;
				classfication = entry.getKey();
			}
		}

		return classfication;
		// return findTopK(newExample, K)[0].getClassification();
	}

	private MNISTImage[] findTopK(final MNISTImage newExample, int k) {
		PriorityQueue<MNISTImage> pq = new PriorityQueue<>(k,
				new Comparator<MNISTImage>() {

					@Override
					public int compare(MNISTImage o1, MNISTImage o2) {
						return -Float.compare(newExample.euclideanDistance(o1),
								newExample.euclideanDistance(o2));
					}
				});
		
		for (MNISTImage trainingExample : trainingSet) {
			if (pq.size() < k)
				pq.add(trainingExample);
			else if (pq.peek().euclideanDistance(newExample) > trainingExample
					.euclideanDistance(newExample)) {
				pq.poll();
				pq.add(trainingExample);
			}
		}

		return pq.toArray(new MNISTImage[k]);
	}
}
