package NHSensor.NHSensorSim.papers.RISC.successProbability;

import NHSensor.NHSensorSim.papers.RISC.GridTraverseRingExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NetworkIDSuccessProbability {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "networkID";
		String yFieldName = "successProbability";
		String frameTitle = "GKNN VS IKNN networkID -> successProbability";
		String xAxisLabel = "����ID";
		String yAxisLabel = "��ѯ�ɹ�����";
		String chartTitle = "����ID�Բ�ѯ�ɹ��ʵ�Ӱ��";
		String adjustedVariable = "networkID";
		String filePath = "GridTraverseRing\\successProbability";
		String absoluteFilePath = Util.generateFilePath(filePath);
		GridTraverseRingExperimentUtil.getDefaultUtil().showAndLogRobustness(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
