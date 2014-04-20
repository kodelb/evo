package kNearestNeighbors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import dataTypes.MNISTDataSet;
import dataTypes.MNISTImage;

public class KNN {
	final static int K = 1;

	private ArrayList<MNISTImage> trainingSet;

	public KNN(ArrayList<MNISTImage> trainingSet) {
		super();
		this.trainingSet = trainingSet;
	}

	@SuppressWarnings("unused")
	public String classify(MNISTImage newExample) {
		if (1 == K) {
			return Collections.min(this.trainingSet,
					new DistanceComparator(newExample)).getClassification();
		} else {
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
		}
		// return findTopK(newExample, K)[0].getClassification();
	}

	public ArrayList<String> classify(MNISTDataSet dataSet) {
		ArrayList<String> classfications = new ArrayList<>(dataSet.size());
		for (MNISTImage image : dataSet.getImages()) {
			classfications.add(this.classify(image));
		}

		return classfications;
	}

	public double getAccuracy(MNISTDataSet dataSet)
			throws InterruptedException, ExecutionException {
		int successCount = 0;
		ArrayList<Future<Integer>> futuresList = new ArrayList<>();
		int nrOfProcessors = Runtime.getRuntime().availableProcessors();
		ExecutorService eservice = Executors.newFixedThreadPool(nrOfProcessors);
		int chunk = dataSet.size() / nrOfProcessors;
		for (int i = 0; i < nrOfProcessors; i++) {
			futuresList.add(eservice.submit(new AccuracyTask(dataSet
					.getImages().subList(i * chunk, (i + 1) * chunk), this)));
		}

		futuresList.add(eservice.submit(new AccuracyTask(dataSet.getImages()
				.subList((dataSet.size() / nrOfProcessors) * nrOfProcessors,
						dataSet.size()), this)));

		eservice.shutdown();

		for (Future<Integer> future : futuresList) {
			successCount += future.get();
		}

		return ((double) successCount) / dataSet.size();
	}

	private MNISTImage[] findTopK(final MNISTImage newExample, int k) {
		PriorityQueue<MNISTImage> pq = new PriorityQueue<>(k,
				new DistanceComparator(newExample, true));

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

class DistanceComparator implements Comparator<MNISTImage> {
	private MNISTImage currentExample;
	private boolean reverse;

	public DistanceComparator(MNISTImage currentExample) {
		this(currentExample, false);
	}

	public DistanceComparator(MNISTImage currentExample, boolean reverse) {
		super();
		this.currentExample = currentExample;
		this.reverse = reverse;
	}

	@Override
	public int compare(MNISTImage o1, MNISTImage o2) {
		if (reverse) {
			return Float.compare(currentExample.euclideanDistance(o2),
					currentExample.euclideanDistance(o1));
		} else {
			return Float.compare(currentExample.euclideanDistance(o1),
					currentExample.euclideanDistance(o2));
		}
	}

}

class AccuracyTask implements Callable<Integer> {
	private List<MNISTImage> images;
	private KNN knn;

	public AccuracyTask(List<MNISTImage> list, KNN knn) {
		super();
		this.images = list;
		this.knn = knn;
	}

	@Override
	public Integer call() throws Exception {
		int successCount = 0;

		for (MNISTImage image : images) {
			if (image.getClassification().equals(knn.classify(image))) {
				successCount++;
			}
		}

		return successCount;
	}

}
