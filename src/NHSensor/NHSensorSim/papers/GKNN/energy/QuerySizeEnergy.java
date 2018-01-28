package NHSensor.NHSensorSim.papers.GKNN.energy;

import NHSensor.NHSensorSim.chart.ChartFrame;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPairCollections;
import NHSensor.NHSensorSim.papers.GKNN.GKNNExperimentUtil;

public class QuerySizeEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ExperimentParamResultPairCollections resultCollections = GKNNExperimentUtil
				.getDefaultUtil().energy("queryMessageSize");

		// chart code
		System.out.println(resultCollections.toString());
		String xFieldName = "queryMessageSize";
		String yFieldName = "consumedEnergy";
		System.out
				.println(resultCollections.toXYString(xFieldName, yFieldName));

		String frameTitle = "GKNN VS IKNN";
		String xAxisLabel = "查询消息大小";
		String yAxisLabel = "消耗的能量";
		String chartTitle = "查询消息大小对能量的影响";
		ChartFrame.showParamResultPairCollections(resultCollections,
				xFieldName, yFieldName, frameTitle, xAxisLabel, yAxisLabel,
				chartTitle);

	}

}
