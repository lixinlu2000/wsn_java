package NHSensor.NHSensorSim.papers.CRISC.other;

import NHSensor.NHSensorSim.papers.CRISC.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumBackupNodeNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "backupNodeNum";
		String frameTitle = "CRISC VS ERISC VS IC";
		String xAxisLabel = "网络节点数目（个）";
		String yAxisLabel = "查询节点或簇头节点数目";
		String chartTitle = "备份节点数目";
		String adjustedVariable = "nodeNum";
		String filePath = "CRISC\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}
