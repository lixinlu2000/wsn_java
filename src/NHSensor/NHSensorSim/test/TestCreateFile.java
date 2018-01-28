package NHSensor.NHSensorSim.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import NHSensor.NHSensorSim.util.FileAndSceenStream;

public class TestCreateFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			File file = new File("result\\rsa\\test\\test.txt");
			File filePath = file.getParentFile();

			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
