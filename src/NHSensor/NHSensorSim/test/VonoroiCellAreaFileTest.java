package NHSensor.NHSensorSim.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class VonoroiCellAreaFileTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		BufferedReader fr;
		FileWriter fw;
		String line;
		String end = "";
		//String end = "\n";
		try {
			fr = new BufferedReader(new FileReader("area.txt"));
			fw = new FileWriter("areaJava.txt");
			fw.write("{"+end);
			line = fr.readLine().trim();
			fw.write(line+end);
			line = fr.readLine();
			while(line!=null&&!line.equals("")) {
				line = line.trim();
				if(!line.equals("NaN"))
					fw.write(","+line+end);
				else {
					fw.write(","+"0"+end);
				}
				line = fr.readLine();
			}
			fw.write("}"); 
			fw.flush();  
			System.out.println("End");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
