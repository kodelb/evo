package dataTypes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MNISTDataSet {

	private ArrayList<MNISTImage> images;

	public ArrayList<MNISTImage> getImages() {
		return images;
	}

	public void setImages(ArrayList<MNISTImage> images) {
		this.images = images;
	}

	public MNISTDataSet() {
		images = new ArrayList<>();
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
