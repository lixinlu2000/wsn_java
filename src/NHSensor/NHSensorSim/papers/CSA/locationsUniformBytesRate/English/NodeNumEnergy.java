package NHSensor.NHSensorSim.papers.CSA.locationsUniformBytesRate.English;

import NHSensor.NHSensorSim.papers.CSA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "maintainLocationsFrameNumRate";
		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] { true, true, false });
		String frameTitle = "ESA-GBA VS CSA-CDA";
		String xAxisLabel = "Number of sensors";
		String yAxisLabel = "Cost for maintaining neighbors' locations";
		String chartTitle = "����ڵ���Ŀ��������Ӱ��";
		String adjustedVariable = "nodeNum";
		String filePath = "CSA\\locationsUniformBytes\\English";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}
