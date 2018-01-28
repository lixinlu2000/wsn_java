package NHSensor.NHSensorSim.papers.HSA.energyNoApproximation;

import NHSensor.NHSensorSim.papers.HSA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class SenseDataSizeEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "answerMessageSize";
		String yFieldName = "consumedEnergy";

		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] { true, true, false, false, true});
		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		String xAxisLabel = "感知数据消息大小（字节）";
		String yAxisLabel = "消耗的能量（mJ）";
		String chartTitle = "感知数据消息大小对能量消耗的影响";
		String adjustedVariable = "answerMessageSize";
		String filePath = "HSA\\energyNoApproximation";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
