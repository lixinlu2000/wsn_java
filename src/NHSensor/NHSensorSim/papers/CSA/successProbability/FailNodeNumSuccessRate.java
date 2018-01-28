package NHSensor.NHSensorSim.papers.CSA.successProbability;

import NHSensor.NHSensorSim.papers.CSA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class FailNodeNumSuccessRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "failNodeNum";
		String yFieldName = "isSuccess";
		String frameTitle = "ESA-GBA VS CSA-CDA";
		String xAxisLabel = "ʧЧ�ڵ���Ŀ������";
		String yAxisLabel = "��ѯ�ɹ���";
		String chartTitle = "ʧЧ�ڵ���Ŀ�Բ�ѯ�ɹ��ʵ�Ӱ��";
		String adjustedVariable = "failNodeNum";
		String filePath = "CSA\\isSuccess";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogSuccessRateHasFailNode(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}
}
