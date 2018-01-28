package NHSensor.NHSensorSim.papers.RSA.successProbability;

import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class QueryRegionRateSuccessProbability {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryRegionRate";
		String yFieldName = "successProbability";
		String frameTitle = "RSA VS IWQE";
		String xAxisLabel = "��ѯ�������ռ���縲������ı���";
		String yAxisLabel = "��ѯ�ɹ��ĸ���";
		String chartTitle = "��ѯ�������ռ���縲������ı����Բ�ѯ�ɹ��ĸ��ʵ�Ӱ��";
		String adjustedVariable = "queryRegionRate";
		String filePath = "RSA\\successProbability";
		String absoluteFilePath = Util.generateFilePath(filePath);
		RSAUtil.getDefaultRSAUtil().showAndLogRobustness(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
