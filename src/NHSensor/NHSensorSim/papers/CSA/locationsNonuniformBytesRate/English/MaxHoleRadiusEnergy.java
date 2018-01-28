package NHSensor.NHSensorSim.papers.CSA.locationsNonuniformBytesRate.English;

import NHSensor.NHSensorSim.papers.CSA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class MaxHoleRadiusEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "maxHoleRadius";
		String yFieldName = "maintainLocationsFrameNumRate";
		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] {true, true, false});
		String frameTitle = "ESA-GBA VS CSA-CDA";
		String xAxisLabel = "Maximal radius of holes";
		String yAxisLabel = "Cost for maintaining neighbors' locations";
		String chartTitle = "感知数据消息大小对能量消耗的影响";
		String adjustedVariable = "maxHoleRadius";
		String filePath = "CSA\\locationsNonUniformBytes\\English";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
