package dataTypes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MNISTDataSet {

	private ArrayList<MNISTImage> images;

	public ArrayList<MNISTImage> getImages() {
		return images;
	}

	public int size() {
		return images.size();
	}

	public void setImages(ArrayList<MNISTImage> images) {
		this.images = images;
	}

	public MNISTDataSet() {
		images = new ArrayList<>();
	}

	public MNISTDataSet resample(double percent) {
		Collections.shuffle(this.images);
		MNISTDataSet dataSet = new MNISTDataSet();
		dataSet.images = new ArrayList<>(this.images.subList(0,
				(int) (percent * this.size())));
		return  dataSet;
		
	}

	public MNISTDataSet(String aFileName) throws IOException {
		this();

		try (BufferedReader br = new BufferedReader(new FileReader(aFileName))) {
			String line;
			while ((line = br.readLine()) != null) {
				// process each line
				images.add(new MNISTImage(line));
			}

		}
	}
}
