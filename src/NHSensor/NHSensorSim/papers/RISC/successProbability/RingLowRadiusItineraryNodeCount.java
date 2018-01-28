package NHSensor.NHSensorSim.papers.RISC.successProbability;

import NHSensor.NHSensorSim.papers.RISC.GridTraverseRingExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class RingLowRadiusItineraryNodeCount {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "ringLowRadius";
		String yFieldName = "itineraryNodeCount";
		String frameTitle = "GKNN VS IKNN ringLowRadius -> itineraryNodeCount";
		String xAxisLabel = "环小圆半径（米）";
		String yAxisLabel = "查询节点数目（个）";
		String chartTitle = "环小圆半径对查询节点数目的影响";
		String adjustedVariable = "ringLowRadius";
		String filePath = "GridTraverseRing\\successProbability";
		String absoluteFilePath = Util.generateFilePath(filePath);
		GridTraverseRingExperimentUtil.getDefaultUtil().showAndLogRobustness(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
