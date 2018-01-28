package NHSensor.NHSensorSim.papers.LSA.queryResultCorrectRate;

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
		String frameTitle = "LSA VS IWQE";
		String xAxisLabel = "������ռͨ�Ű뾶�ı���";
		String yAxisLabel = "��ѯ�������";
		String chartTitle = "������ռͨ�Ű뾶�ı��ʶԲ�ѯ���������Ӱ��";
		String adjustedVariable = "gridWidthRate";
		String filePath = "LSA\\queryResultCorrectRate";
		String absoluteFilePath = Util.generateFilePath(filePath);
		LSAUtil.getDefaultLSAUtil().showAndLogQueryCorrectnessRate(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
