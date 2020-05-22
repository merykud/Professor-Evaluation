import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		try {
			
			
			makeNewFolder();

			PrintWriter print = new PrintWriter(
					new File("C:/JavaProjects/EvaluationProject/Survey_calc/Survey_Avg_Calc.csv"));

			for (int i = 1; i < 11; i++) {
				// opening files one by one
				File file = new File("C:/JavaProjects/EvaluationProject/Course" + i + ".txt");
				writeToCSV(creatingCSV(readingFiles(file)), i);

				System.out.print(calculateAverage(readingFiles(file), i));

				// writing data to Survey_Avg_Calc
				print.write(calculateAverage(readingFiles(file), i));

			}
			print.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	// writing csv to different files
	private static void writeToCSV(String str, int counter) throws FileNotFoundException {

		PrintWriter print = new PrintWriter(
				new File("C:/JavaProjects/EvaluationProject/Survey_calc/Course" + counter + ".csv"));

		print.write(str);
		print.close();

	}

	// calculating average and returning csv string
	private static String calculateAverage(ArrayList<String> list, int counter){

		StringBuilder builder = new StringBuilder();

		// For each course function is called once and counter is incremented
		builder.append("CourseNum" + counter);
		builder.append(",");

		// list.size() is number of lines in list, each line is one students
		// calculation
		int noOfStudents = list.size();

		builder.append(noOfStudents);
		builder.append(",");

		int evaluationNumber;

		double[] arrOfAvgValues = new double[500];
		int[] arrOfInt = new int[500];
		int[] peopleThatAnswered = new int[100];

		for (int i = 0; i < list.size(); i++) {
			String line = list.get(i);

			for (int j = 0; j < line.length(); j++) {

				// not answered questions(blank spaces) are 'x'
				if (line.charAt(j) != 'x') {

					evaluationNumber = Character.getNumericValue(line.charAt(j));
					arrOfInt[j] = evaluationNumber;

					// if the string is '-1'
				} else {

					// blank spaces become -1
					arrOfInt[j] = -1;

				}

				if (arrOfInt[j] != -1) {
					// sum
					arrOfAvgValues[j] += arrOfInt[j];

					// number of all non blank fields per question
					peopleThatAnswered[j]++;

				}

			}

		}
		DecimalFormat df = new DecimalFormat("#.###");

		int noOfQuestions = 34;

		// getting the average and appending it to the string
		for (int i = 0; i < noOfQuestions; i++) {

			//divizion by zero possible
			if (peopleThatAnswered[i] != 0) {
				arrOfAvgValues[i] = arrOfAvgValues[i] / peopleThatAnswered[i];
				builder.append(df.format(arrOfAvgValues[i]));
				builder.append(",");
			} else {
				builder.append(" ");
				builder.append(",");

			}

		}
		builder.append("\n");

		return builder.toString();
	}

	private static void makeNewFolder() {
		File folder = new File("C:/JavaProjects/EvaluationProject/Survey_calc");
		try {
			folder.mkdir();
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	private static String creatingCSV(ArrayList<String> list) {

		StringBuilder builder = new StringBuilder();
		String stringForCsv = "";

		for (int i = 0; i < list.size(); i++) {
			String line = list.get(i);
			// adding one because beggining index of student is one
			int studentNO = i + 1;
			builder.append("Student" + studentNO);
			builder.append(",");
			for (int j = 0; j < line.length(); j++) {
				int x;

				// appending values if the character is not '-'
				if (line.charAt(j) != 'x') {

					x = Character.getNumericValue(line.charAt(j));

					builder.append(x);
					builder.append(",");
				} else {

					// after '-' sign is number 1, both of them are ignored, put
					// space instead
					builder.append(" ");
					builder.append(",");

				}

			}
			builder.append("\n");
		}
		stringForCsv = builder.toString();
		return stringForCsv;
	}

	// reading from file and returning an array list of converted values
	private static ArrayList<String> readingFiles(File files) throws Exception {

		FileReader file = new FileReader(files);
		BufferedReader reader = new BufferedReader(file);

		ArrayList<String> list = new ArrayList<String>();

		String line = reader.readLine();
		int counter = 1;
		while (line != null) {

			list.add("Student" + counter + " " + line.substring(10, line.length()));

			counter++;
			line = reader.readLine();

		}

		ArrayList<String> convertedGrades = null;
		convertedGrades = new ArrayList<String>();
		String charLetterGrades = null;

		for (int i = 0; i < list.size(); i++) {

			int counter1 = 0;
			charLetterGrades = list.get(i);
			StringBuilder strBuilder = new StringBuilder();

			for (int j = charLetterGrades.indexOf(' ') + 1; j < charLetterGrades.length(); j++) {

				switch (charLetterGrades.charAt(j)) {
				case 'A':
					strBuilder.append("4");
					break;
				case 'B':
					strBuilder.append("3");
					break;
				case 'C':
					strBuilder.append("2");
					break;
				case 'D':
					strBuilder.append("1");
					break;
				case 'E':
					strBuilder.append("0");
					break;
				case ' ':
					strBuilder.append("x");
					break;

				default:
					break;

				}

			}

			String string = strBuilder.toString();
			convertedGrades.add(counter1, string);
			counter1++;
		}
		reader.close();
		return convertedGrades;

	}
}
