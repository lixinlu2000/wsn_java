package NHSensor.NHSensorSim.papers.RISC.successProbability;

import NHSensor.NHSensorSim.papers.RISC.GridTraverseRingExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NullSuccessProbability {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "k";
		String yFieldName = "successProbability";
		String frameTitle = "GKNN VS IKNN k -> successProbability";
		String xAxisLabel = "kֵ";
		String yAxisLabel = "��ѯ�ɹ�����";
		String chartTitle = "k��Ŀ�Բ�ѯ�ɹ��ʵ�Ӱ��";
		String adjustedVariable = "null";
		String filePath = "GridTraverseRing\\successProbability";
		String absoluteFilePath = Util.generateFilePath(filePath);
		GridTraverseRingExperimentUtil.getDefaultUtil().showAndLogRobustness(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
