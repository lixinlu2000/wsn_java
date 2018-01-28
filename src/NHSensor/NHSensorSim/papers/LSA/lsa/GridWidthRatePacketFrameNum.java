package NHSensor.NHSensorSim.papers.LSA.lsa;

import NHSensor.NHSensorSim.papers.LSA.LSAUtil;
import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class GridWidthRatePacketFrameNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "gridWidthRate";
		String yFieldName = "packetFrameNum";
		String frameTitle = "LSA";
		String xAxisLabel = "������";
		String yAxisLabel = "���͵����ݰ���Ŀ������";
		String chartTitle = "�����ȶԷ��͵�����֡��Ŀ��Ӱ��";
		String adjustedVariable = "gridWidthRate";
		String filePath = "LSA\\lsa";
		String absoluteFilePath = Util.generateFilePath(filePath);
		LSAUtil.getDefaultLSAUtil().showAndLogLSAEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
