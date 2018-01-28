package NHSensor.NHSensorSim.papers.ESA.esa;

import NHSensor.NHSensorSim.papers.ESA.ESAUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeIDEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "networkID";
		String yFieldName = "consumedEnergy";
		String frameTitle = "ESAGBA VS ESAGAA";
		String xAxisLabel = "����ID";
		String yAxisLabel = "���ĵ�������mJ��";
		String chartTitle = "����ID��������Ӱ��";
		String adjustedVariable = "networkID";
		String filePath = "ESA\\esa";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ESAUtil.getDefaultESAUtil().showAndLogTwoESAEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}
