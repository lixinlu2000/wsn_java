package NHSensor.NHSensorSim.papers.GKNNEx.energy;

import NHSensor.NHSensorSim.chart.ChartFrame;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPairCollections;
import NHSensor.NHSensorSim.papers.GKNNEx.GKNNExExperimentUtil;

public class NodeNumEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ExperimentParamResultPairCollections resultCollections = GKNNExExperimentUtil
				.getDefaultUtil().energy("nodeNum");

		// chart code
		System.out.println(resultCollections.toString());
		String xFieldName = "nodeNum";
		String yFieldName = "consumedEnergy";
		System.out
				.println(resultCollections.toXYString(xFieldName, yFieldName));

		String frameTitle = "GKNNEx VS IKNNEx";
		String xAxisLabel = "节点数目";
		String yAxisLabel = "消耗的能量";
		String chartTitle = "节点数目对能量的影响";
		ChartFrame.showParamResultPairCollections(resultCollections,
				xFieldName, yFieldName, frameTitle, xAxisLabel, yAxisLabel,
				chartTitle);
	}

}
