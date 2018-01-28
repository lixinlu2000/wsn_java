package NHSensor.NHSensorSim.papers.LKNN.packetFrameNumNonuniform;

import NHSensor.NHSensorSim.papers.LKNN.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class MaxHoleRadiusPacketFrameNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "maxHoleRadius";
		String yFieldName = "packetFrameNum";
		String frameTitle = "LAC VS IC";
		String xAxisLabel = "Maximal radius of holes";
		String yAxisLabel = "The total number of data frames sent";
		String chartTitle = "网络节点数目对发送数据包数目的影响";
		String adjustedVariable = "maxHoleRadius";
		String filePath = "LKNN\\English\\packetFrameNumNonuniform";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}
