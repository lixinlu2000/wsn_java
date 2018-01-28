package NHSensor.NHSensorSim.papers.CSA.timeNonuniform.English;

import NHSensor.NHSensorSim.papers.CSA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class MaxHoleRadiusTime {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "maxHoleRadius";
		String yFieldName = "communicationTime";
		String frameTitle = "ESA-GBA VS CSA-CDA";
		String xAxisLabel = "Maximal radius of holes";
		String yAxisLabel = "Time latency (s)";
		String chartTitle = "感知数据消息大小对能量消耗的影响";
		String adjustedVariable = "maxHoleRadius";
		String filePath = "CSA\\bytesNonuniform\\English";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
