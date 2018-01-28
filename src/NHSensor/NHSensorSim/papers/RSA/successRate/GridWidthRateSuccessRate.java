package NHSensor.NHSensorSim.papers.RSA.successRate;

import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class GridWidthRateSuccessRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "gridWidthRate";
		String yFieldName = "isSuccess";
		String frameTitle = "RSA VS IWQE";
		String xAxisLabel = "������ռͨ�Ű뾶�ı���";
		String yAxisLabel = "��ѯ��·��ͨ��";
		String chartTitle = "������ռͨ�Ű뾶�ı����Բ�ѯ��·��ͨ�Ե�Ӱ��";
		String adjustedVariable = "gridWidthRate";
		String filePath = "RSA\\successRate";
		String absoluteFilePath = Util.generateFilePath(filePath);
		RSAUtil.getDefaultRSAUtil().showAndLogRobustness(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}
