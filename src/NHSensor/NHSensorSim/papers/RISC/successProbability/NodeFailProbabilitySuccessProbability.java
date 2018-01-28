package NHSensor.NHSensorSim.papers.RISC.successProbability;

import NHSensor.NHSensorSim.papers.RISC.GridTraverseRingExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeFailProbabilitySuccessProbability {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeFailProbability";
		String yFieldName = "successProbability";
		String frameTitle = "GKNN VS IKNN nodeFailProbability -> successProbability";
		String xAxisLabel = "�ڵ�ʧЧ����";
		String yAxisLabel = "��ѯ�ɹ�����";
		String chartTitle = "�ڵ�ʧЧ���ʶԲ�ѯ�ɹ��ʵ�Ӱ��";
		String adjustedVariable = "nodeFailProbability";
		String filePath = "GridTraverseRing\\successProbability";
		String absoluteFilePath = Util.generateFilePath(filePath);
		GridTraverseRingExperimentUtil.getDefaultUtil().showAndLogRobustness(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
