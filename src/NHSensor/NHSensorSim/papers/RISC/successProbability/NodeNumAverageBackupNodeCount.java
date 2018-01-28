package NHSensor.NHSensorSim.papers.RISC.successProbability;

import NHSensor.NHSensorSim.papers.RISC.GridTraverseRingExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumAverageBackupNodeCount {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "averageBackupNodeCount";
		String frameTitle = "GKNN VS IKNN nodeNum -> averageBackupNodeCount";
		String xAxisLabel = "����ڵ���Ŀ������";
		String yAxisLabel = "ƽ�����ݽڵ���Ŀ������";
		String chartTitle = "�ڵ���Ŀ��ƽ�����ݽڵ���Ŀ��Ӱ��";
		String adjustedVariable = "nodeNum";
		String filePath = "GridTraverseRing\\successProbability";
		String absoluteFilePath = Util.generateFilePath(filePath);
		GridTraverseRingExperimentUtil.getDefaultUtil().showAndLogRobustness(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
