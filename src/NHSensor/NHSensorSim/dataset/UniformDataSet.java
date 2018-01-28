package NHSensor.NHSensorSim.dataset;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import NHSensor.NHSensorSim.core.Time;
import NHSensor.NHSensorSim.shape.Position;

public class UniformDataSet extends DataSet {
	double maxValue;
	Random r;
	double minValue = 0;

	public UniformDataSet(double maxValue, int randomNumber) {
		this.maxValue = maxValue;
		r = new Random(randomNumber);
	}

	public UniformDataSet(double minValue, double maxValue, int randomNumber) {
		super();
		this.maxValue = maxValue;
		this.minValue = minValue;
		r = new Random(randomNumber);
	}

	@Override
	public double getValue(Position pos, Time time, int attributeID) {
		return this.minValue+(this.maxValue-this.minValue)*r.nextDouble();
		// return 16;
	}
	
	public double[][] toArray(int yMax, int xMax) {
		double[][] array = new double[yMax][xMax];
		
		for(int yIndex=0;yIndex<yMax;yIndex++) {
			for(int xIndex=0;xIndex<xMax;xIndex++) {
				array[yIndex][xIndex] = this.getValue(new Position(xIndex, yIndex), new Time(0), 0);
			}
		}
		return array;
	}
	
	
	public String toMatlabArray(String arrayName, int yMax, int xMax) {
		StringBuffer sb = new StringBuffer();
		sb.append(arrayName);
		sb.append(" = [\n");
		double[][] data = this.toArray(yMax, xMax);
		
		for(int yIndex=0;yIndex<data.length;yIndex++) {
			for(int xIndex=0;xIndex<data[yIndex].length;xIndex++) {
				sb.append(data[yIndex][xIndex]);
				sb.append(" ");
			}
			sb.append(";\n");
		}
		sb.append("]");
		return sb.toString();
	}
	
	public void writeMatlabArrayToFile(String fileName, String arrayName, int yMax, int xMax) {
		BufferedWriter bw;
		
		try {
			bw = new BufferedWriter(new FileWriter(new File(
					fileName)));
			bw.write(this.toMatlabArray(arrayName, yMax, xMax));
			bw.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}
	
	public static void main(String[] args) {
		UniformDataSet dataset = new UniformDataSet(946.37, 1077.9, 1); 
		//System.out.println(dataset.toMatlabArray("matlabArray"));
		dataset.writeMatlabArrayToFile("matlabArrayUniform", "dem", 100, 100);
	}
}
