package dataTypes;

import java.util.Arrays;

public class MNISTImage {
	public static int rows = 28;
	public static int cols = 28;

	private float[] image;
	private String classification;

	public float[] getImage() {
		return image;
	}

	public void setImage(float[] image) {
		this.image = image;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public MNISTImage() {
		image = new float[rows * cols];
	}

	public MNISTImage(String line) {
		this();

		String[] splitted = line.split(",");
		classification = splitted[0];
		for (int i = 1; i < splitted.length; i++) {
			image[i - 1] = Float.parseFloat(splitted[i]);

		}

	}

	public float euclideanDistance(MNISTImage other) {
		float sum = 0;

		for (int i = 0; i < this.image.length; i++) {
			float tmp = (this.image[i] - other.image[i]);
			sum = sum + tmp * tmp;
		}

		return sum;
	}

	@Override
	public String toString() {
		return "MNISTImage [classification=" + classification + ", image="
				+ Arrays.toString(image) + "]";
	}

	public float[][] toPicture() {
		float pic[][] = new float[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				pic[i][j] = image[i + j * rows];
			}
		}

		return pic;
	}
}
