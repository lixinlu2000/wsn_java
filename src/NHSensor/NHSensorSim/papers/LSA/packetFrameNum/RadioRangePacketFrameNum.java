package NHSensor.NHSensorSim.papers.LSA.packetFrameNum;

import NHSensor.NHSensorSim.papers.LSA.LSAUtil;
import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class RadioRangePacketFrameNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "radioRange";
		String yFieldName = "packetFrameNum";
		String frameTitle = "LSA VS IWQE";
		String xAxisLabel = "ͨ�Ű뾶";
		String yAxisLabel = "���͵����ݰ���Ŀ������";
		String chartTitle = "����ڵ���Ŀ�Է��͵�����֡��Ŀ��Ӱ��";
		String adjustedVariable = "radioRange";
		String filePath = "LSA\\packetFrameNum";
		String absoluteFilePath = Util.generateFilePath(filePath);
		LSAUtil.getDefaultLSAUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}
