package NHSensor.NHSensorSim.papers.ERISC.sucessProbability;

import NHSensor.NHSensorSim.papers.ERISC.ExperimentUtil;
import NHSensor.NHSensorSim.papers.ESA.ESAUtil;
import NHSensor.NHSensorSim.papers.LSA.LSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeIDSuccessRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "networkID";
		String yFieldName = "isSuccess";
		String frameTitle = "ESA VS IWQE";
		String xAxisLabel = "����ID";
		String yAxisLabel = "��ѯ�ɹ���";
		String chartTitle = "����ID�Բ�ѯ�ɹ��ʵ�Ӱ��";
		String adjustedVariable = "networkID";
		String filePath = "EDCAdaptiveGridTraverseRingEvent\\isSuccess";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogSuccessRateHasFailNode(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}
}
