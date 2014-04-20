import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import kNearestNeighbors.KNN;
import dataTypes.MNISTDataSet;

public class Main {

	public enum Mode {
		VALIDATE1, VALIDATE2, TEST
	}

	final static String trainFile = "./input/train.txt";
	final static String validateFile1 = "./input/validate1.txt";
	final static String validateFile2 = "./input/validate2.txt";
	final static String testFile = "./input/test.txt";
	final static String outFile = "./input/prediction.txt";
	final static Mode MODE = Mode.VALIDATE1;

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {

		long startTime = System.currentTimeMillis();
		System.out.println("training...");
		MNISTDataSet trainSet = new MNISTDataSet(trainFile);
		trainSet.resample(0.25);
		KNN knn = new KNN(trainSet.getImages());
		long endTime = System.currentTimeMillis();
		System.out.println("training done in " + (endTime - startTime) / 1000);

		switch (MODE) {
		case VALIDATE1:
			System.out.println("validation: "
					+ knn.getAccuracy(new MNISTDataSet(validateFile1)));
			break;
		case VALIDATE2:
			System.out.println("validation: "
					+ knn.getAccuracy(new MNISTDataSet(validateFile2)));
			break;
		case TEST:
			writePrediction(outFile, knn.classify(new MNISTDataSet(testFile)));
			break;
		default:
			break;
		}

		endTime = System.currentTimeMillis();
		System.out.println("Took " + (endTime - startTime) / 1000 + " seconds");
	}

	private static void writePrediction(String filename,
			ArrayList<String> predictions) throws IOException {
		Path path = Paths.get(filename);
		try (BufferedWriter writer = Files.newBufferedWriter(path,
				StandardCharsets.UTF_8)) {
			for (String line : predictions) {
				writer.write(line);
				writer.newLine();
			}
		}
	}

}
