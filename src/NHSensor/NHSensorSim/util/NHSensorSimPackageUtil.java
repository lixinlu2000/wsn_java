package NHSensor.NHSensorSim.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;

public class NHSensorSimPackageUtil {

	public static Vector getClasses(String packageName) throws IOException,
			ClassNotFoundException {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		String path = packageName.replace('.', '/');
		Enumeration resources = classLoader.getResources(path);
		Vector dirs = new Vector();
		while (resources.hasMoreElements()) {
			URL resource = (URL) resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		Vector classNames = new Vector();
		File directory;
		for (int i = 0; i < dirs.size(); i++) {
			directory = (File) dirs.elementAt(i);
			classNames.addAll(findClasses(directory, packageName));
		}
		return classNames;
	}

	public static Vector findClasses(File directory, String packageName)
			throws ClassNotFoundException {
		Vector classes = new Vector();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		File file;
		for (int i = 0; i < files.length; i++) {
			file = files[i];
			if (file.isDirectory()) {
				classes.addAll(findClasses(file, packageName + "."
						+ file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(packageName
						+ '.'
						+ file.getName().substring(0,
								file.getName().length() - 6));
			}
		}
		return classes;
	}

}
