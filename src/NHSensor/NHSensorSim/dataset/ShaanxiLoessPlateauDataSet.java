package NHSensor.NHSensorSim.dataset;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import NHSensor.NHSensorSim.core.Time;
import NHSensor.NHSensorSim.shape.Position;

public class ShaanxiLoessPlateauDataSet extends DataSet {
	String datasetFilePath = ".\\dataset\\dem.txt";
	double data[][];
	int xMax = 486;
	int yMax = 327;
	double min;
	double max;
	
	protected void parseFile() {
		BufferedReader br;
		int xIndex = 0;
		int yIndex = 0;
		double value;
		
		try {
			br = new BufferedReader(new FileReader(new File(
					datasetFilePath)));
			String line;

			while ((line = br.readLine()) != null) {
				String[] s = line.split(" ");

				for(xIndex=0;xIndex<xMax;xIndex++) {
					value = Double.parseDouble(s[xIndex]);
					data[yIndex][xIndex] = value;
				}
				yIndex++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public ShaanxiLoessPlateauDataSet() {
		this.data = new double[yMax][xMax];
		this.parseFile();
	}

	@Override
	public double getValue(Position pos, Time time, int attributeID) {
		return this.data[(int)pos.getX()][(int)pos.getY()];
		// return 16;
	}
	
	public double[][] getData() {
		return data;
	}


	public String toString() {
		return data.toString();
	}
	
	public String toMatlabArray(String arrayName) {
		StringBuffer sb = new StringBuffer();
		sb.append(arrayName);
		sb.append(" = [\n");
		for(int yIndex=0;yIndex<this.data.length;yIndex++) {
			for(int xIndex=0;xIndex<this.data[yIndex].length;xIndex++) {
				sb.append(this.data[yIndex][xIndex]);
				sb.append(" ");
			}
			sb.append(";\n");
		}
		sb.append("]");
		return sb.toString();
	}
	
	public void writeMatlabArrayToFile(String fileName, String arrayName) {
		BufferedWriter bw;
		
		try {
			bw = new BufferedWriter(new FileWriter(new File(
					fileName)));
			bw.write(this.toMatlabArray(arrayName));
			bw.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		ShaanxiLoessPlateauDataSet dataset = new ShaanxiLoessPlateauDataSet();
		System.out.println(dataset);
		System.out.println(dataset.getData()[1][1]);
		//System.out.println(dataset.toMatlabArray("matlabArray"));
		dataset.writeMatlabArrayToFile("matlabArray", "dem");
	}

}
