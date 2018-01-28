package NHSensor.NHSensorSim.papers.LSA.packetFrameNumNeed;

import NHSensor.NHSensorSim.papers.LSA.LSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeIDPacketFrameNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "networkID";
		String yFieldName = "packetFrameNumNeed";
		String frameTitle = "LSA VS IWQE";
		String xAxisLabel = "����ID";
		String yAxisLabel = "���͵����ݰ���Ŀ������";
		String chartTitle = "����ID�Է������ݰ���Ŀ��Ӱ��";
		String adjustedVariable = "networkID";
		String filePath = "LSA\\packetFrameNumNeed";
		String absoluteFilePath = Util.generateFilePath(filePath);
		LSAUtil.getDefaultLSAUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}
