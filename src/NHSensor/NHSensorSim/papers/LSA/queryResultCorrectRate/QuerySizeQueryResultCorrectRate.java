package NHSensor.NHSensorSim.papers.LSA.queryResultCorrectRate;

import NHSensor.NHSensorSim.papers.LSA.LSAUtil;
import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class QuerySizeQueryResultCorrectRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryMessageSize";
		String yFieldName = "queryResultCorrectRate";
		String frameTitle = "LSA VS IWQE";
		String xAxisLabel = "��ѯ��Ϣ��С���ֽڣ�";
		String yAxisLabel = "��ѯ�������";
		String chartTitle = "��ѯ��Ϣ��С�Բ�ѯ���������Ӱ��";
		String adjustedVariable = "queryMessageSize";
		String filePath = "LSA\\queryResultCorrectRate";
		String absoluteFilePath = Util.generateFilePath(filePath);
		LSAUtil.getDefaultLSAUtil().showAndLogQueryCorrectnessRate(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
