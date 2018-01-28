package NHSensor.NHSensorSim.papers.LSA.packetFrameNum;

import NHSensor.NHSensorSim.papers.LSA.LSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeIDPacketFrameNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "networkID";
		String yFieldName = "packetFrameNum";
		String frameTitle = "LSA VS IWQE";
		String xAxisLabel = "网络ID";
		String yAxisLabel = "发送的数据包数目（个）";
		String chartTitle = "网络ID对发送的数据帧数目的影响";
		String adjustedVariable = "networkID";
		String filePath = "LSA\\packetFrameNum";
		String absoluteFilePath = Util.generateFilePath(filePath);
		LSAUtil.getDefaultLSAUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}
