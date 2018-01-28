package NHSensor.NHSensorSim.papers.CSA.bytes.English;

import NHSensor.NHSensorSim.papers.CSA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumQueryNodeNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "queryNodeNum";
		String frameTitle = "ESA-GBA VS CSA-CDA";
		String xAxisLabel = "Number of sensors";
		String yAxisLabel = "Number of cluster head nodes";
		String chartTitle = "网络节点数目对查询节点数目的影响";
		String adjustedVariable = "nodeNum";
		String filePath = "CSA\\energy\\English";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
