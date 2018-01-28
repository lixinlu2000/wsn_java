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
		String xAxisLabel = "网络节点数目（个）";
		String yAxisLabel = "平均备份节点数目（个）";
		String chartTitle = "节点数目对平均备份节点数目的影响";
		String adjustedVariable = "nodeNum";
		String filePath = "GridTraverseRing\\successProbability";
		String absoluteFilePath = Util.generateFilePath(filePath);
		GridTraverseRingExperimentUtil.getDefaultUtil().showAndLogRobustness(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
