package NHSensor.NHSensorSim.algorithm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import NHSensor.NHSensorSim.algorithm.algorithmDemoInstance.AlgorithmDemo;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.experiment.AlgorithmExperiment;
import NHSensor.NHSensorSim.experiment.ExperimentParam;
import NHSensor.NHSensorSim.query.QueryBase;
import NHSensor.NHSensorSim.ui.mainFrame.NetworkShowPanel;
import cn.org1.user1.AlgParam;

public class AlgorithmModel {
	AlgorithmProperty algorithmProperty;
	ExperimentParam experimentParam;
	Network network;
	Algorithm algorithm;
	Simulator simulator;
	Param param;
	QueryBase query;
	AlgorithmExperiment algorithmExperiment;
	NetworkShowPanel networkShowPanel;
	
	public AlgorithmModel(AlgorithmProperty algorithmProperty) throws Exception {
		this.algorithmProperty = algorithmProperty;
		Class experimentParamClass = Class.forName(this.algorithmProperty.getExperimentParamClassFullName());
		experimentParam = (ExperimentParam) experimentParamClass.newInstance();
		this.setExperimentParam(experimentParam);
	}

	public AlgorithmProperty getAlgorithmProperty() {
		return algorithmProperty;
	}

	public ExperimentParam getExperimentParam() {
		return experimentParam;
	}

	public void setExperimentParam(ExperimentParam experimentParam) throws Exception {
		this.experimentParam = experimentParam;
		
		Class experimentClass  = Class.forName(this.algorithmProperty.getExperimentClassFullName());
		Class experimentParamClass = Class.forName(this.algorithmProperty.getExperimentParamClassFullName());
		Constructor constructor = null;
		constructor = experimentClass.getConstructor(new Class[]{experimentParamClass});
		algorithmExperiment = (AlgorithmExperiment)constructor.newInstance(new Object[]{experimentParam});
		this.network = this.algorithmExperiment.getNetwork();
		this.algorithm = this.algorithmExperiment.getAlgorithm();
		this.param = this.algorithmExperiment.getAlgParam();
		this.query = this.algorithmExperiment.getQuery();		
	}
	
	public void runAlgorithm() throws Exception{
		Class experimentClass = algorithmExperiment.getClass();
		Method runMethod = experimentClass.getMethod("run",
				new Class[] {});
		runMethod.invoke(algorithmExperiment, new Object[] {});
	}
	
	public Network getNetwork() {
		return network;
	}

	public Algorithm getAlgorithm() {
		return algorithm;
	}

	public Simulator getSimulator() {
		return simulator;
	}

	public Param getParam() {
		return param;
	}

	public QueryBase getQuery() {
		return query;
	}

	public NetworkShowPanel getNetworkShowPanel() {
		return networkShowPanel;
	}

	public void setNetworkShowPanel(NetworkShowPanel networkShowPanel) {
		this.networkShowPanel = networkShowPanel;
	}
	
	public void updateViews() {
		if(this.networkShowPanel!=null) this.networkShowPanel.setNetwork(this.getNetwork());
	}

	public static void main(String[] args) {
		String className = "ThirdPartyAlg1";
		String description = "UserAlg";
		String packageName = "cn.org1.user1";
		String classFullName = packageName+'.'+className; // contains
		String demoClassFullName = classFullName+"Demo";
		boolean isSystemAlgorithm = false;
		String experimentClassFullName = classFullName+"EnergyExperiment";
		String experimentParamClassFullName =packageName+'.'+"AlgParam";
		
		String name;

		try {
			AlgorithmDemo algorithmDemo = (AlgorithmDemo) Class.forName(
					demoClassFullName).newInstance();
			name = algorithmDemo.getRunnedAlgorithmInstance().getName();

			AlgorithmProperty ap = new AlgorithmProperty(name, description,
					classFullName, demoClassFullName, isSystemAlgorithm,
					experimentClassFullName, experimentParamClassFullName);
			AlgorithmModel algorithmModel = new AlgorithmModel(ap);
			
			AlgParam AlgParam = new AlgParam();	
			algorithmModel.setExperimentParam(AlgParam);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
