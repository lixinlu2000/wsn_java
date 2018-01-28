package NHSensor.NHSensorSim.papers.RSA.averageEnergy;

import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class SenseDataSizeEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "answerMessageSize";
		String yFieldName = "consumedEnergy";
		String frameTitle = "RSA VS IWQE";
		String xAxisLabel = "感知数据消息大小（字节）";
		String yAxisLabel = "消耗的能量（mJ）";
		String chartTitle = "感知数据消息大小对能量消耗的影响";
		String adjustedVariable = "answerMessageSize";
		String filePath = "RSA\\averageEnergy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		RSAUtil.getDefaultRSAUtil().showAndLogAverageEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
