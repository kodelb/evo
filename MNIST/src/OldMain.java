import java.util.ListIterator;
import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

public class OldMain {

	private static PrintStream cOUT = System.out;
	static ArrayList<DataItem> train40k = new ArrayList<DataItem>();
	static ArrayList<DataItem> validate10k = new ArrayList<DataItem>();
	static int[] predictNum = new int[10000];
	static int[] digitNum = new int[10000];

	public static void oldmain(String[] args) throws IOException {

		// Scanner input = new Scanner(System.in);
		// System.out.print("Enter the file path+name.. ");
		// String path = input.next();
		String train = "C:/Users/fvx463/Desktop/train.txt";
		String validate1 = "C:/Users/fvx463/Desktop/validate1.txt";
		String validate2 = "C:/Users/fvx463/Desktop/validate2.txt";
		// String in =path;
		readtrainFile(train);
		readvalidateFile(validate1);
		init();
		validate();

	}

	public static void readvalidateFile(String validate) throws IOException {

		String fLine;
		int counter = 0;
		BufferedReader br = new BufferedReader(new FileReader(validate));

		cOUT.println("New digit from - " + validate);
		while ((fLine = br.readLine()) != null) {
			DataItem digit = new DataItem();
			StringTokenizer st = new StringTokenizer(fLine);
			while (st.hasMoreTokens()) {
				String s = st.nextToken();
				String[] dataArray = s.split(",");
				System.out.println();
				for (int i = 0; i < dataArray.length; i++) {

					if (i == 0) {
						digit.setDigit_num(Integer.parseInt(dataArray[0]));

					} else {
						digit.add(Double.parseDouble(dataArray[i]));

					}
				}

			}

			validate10k.add(digit);

		}

		ListIterator<DataItem> lit = validate10k.listIterator();
		int count = 0;
		while (lit.hasNext()) {
			DataItem d = lit.next();
			System.out.print(count);
			d.print();
			System.out.println();
			digitNum[count] = d.getDigit_num();
			d.setPredict_num((int) ((Math.random() * (9 - 1 + 1)) + 1));
			predictNum[count] = d.getPredict_num();
			count++;

		}

	}

	public static void readtrainFile(String fpath) throws IOException {

		String fLine;
		int counter = 0;
		BufferedReader br = new BufferedReader(new FileReader(fpath));

		cOUT.println("New digit from - " + fpath);
		while ((fLine = br.readLine()) != null) {
			DataItem digit = new DataItem();
			//StringTokenizer st = new StringTokenizer(fLine);
			//while (st.hasMoreTokens()) {
				//String s = st.nextToken();
				String[] dataArray = fLine.split(",");
				// System.out.println();
				for (int i = 0; i < dataArray.length; i++) {

					if (i == 0) {
						digit.setDigit_num(Integer.parseInt(dataArray[0]));
					} else {
						digit.add(Double.parseDouble(dataArray[i]));
					}
				//}

			}

			train40k.add(digit);

		}
		
		train40k.get(0).print();

//		ListIterator<DataItem> lit = train40k.listIterator();
//		int count = 0;
//		while (lit.hasNext()) {
//			DataItem d = lit.next();
//			System.out.print(count);
//			d.print();
//			System.out.println();
//			count++;
//
//		}

	}

	public static void init() {
		for (int g = 0; g < (predictNum.length); g++) {
			// predictNum[g] = (int) ((Math.random() * (9 - 1 + 1)) +
			// 1);//random
			predictNum[g] = digitNum[g];// 100%

		}

	}

	public static void validate() {
		int validate = 0;

		for (int k = 0; k < digitNum.length; k++) {
			// System.out.print("$$" + digitNum[k] + "##" + predictNum[k] +
			// ",");
			if (digitNum[k] == predictNum[k]) {
				validate++;
			}

		}
		System.out.println("accurate" + validate / digitNum.length * 100 + "%");
	}
}
