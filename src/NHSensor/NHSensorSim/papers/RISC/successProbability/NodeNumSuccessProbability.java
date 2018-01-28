package NHSensor.NHSensorSim.papers.RISC.successProbability;

import NHSensor.NHSensorSim.papers.RISC.GridTraverseRingExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumSuccessProbability {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "successProbability";
		String frameTitle = "GKNN VS IKNN nodeNum -> successProbability";
		String xAxisLabel = "����ڵ���Ŀ������";
		String yAxisLabel = "��ѯ�ɹ�����";
		String chartTitle = "�ڵ���Ŀ�Բ�ѯ�ɹ��ʵ�Ӱ��";
		String adjustedVariable = "nodeNum";
		String filePath = "GridTraverseRing\\successProbability";
		String absoluteFilePath = Util.generateFilePath(filePath);
		GridTraverseRingExperimentUtil.getDefaultUtil().showAndLogRobustness(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
