package NHSensor.NHSensorSim.papers.LSA.iwqe;

import NHSensor.NHSensorSim.papers.LSA.LSAUtil;
import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumPacketFrameNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "packetFrameNum";
		String frameTitle = "WQE";
		String xAxisLabel = "网络节点数目（个）";
		String yAxisLabel = "发送的数据包数目（个）";
		String chartTitle = "网络节点数目对发送数据包数目的影响";
		String adjustedVariable = "nodeNum";
		String filePath = "LSA\\iwqe";
		String absoluteFilePath = Util.generateFilePath(filePath);
		LSAUtil.getDefaultLSAUtil().showAndLogIWQEEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}
