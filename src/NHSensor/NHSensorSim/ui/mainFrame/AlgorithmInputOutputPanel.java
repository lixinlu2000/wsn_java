package NHSensor.NHSensorSim.ui.mainFrame;

import java.awt.GridLayout;

import javax.swing.JPanel;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.algorithm.AlgorithmModel;
import NHSensor.NHSensorSim.algorithm.AlgorithmProperty;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Param;

public class AlgorithmInputOutputPanel extends JPanel {
	AlgorithmModel algorithmModel;
	AlgorithmParamPropertyPage algorithmParamPropertyPage;
	ObjectPropertyPage networkProperty;
	ObjectPropertyPage algorithmkProperty;
	ObjectPropertyPage paramProperty;
		
	public void setAlgorithmModel(AlgorithmModel algorithmModel) throws Exception {
		algorithmParamPropertyPage = new AlgorithmParamPropertyPage(algorithmModel);
		this.add(algorithmParamPropertyPage);
		
/*		networkProperty = new ObjectPropertyPage(Network.class.getCanonicalName(), algorithmModel.getNetwork());
		this.add(networkProperty);
		
		algorithmkProperty = new ObjectPropertyPage(Algorithm.class.getCanonicalName(), algorithmModel.getAlgorithm());
		this.add(algorithmkProperty);

*/		paramProperty = new ObjectPropertyPage(Param.class.getCanonicalName(), algorithmModel.getParam());
		this.add(paramProperty);
	}

	public AlgorithmModel getAlgorithmModel() {
		return algorithmModel;
	}

	public AlgorithmInputOutputPanel() {
		setLayout(new GridLayout(2, 1, 0, 0));
	}

	public AlgorithmProperty getAlgorithmProperty() {
		return algorithmModel.getAlgorithmProperty();
	}
}
