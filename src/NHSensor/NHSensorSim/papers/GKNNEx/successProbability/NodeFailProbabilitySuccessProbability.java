package NHSensor.NHSensorSim.papers.GKNNEx.successProbability;

import NHSensor.NHSensorSim.chart.ChartFrame;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPairCollections;
import NHSensor.NHSensorSim.papers.GKNNEx.GKNNExExperimentUtil;

public class NodeFailProbabilitySuccessProbability {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExperimentParamResultPairCollections resultCollections = GKNNExExperimentUtil
				.getDefaultUtil().robustness("nodeFailProbability");

		// chart code
		System.out.println(resultCollections.toString());
		String xFieldName = "nodeFailProbability";
		String yFieldName = "successProbability";
		System.out
				.println(resultCollections.toXYString(xFieldName, yFieldName));

		String frameTitle = "GKNN VS IKNN nodeFailProbability -> successProbability";
		String xAxisLabel = "�ڵ�ʧЧ����";
		String yAxisLabel = "�ɹ���";
		String chartTitle = "�ڵ�ʧЧ���ʶԲ�ѯ�ɹ��ʵ�Ӱ��";
		ChartFrame.showParamResultPairCollections(resultCollections,
				xFieldName, yFieldName, frameTitle, xAxisLabel, yAxisLabel,
				chartTitle);
	}

}
