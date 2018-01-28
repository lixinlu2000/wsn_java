package NHSensor.NHSensorSim.algorithm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class AlgorithmProperty implements Serializable {
	String name;
	String description;
	String classFullName; // contains package name
	String demoClassFullName;
	String experimentClassFullName;
	String experimentParamClassFullName;
	boolean isSystemAlgorithm;

	public AlgorithmProperty() {
	}

	public AlgorithmProperty(String name, String description,
			String classFullName, String demoClassFullName,
			boolean isSystemAlgorithm,
			String experimentClassFullName,
			String experimentParamClassFullName) {
		super();
		this.name = name;
		this.description = description;
		this.classFullName = classFullName;
		this.demoClassFullName = demoClassFullName;
		this.isSystemAlgorithm = isSystemAlgorithm;
		this.experimentClassFullName = experimentClassFullName;
		this.experimentParamClassFullName = experimentParamClassFullName;
		
	}


	public AlgorithmProperty(String name, String description,
			String classFullName, String demoClassFullName) {
		super();
		this.name = name;
		this.description = description;
		this.classFullName = classFullName;
		this.demoClassFullName = demoClassFullName;
		this.isSystemAlgorithm = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getClassFullName() {
		return classFullName;
	}

	public void setClassFullName(String classFullName) {
		this.classFullName = classFullName;
	}

	public String getDemoClassFullName() {
		return demoClassFullName;
	}

	public void setDemoClassFullName(String demoClassFullName) {
		this.demoClassFullName = demoClassFullName;
	}
	

	public boolean isSystemAlgorithm() {
		return isSystemAlgorithm;
	}

	public void setSystemAlgorithm(boolean isSystemAlgorithm) {
		this.isSystemAlgorithm = isSystemAlgorithm;
	}

	public String getExperimentClassFullName() {
		return experimentClassFullName;
	}

	public void setExperimentClassFullName(String experimentClassFullName) {
		this.experimentClassFullName = experimentClassFullName;
	}

	public String getExperimentParamClassFullName() {
		return experimentParamClassFullName;
	}

	public void setExperimentParamClassFullName(String experimentParamClassFullName) {
		this.experimentParamClassFullName = experimentParamClassFullName;
	}

	public static AlgorithmProperty read(File file) throws Exception {
		FileInputStream fis;
		fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		return (AlgorithmProperty) ois.readObject();
	}

	public void save(File file) {
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(this);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
