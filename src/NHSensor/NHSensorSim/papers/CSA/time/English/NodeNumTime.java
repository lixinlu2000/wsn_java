package NHSensor.NHSensorSim.papers.CSA.time.English;

import NHSensor.NHSensorSim.papers.CSA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumTime {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "communicationTime";
		String frameTitle = "ESA-GBA VS CSA-CDA";
		String xAxisLabel = "Number of sensors";
		String yAxisLabel = "Time latency (s)";
		String chartTitle = "网络节点数目对能量的影响";
		String adjustedVariable = "nodeNum";
		String filePath = "CSA\\time\\English";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}
