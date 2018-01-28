package NHSensor.NHSensorSim.papers.LSA.successRate;

import NHSensor.NHSensorSim.papers.LSA.LSAUtil;
import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class SenseDataSizeSuccessRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "answerMessageSize";
		String yFieldName = "isSuccess";
		String frameTitle = "LSA VS IWQE";
		String xAxisLabel = "��֪������Ϣ��С���ֽڣ�";
		String yAxisLabel = "��ѯ�ɹ���";
		String chartTitle = "��֪������Ϣ��С�Բ�ѯ�ɹ��ʵ�Ӱ��";
		String adjustedVariable = "answerMessageSize";
		String filePath = "LSA\\isSuccess";
		String absoluteFilePath = Util.generateFilePath(filePath);
		LSAUtil.getDefaultLSAUtil().showAndLogSuccessRate(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
