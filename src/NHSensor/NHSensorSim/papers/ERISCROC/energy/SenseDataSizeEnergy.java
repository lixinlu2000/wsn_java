package NHSensor.NHSensorSim.papers.ERISCROC.energy;

import NHSensor.NHSensorSim.papers.ERISCROC.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class SenseDataSizeEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "answerMessageSize";
		String yFieldName = "consumedEnergy";
		String frameTitle = "GKNN VS IKNN";
		String xAxisLabel = "感知数据消息大小（字节）";
		String yAxisLabel = "消耗的能量（mJ）";
		String chartTitle = "感知数据消息大小对能量的影响";
		String adjustedVariable = "answerMessageSize";
		String filePath = "ERISCROC\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);

	}

}
