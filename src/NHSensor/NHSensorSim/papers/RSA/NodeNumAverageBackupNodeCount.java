package NHSensor.NHSensorSim.papers.RSA;

import NHSensor.NHSensorSim.chart.ChartFrame;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPairCollections;

public class NodeNumAverageBackupNodeCount {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExperimentParamResultPairCollections resultCollections = RSAUtil
				.getDefaultRSAUtil().robustness("nodeNum");

		// chart code
		System.out.println(resultCollections.toString());
		String xFieldName = "nodeNum";
		String yFieldName = "successProbability";
		System.out
				.println(resultCollections.toXYString(xFieldName, yFieldName));

		String frameTitle = "DGSANew nodeNum -> gridHeight";
		String xAxisLabel = "节点数目";
		String yAxisLabel = "平均备份节点数目";
		String chartTitle = "节点数目对查询成功率的影响";
		ChartFrame.showParamResultPairCollections(resultCollections,
				xFieldName, yFieldName, frameTitle, xAxisLabel, yAxisLabel,
				chartTitle);
	}
}
