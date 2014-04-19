import java.io.IOException;

import kNearestNeighbors.KNN;
import dataTypes.MNISTDataSet;

public class Main {

	public static void main(String[] args) throws IOException {
		String trainFile = "./input/train.txt";
		String validateFile = "./input/validate1.txt";
		long startTime = System.currentTimeMillis();

		MNISTDataSet trainSet = new MNISTDataSet(trainFile);
		long endTime = System.currentTimeMillis();
		System.out.println("done training, it took " + (endTime - startTime));
		MNISTDataSet validateSet = new MNISTDataSet(validateFile);
		//System.out.println(trainSet.getImages().get(300));
		KNN knn = new KNN(trainSet.getImages());
		System.out.println(validateSet.getImages().get(500).getClassification());
		System.out.println(knn.classify(validateSet.getImages().get(500)));
		System.out.println(validateSet.getImages().get(600).getClassification());
		System.out.println(knn.classify(validateSet.getImages().get(600)));
		
		//OldMain.readtrainFile(fileName);

		endTime = System.currentTimeMillis();
		System.out.println("Took " + (endTime - startTime) + " ms");
	}

}
