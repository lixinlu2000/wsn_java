package NHSensor.NHSensorSim.papers.LSA.queryResultCorrectRate;

import NHSensor.NHSensorSim.papers.LSA.LSAUtil;
import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class RadioRangeQueryResultCorrectRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "radioRange";
		String yFieldName = "queryResultCorrectRate";
		String frameTitle = "LSA VS IWQE";
		String xAxisLabel = "ͨ�Ű뾶";
		String yAxisLabel = "��ѯ�������";
		String chartTitle = "����ڵ���Ŀ�Բ�ѯ���������Ӱ��";
		String adjustedVariable = "radioRange";
		String filePath = "LSA\\packetFrameNum";
		String absoluteFilePath = Util.generateFilePath(filePath);
		LSAUtil.getDefaultLSAUtil().showAndLogQueryCorrectnessRate(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}
}
