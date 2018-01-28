package NHSensor.NHSensorSim.papers.CRISC.sucessProbability;

import NHSensor.NHSensorSim.papers.CRISC.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class FailNodeNumSuccessRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "failNodeNum";
		String yFieldName = "isSuccess";
		String frameTitle = "CRISC VS ERISC VS IC";
		String xAxisLabel = "ʧЧ�ڵ���Ŀ������";
		String yAxisLabel = "��ѯ�ɹ���";
		String chartTitle = "ʧЧ�ڵ���Ŀ�Բ�ѯ�ɹ��ʵ�Ӱ��";
		String adjustedVariable = "failNodeNum";
		String filePath = "CRISC\\isSuccess";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogSuccessRateHasFailNode(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}
}
