package NHSensor.NHSensorSim.papers.CSA.locationsNonuniformBytesRate.English;

import NHSensor.NHSensorSim.papers.CSA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeIDEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "networkID";
		String yFieldName = "maintainLocationsFrameNumRate";
		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] {true, true, false});
		String frameTitle = "ESA-GBA VS CSA-CDA";
		String xAxisLabel = "Network ID";
		String yAxisLabel = "Cost for maintaining neighbors' locations";
		String chartTitle = "网络节点数目对能量的影响";
		String adjustedVariable = "networkID";
		String filePath = "CSA\\locationsNonUniformBytes\\English";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}
