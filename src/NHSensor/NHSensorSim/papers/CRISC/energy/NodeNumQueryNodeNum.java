package NHSensor.NHSensorSim.papers.CRISC.energy;

import NHSensor.NHSensorSim.papers.CRISC.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumQueryNodeNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "queryNodeNum";
		String frameTitle = "CRISC VS ERISC VS IC";
		String xAxisLabel = "网络节点数目（个）";
		String yAxisLabel = "查询节点或簇头节点数目";
		String chartTitle = "节点数目对查询节点或簇头节点的影响";
		String adjustedVariable = "nodeNum";
		String filePath = "CRISC\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}
