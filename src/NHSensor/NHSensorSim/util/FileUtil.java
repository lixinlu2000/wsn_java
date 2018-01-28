package NHSensor.NHSensorSim.util;

import java.io.File;

public class FileUtil {
	public static File packageNameToFile(String path, String packageName) {
		String packagePath = packageName.replace('.', '/');
		String finalPath = path+packagePath;
		
		File file = new File(finalPath);
		return file;
	}
}
