package NHSensor.NHSensorSim.papers.RISC.successProbability;

import NHSensor.NHSensorSim.papers.RISC.GridTraverseRingExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class SitaSuccessProbability {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "sita";
		String yFieldName = "successProbability";
		String frameTitle = "GKNN VS IKNN sita -> successProbability";
		String xAxisLabel = "�����μнǣ�����)";
		String yAxisLabel = "��ѯ�ɹ�����";
		String chartTitle = "�����μнǶԲ�ѯ�ɹ��ʵ�Ӱ��";
		String adjustedVariable = "sita";
		String filePath = "GridTraverseRing\\successProbability";
		String absoluteFilePath = Util.generateFilePath(filePath);
		GridTraverseRingExperimentUtil.getDefaultUtil().showAndLogRobustness(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
