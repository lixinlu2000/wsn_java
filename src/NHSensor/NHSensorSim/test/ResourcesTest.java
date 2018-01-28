package NHSensor.NHSensorSim.test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;

public class ResourcesTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String packageName = "NHSensor.NHSensorSim.papers.ROCAdaptiveGridTranverseRing";
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		String path = packageName.replace('.', '/');
		Enumeration resources;
		try {
			resources = classLoader.getResources(path);
			Vector dirs = new Vector();
			while (resources.hasMoreElements()) {
				URL resource = (URL) resources.nextElement();
				// dirs.add(new File(resource.getFile()));
				System.out.println(new File(resource.getFile()));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
