package NHSensor.NHSensorSim.papers.ESA.successProbability;

import NHSensor.NHSensorSim.papers.ESA.ESAUtil;
import NHSensor.NHSensorSim.papers.LSA.LSAUtil;
import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class FailNodeNumSuccessRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "failNodeNum";
		String yFieldName = "isSuccess";
		String frameTitle = "ESA VS IWQE";
		String xAxisLabel = "ʧЧ�ڵ���Ŀ������";
		String yAxisLabel = "��ѯ�ɹ���";
		String chartTitle = "ʧЧ�ڵ���Ŀ�Բ�ѯ�ɹ��ʵ�Ӱ��";
		String adjustedVariable = "failNodeNum";
		String filePath = "ESA\\isSuccess";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ESAUtil.getDefaultESAUtil().showAndLogSuccessRateHasFailNode(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}
}
