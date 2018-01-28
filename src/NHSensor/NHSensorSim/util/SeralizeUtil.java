package NHSensor.NHSensorSim.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SeralizeUtil {

	public static void save(Object object, File file) {
		 ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(file));
			 oos.writeObject(object);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static Object read(File file) throws Exception {
       FileInputStream fis;
		fis = new FileInputStream(file);
       ObjectInputStream ois = new ObjectInputStream(fis);
       return ois.readObject();
	}


}
