package NHSensor.NHSensorSim.papers.CSA.energy.English;

import NHSensor.NHSensorSim.papers.CSA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeIDEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "networkID";
		String yFieldName = "consumedEnergy";
		String frameTitle = "ESA-GBA VS CSA-CDA";
		String xAxisLabel = "Network ID";
		String yAxisLabel = "Energy Consumption (mJ)";
		String chartTitle = "����ڵ���Ŀ��������Ӱ��";
		String adjustedVariable = "networkID";
		String filePath = "CSA\\energy\\English";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}
