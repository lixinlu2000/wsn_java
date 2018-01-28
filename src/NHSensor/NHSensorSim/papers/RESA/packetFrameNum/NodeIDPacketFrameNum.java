package NHSensor.NHSensorSim.papers.RESA.packetFrameNum;

import NHSensor.NHSensorSim.papers.RESA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeIDPacketFrameNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "networkID";
		String yFieldName = "packetFrameNum";
		String frameTitle = "RESA VS IWQE";
		String xAxisLabel = "网络ID";
		String yAxisLabel = "发送的数据包数目（个）";
		String chartTitle = "网络ID对发送数据包数目的影响";
		String adjustedVariable = "networkID";
		String filePath = "RESA\\packetFrameNum\\Chinese";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}
