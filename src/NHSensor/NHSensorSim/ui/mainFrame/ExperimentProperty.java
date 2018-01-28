package NHSensor.NHSensorSim.ui.mainFrame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import NHSensor.NHSensorSim.algorithm.AlgorithmProperty;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.experiment.ExperimentConfig;

public class ExperimentProperty implements Serializable {
	Network network;
	AlgorithmProperty algorithmProperty;
	ExperimentConfig experimentConfig;
	static final String NETWORK_FILE = "network";
	static final String ALGORITHM_FILE = "algorithm";
	static final String CONFIG_FILE = "config";
		
	public ExperimentProperty() {
		
	}

	public ExperimentProperty(Network network, AlgorithmProperty algorithmProperty, ExperimentConfig experimentConfig) {
		super();
		this.network = network;
		this.algorithmProperty = algorithmProperty;
		this.experimentConfig = experimentConfig;
	}

	public ExperimentConfig getExperimentConfig() {
		return experimentConfig;
	}

	public void setExperimentConfig(ExperimentConfig experimentConfig) {
		this.experimentConfig = experimentConfig;
	}

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

	public AlgorithmProperty getAlgorithmProperty() {
		return algorithmProperty;
	}

	public void setAlgorithmProperty(AlgorithmProperty algorithmProperty) {
		this.algorithmProperty = algorithmProperty;
	}

	public static ExperimentProperty read(File path) throws Exception {
		FileInputStream fis;
		File networkFile = new File(path, NETWORK_FILE);
		File algorithmFile = new File(path, ALGORITHM_FILE);
		File configFile = new File(path, CONFIG_FILE);
		
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(networkFile));
		Network network = (Network)ois.readObject();
		ois = new ObjectInputStream(new FileInputStream(algorithmFile));
		AlgorithmProperty algorithmProperty = (AlgorithmProperty)ois.readObject();
		ois = new ObjectInputStream(new FileInputStream(configFile));
		ExperimentConfig experimentConfig = (ExperimentConfig)ois.readObject();
		ExperimentProperty experimentProperty = new ExperimentProperty(network, algorithmProperty, experimentConfig);
		return experimentProperty;
	}

	public void save(File path) {
		ObjectOutputStream oos;
		File networkFile = new File(path, NETWORK_FILE);
		this.createFileIfNotExist(networkFile);
		File algorithmFile = new File(path, ALGORITHM_FILE);
		this.createFileIfNotExist(algorithmFile);
		File configFile = new File(path, CONFIG_FILE);
		this.createFileIfNotExist(configFile);	

		try {

			oos = new ObjectOutputStream(new FileOutputStream(networkFile));
			oos.writeObject(this.getNetwork());
			oos = new ObjectOutputStream(new FileOutputStream(algorithmFile));
			oos.writeObject(this.getAlgorithmProperty());
			oos = new ObjectOutputStream(new FileOutputStream(configFile));
			oos.writeObject(this.getExperimentConfig());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void createFileIfNotExist(File file) {
		if (!file.getParentFile().exists()) {
			System.out.println("目标文件所在路径不存在，准备创建。。。");
			if (!file.getParentFile().mkdirs()) {
				System.out.println("创建目录文件所在的目录失败！");
				return;
			}
		}

	}
}
