package NHSensor.NHSensorSim.papers.LSA.packetFrameNum;

import NHSensor.NHSensorSim.papers.LSA.LSAUtil;
import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class SenseDataSizePacketFrameNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "answerMessageSize";
		String yFieldName = "packetFrameNum";
		String frameTitle = "LSA VS IWQE";
		String xAxisLabel = "��֪���ݴ�С���ֽڣ�";
		String yAxisLabel = "���͵����ݰ���Ŀ������";
		String chartTitle = "��֪���ݴ�С�Է��͵�����֡��Ŀ��Ӱ��";
		String adjustedVariable = "answerMessageSize";
		String filePath = "LSA\\packetFrameNum";
		String absoluteFilePath = Util.generateFilePath(filePath);
		LSAUtil.getDefaultLSAUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
