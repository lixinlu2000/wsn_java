package NHSensor.NHSensorSim.papers.CSA.energy.English;

import NHSensor.NHSensorSim.papers.CSA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class SenseDataSizeEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "answerMessageSize";
		String yFieldName = "consumedEnergy";
		String frameTitle = "ESA-GBA VS CSA-CDA";
		String xAxisLabel = "Sensed data size (byte)";
		String yAxisLabel = "Energy Consumption (mJ)";
		String chartTitle = "感知数据消息大小对能量消耗的影响";
		String adjustedVariable = "answerMessageSize";
		String filePath = "CSA\\energy\\English";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
