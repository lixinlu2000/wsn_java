package NHSensor.NHSensorSim.papers.LKNN.packetFrameNumUniform;

import NHSensor.NHSensorSim.papers.LKNN.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeIDPacketFrameNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "networkID";
		String yFieldName = "packetFrameNum";
		String frameTitle = "LAC VS IC";
		String xAxisLabel = "Network ID";
		String yAxisLabel = "The total number of data frames sent";
		String chartTitle = "网络ID对发送数据包数目的影响";
		String adjustedVariable = "networkID";
		String filePath = "LKNN\\English\\packetFrameNumUniform";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}
