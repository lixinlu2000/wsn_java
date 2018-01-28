package NHSensor.NHSensorSim.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class IntelLabDatasetExtractor {
	int epochCount = 100;
	int moteCount = 58;
	Double temperature[][];
	Double humidity[][];
	Double light[][];
	Double voltage[][];
	String datasetFilePath = "D:\\research\\project\\data.txt";

	public IntelLabDatasetExtractor() {
		temperature = new Double[moteCount][epochCount];
		humidity = new Double[moteCount][epochCount];
		light = new Double[moteCount][epochCount];
		voltage = new Double[moteCount][epochCount];
	}

	public IntelLabDatasetExtractor(int epochCount) {
		this.epochCount = epochCount;
		temperature = new Double[moteCount][epochCount];
		humidity = new Double[moteCount][epochCount];
		light = new Double[moteCount][epochCount];
		voltage = new Double[moteCount][epochCount];
	}

	public void extract() {
		int moteID;
		int epochIndex;
		double value;

		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(
					datasetFilePath)));
			String line;

			while ((line = br.readLine()) != null) {
				String[] s = line.split(" +");
				// if(s.length!=8)continue;

				try {
					StringBuffer sb = new StringBuffer();

					if (s.length >= 4) {
						epochIndex = Integer.parseInt(s[2]);
						moteID = Integer.parseInt(s[3]);
						if (epochIndex >= epochCount)
							continue;

						sb.append(moteID + " " + epochIndex);
						System.out.println(sb.toString());
						// temperature
						if (s.length >= 5) {
							value = Double.parseDouble(s[4]);
							temperature[moteID - 1][epochIndex] = new Double(
									value);
							sb.append(" " + value);

							// humidity
							if (s.length >= 6) {
								value = Double.parseDouble(s[5]);
								humidity[moteID - 1][epochIndex] = new Double(
										value);
								sb.append(" " + value);
								// light
								if (s.length >= 7) {
									value = Double.parseDouble(s[6]);
									light[moteID - 1][epochIndex] = new Double(
											value);
									sb.append(" " + value);
									// voltage
									if (s.length >= 8) {
										value = Double.parseDouble(s[7]);
										voltage[moteID - 1][epochIndex] = new Double(
												value);
										sb.append(" " + value);
									}
								}
							}
						}
					}
					System.out.println(sb.toString());
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveToFile(String fileName) {
		try {
			PrintWriter fpw = new PrintWriter(new FileWriter(fileName));
			Double value;
			for (int moteID = 0; moteID < this.moteCount; moteID++) {
				for (int epochIndex = 0; epochIndex < this.epochCount; epochIndex++) {
					fpw.print(moteID);
					fpw.print(" ");
					fpw.print(epochIndex);
					value = temperature[moteID][epochIndex];
					fpw.print(" ");
					fpw.print(value);
					value = humidity[moteID][epochIndex];
					fpw.print(" ");
					fpw.print(value);
					value = light[moteID][epochIndex];
					fpw.print(" ");
					fpw.print(value);
					value = voltage[moteID][epochIndex];
					fpw.print(" ");
					fpw.print(value);
					fpw.println();
				}
			}
			fpw.flush();
			fpw.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IntelLabDatasetExtractor ie = new IntelLabDatasetExtractor();
		ie.extract();
		ie.saveToFile(".\\dataset\\extractedData100.txt");
	}

}
