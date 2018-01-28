package NHSensor.NHSensorSim.papers.ERISC.sucessProbability;

import NHSensor.NHSensorSim.papers.ERISC.ExperimentUtil;
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
		String filePath = "EDCAdaptiveGridTraverseRingEvent\\isSuccess";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogSuccessRateHasFailNode(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}
}
