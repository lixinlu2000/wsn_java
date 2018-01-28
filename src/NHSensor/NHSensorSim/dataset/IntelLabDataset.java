package NHSensor.NHSensorSim.dataset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import NHSensor.NHSensorSim.core.Time;

public class IntelLabDataset {
	int epochCount = 100;
	int moteCount = 54;

	Double temperature[][];
	Double humidity[][];
	Double light[][];
	Double voltage[][];
	String datasetFilePath = ".\\dataset\\extractedData100.txt";

	public IntelLabDataset() {
		temperature = new Double[moteCount][epochCount];
		humidity = new Double[moteCount][epochCount];
		light = new Double[moteCount][epochCount];
		voltage = new Double[moteCount][epochCount];
	}

	public IntelLabDataset(int epochCount) {
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
				String[] s = line.split(" ");
				epochIndex = Integer.parseInt(s[1]);
				moteID = Integer.parseInt(s[0]);
				if (epochIndex < epochCount) {
					try {
						// temperature
						if (s[2] == null) {
							temperature[moteID][epochIndex] = null;
						} else {
							value = Double.parseDouble(s[2]);
							temperature[moteID][epochIndex] = new Double(value);
						}

						// humidity
						if (s[3] == null) {
							humidity[moteID][epochIndex] = null;
						} else {
							value = Double.parseDouble(s[3]);
							humidity[moteID][epochIndex] = new Double(value);
						}

						// light
						if (s[4] == null) {
							light[moteID][epochIndex] = null;
						} else {
							value = Double.parseDouble(s[4]);
							light[moteID][epochIndex] = new Double(value);
						}

						// voltage
						if (s[5] == null) {
							voltage[moteID][epochIndex] = null;
						} else {
							value = Double.parseDouble(s[5]);
							voltage[moteID][epochIndex] = new Double(value);
						}
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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

	public int getEpochCount() {
		return epochCount;
	}

	public void setEpochCount(int epochCount) {
		this.epochCount = epochCount;
	}

	public int getMoteCount() {
		return moteCount;
	}

	public void setMoteCount(int moteCount) {
		this.moteCount = moteCount;
	}

	public Double[][] getTemperature() {
		return temperature;
	}

	public Double getTemperature(int nodeID, Time time) {
		int epochIndex = (int) time.getTime();
		return this.temperature[nodeID][epochIndex];
	}

	public Double getHumidity(int nodeID, Time time) {
		int epochIndex = (int) time.getTime();
		return this.humidity[nodeID][epochIndex];
	}

	public Double getLight(int nodeID, Time time) {
		int epochIndex = (int) time.getTime();
		return this.light[nodeID][epochIndex];
	}

	public Double getVoltage(int nodeID, Time time) {
		int epochIndex = (int) time.getTime();
		return this.voltage[nodeID][epochIndex];
	}

	public void setTemperature(Double[][] temperature) {
		this.temperature = temperature;
	}

	public Double[][] getHumidity() {
		return humidity;
	}

	public void setHumidity(Double[][] humidity) {
		this.humidity = humidity;
	}

	public Double[][] getLight() {
		return light;
	}

	public void setLight(Double[][] light) {
		this.light = light;
	}

	public Double[][] getVoltage() {
		return voltage;
	}

	public void setVoltage(Double[][] voltage) {
		this.voltage = voltage;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IntelLabDataset ie = new IntelLabDataset();
		ie.extract();
	}

}
