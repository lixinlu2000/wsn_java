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
		String xAxisLabel = "�ڵ���Ŀ";
		String yAxisLabel = "�ɹ���";
		String chartTitle = "�ڵ���Ŀ�Բ�ѯ�ɹ��ʵ�Ӱ��";
		ChartFrame.showParamResultPairCollections(resultCollections,
				xFieldName, yFieldName, frameTitle, xAxisLabel, yAxisLabel,
				chartTitle);
	}

}
