package NHSensor.NHSensorSim.papers.CSA.bytesNonuniform.English;

import NHSensor.NHSensorSim.papers.CSA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class HoleNumberEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "holeNumber";
		String yFieldName = "packetFrameNum";
		String frameTitle = "ESA-GBA VS CSA-CDA";
		String xAxisLabel = "Number of holes";
		String yAxisLabel = "Comm. cost (Number of bytes)";
		String chartTitle = "网络节点数目对能量的影响";
		String adjustedVariable = "holeNumber";
		String filePath = "CSA\\bytesNonuniform\\English";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}
