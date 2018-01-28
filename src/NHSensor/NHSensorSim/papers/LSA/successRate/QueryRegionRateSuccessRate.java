package NHSensor.NHSensorSim.papers.LSA.successRate;

import NHSensor.NHSensorSim.papers.LSA.LSAUtil;
import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class QueryRegionRateSuccessRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryRegionRate";
		String yFieldName = "isSuccess";
		String frameTitle = "LSA VS IWQE";
		String xAxisLabel = "��ѯ�������ռ���縲������ı���";
		String yAxisLabel = "��ѯ�ɹ���";
		String chartTitle = "��ѯ�������ռ���縲������ı����Բ�ѯ�����С�Բ�ѯ�ɹ��ʵ�Ӱ��";
		String adjustedVariable = "queryRegionRate";
		String filePath = "LSA\\isSuccess";
		String absoluteFilePath = Util.generateFilePath(filePath);
		LSAUtil.getDefaultLSAUtil().showAndLogSuccessRate(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
