package NHSensor.NHSensorSim.papers.RISC.successProbability;

import NHSensor.NHSensorSim.papers.RISC.GridTraverseRingExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class RingWidthSuccessProbability {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "ringWidth";
		String yFieldName = "successProbability";
		String frameTitle = "GKNN VS IKNN ringWidth -> successProbability";
		String xAxisLabel = "����ȣ��ף�";
		String yAxisLabel = "��ѯ�ɹ�����";
		String chartTitle = "����ȶԲ�ѯ�ɹ��ʵ�Ӱ��";
		String adjustedVariable = "ringWidth";
		String filePath = "GridTraverseRing\\successProbability";
		String absoluteFilePath = Util.generateFilePath(filePath);
		GridTraverseRingExperimentUtil.getDefaultUtil().showAndLogRobustness(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
