package NHSensor.NHSensorSim.papers.GKNN.successProbability;

import NHSensor.NHSensorSim.chart.ChartFrame;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPairCollections;
import NHSensor.NHSensorSim.papers.GKNN.GKNNExperimentUtil;

public class KSuccessProbability {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExperimentParamResultPairCollections resultCollections = GKNNExperimentUtil
				.getDefaultUtil().robustness("k");

		// chart code
		System.out.println(resultCollections.toString());
		String xFieldName = "k";
		String yFieldName = "successProbability";
		System.out
				.println(resultCollections.toXYString(xFieldName, yFieldName));

		String frameTitle = "GKNN VS IKNN k -> successProbability";
		String xAxisLabel = "k��Ŀ";
		String yAxisLabel = "�ɹ���";
		String chartTitle = "k��Ŀ�Բ�ѯ�ɹ��ʵ�Ӱ��";
		ChartFrame.showParamResultPairCollections(resultCollections,
				xFieldName, yFieldName, frameTitle, xAxisLabel, yAxisLabel,
				chartTitle);
	}

}
