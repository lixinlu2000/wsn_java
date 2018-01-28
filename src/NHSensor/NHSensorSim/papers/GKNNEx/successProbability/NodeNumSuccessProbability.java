package NHSensor.NHSensorSim.papers.GKNNEx.successProbability;

import NHSensor.NHSensorSim.chart.ChartFrame;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPairCollections;
import NHSensor.NHSensorSim.papers.GKNNEx.GKNNExExperimentUtil;

public class NodeNumSuccessProbability {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExperimentParamResultPairCollections resultCollections = GKNNExExperimentUtil
				.getDefaultUtil().robustness("nodeNum");

		// chart code
		System.out.println(resultCollections.toString());
		String xFieldName = "nodeNum";
		String yFieldName = "successProbability";
		System.out
				.println(resultCollections.toXYString(xFieldName, yFieldName));

		String frameTitle = "GKNN VS IKNN nodeNum -> successProbability";
		String xAxisLabel = "节点数目";
		String yAxisLabel = "成功率";
		String chartTitle = "节点数目对查询成功率的影响";
		ChartFrame.showParamResultPairCollections(resultCollections,
				xFieldName, yFieldName, frameTitle, xAxisLabel, yAxisLabel,
				chartTitle);
	}

}
