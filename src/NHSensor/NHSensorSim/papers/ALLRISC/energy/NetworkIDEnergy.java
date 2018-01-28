package NHSensor.NHSensorSim.papers.ALLRISC.energy;

import NHSensor.NHSensorSim.papers.ALLRISC.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NetworkIDEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "networkID";
		String yFieldName = "consumedEnergy";
		String frameTitle = "ETDC VS IC";
		String xAxisLabel = "网络ID";
		String yAxisLabel = "消耗的能量（mJ）";
		String chartTitle = "网络ID对能量的影响";
		String adjustedVariable = "networkID";
		String filePath = "ETDC\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
