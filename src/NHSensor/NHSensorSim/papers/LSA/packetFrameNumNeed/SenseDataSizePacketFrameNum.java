package NHSensor.NHSensorSim.papers.LSA.packetFrameNumNeed;

import NHSensor.NHSensorSim.papers.LSA.LSAUtil;
import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class SenseDataSizePacketFrameNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "answerMessageSize";
		String yFieldName = "packetFrameNumNeed";
		String frameTitle = "LSA VS IWQE";
		String xAxisLabel = "感知数据消息大小（字节）";
		String yAxisLabel = "发送的数据包数目（个）";
		String chartTitle = "感知数据消息大小对发送数据包数目的影响";
		String adjustedVariable = "answerMessageSize";
		String filePath = "LSA\\packetFrameNumNeed";
		String absoluteFilePath = Util.generateFilePath(filePath);
		LSAUtil.getDefaultLSAUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
