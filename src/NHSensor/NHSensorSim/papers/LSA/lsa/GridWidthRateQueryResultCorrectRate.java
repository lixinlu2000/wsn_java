package NHSensor.NHSensorSim.papers.LSA.lsa;

import NHSensor.NHSensorSim.papers.LSA.LSAUtil;
import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class GridWidthRateQueryResultCorrectRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "gridWidthRate";
		String yFieldName = "queryResultCorrectRate";
		String frameTitle = "LSA";
		String xAxisLabel = "������";
		String yAxisLabel = "��ѯ�������";
		String chartTitle = "�����ȶԲ�ѯ���������Ӱ��";
		String adjustedVariable = "gridWidthRate";
		String filePath = "LSA\\lsa";
		String absoluteFilePath = Util.generateFilePath(filePath);
		LSAUtil.getDefaultLSAUtil().showAndLogLSAQueryCorrectnessRate(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
