package NHSensor.NHSensorSim.papers.RSA.successRate;

import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class QueryRegionRateSuccessRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryRegionRate";
		String yFieldName = "isSuccess";
		String frameTitle = "RSA VS IWQE";
		String xAxisLabel = "�ڵ���Ŀ";
		String yAxisLabel = "��ѯ�������ռ��������ı���";
		String chartTitle = "��ѯ��������Բ�ѯ��·��ͨ�Ե�Ӱ��";
		String adjustedVariable = "queryRegionRate";
		String filePath = "RSA\\successRate";
		String absoluteFilePath = Util.generateFilePath(filePath);
		RSAUtil.getDefaultRSAUtil().showAndLogRobustness(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}
