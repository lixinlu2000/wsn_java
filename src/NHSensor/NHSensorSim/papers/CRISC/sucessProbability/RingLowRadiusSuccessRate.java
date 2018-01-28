package NHSensor.NHSensorSim.papers.CRISC.sucessProbability;

import NHSensor.NHSensorSim.papers.CRISC.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class RingLowRadiusSuccessRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "ringLowRadius";
		String yFieldName = "isSuccess";
		String frameTitle = "CRISC VS ERISC VS IC";
		String xAxisLabel = "��СԲ�뾶���ף�";
		String yAxisLabel = "��ѯ�ɹ���";
		String chartTitle = "��СԲ�뾶�Բ�ѯ�ɹ��ʵ�Ӱ��";
		String adjustedVariable = "ringLowRadius";
		String filePath = "CRISC\\isSuccess";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogSuccessRateHasFailNode(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
