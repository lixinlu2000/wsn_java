package NHSensor.NHSensorSim.papers.RSA.successProbability;

import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeFailProbabilitySuccessProbability {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeFailProbability";
		String yFieldName = "successProbability";
		String frameTitle = "RSA VS IWQE";
		String xAxisLabel = "�ڵ�ʧЧ����";
		String yAxisLabel = "��ѯ�ɹ��ĸ���";
		String chartTitle = "�ڵ�ʧЧ���ʶԲ�ѯ�ɹ��ĸ��ʵ�Ӱ��";
		String adjustedVariable = "nodeFailProbability";
		String filePath = "RSA\\successProbability";
		String absoluteFilePath = Util.generateFilePath(filePath);
		RSAUtil.getDefaultRSAUtil().showAndLogRobustness(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
