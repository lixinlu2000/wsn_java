package NHSensor.NHSensorSim.papers.ERISCROC.energy;

import NHSensor.NHSensorSim.papers.ERISCROC.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NetworkIDEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "networkID";
		String yFieldName = "consumedEnergy";
		String frameTitle = "ETDC VS IC";
		String xAxisLabel = "����ID";
		String yAxisLabel = "���ĵ�������mJ��";
		String chartTitle = "����ID��������Ӱ��";
		String adjustedVariable = "networkID";
		String filePath = "ERISCROC\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
