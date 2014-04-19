import java.util.Arrays;

public class DataItem {

	double[] inputs = new double[784];
	int digit_num;
	int predict_num;
	int counter = 0;

	public int getPredict_num() {
		return predict_num;
	}

	public void setPredict_num(int predict_num) {
		this.predict_num = predict_num;
	}

	public DataItem() {
	}

	public void add(double num) {
		this.inputs[counter] = num;
		counter++;
	}

	public double[] getInputs() {
		return inputs;
	}

	public void setInputs(double[] inputs) {
		this.inputs = inputs;
	}

	public int getDigit_num() {
		return digit_num;
	}

	public void setDigit_num(int digit_num) {
		this.digit_num = digit_num;
	}
	
	

	@Override
	public String toString() {
		return "DataItem [digit_num=" + digit_num + ", inputs="
				+ Arrays.toString(inputs) + "]";
	}

	public void print() {
		System.out.println(toString());
//		System.out.print("The number:" + this.digit_num + "-  ");
//		for (int i = 0; i < this.inputs.length; i++) {
//			System.out.print(this.inputs[i] + ",");
//		}
	}

}
