package NHSensor.NHSensorSim.papers.GKNNEx.energy;

import NHSensor.NHSensorSim.chart.ChartFrame;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPairCollections;
import NHSensor.NHSensorSim.papers.GKNNEx.GKNNExExperimentUtil;

public class SenseDataSizeEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ExperimentParamResultPairCollections resultCollections = GKNNExExperimentUtil
				.getDefaultUtil().energy("answerMessageSize");

		// chart code
		System.out.println(resultCollections.toString());
		String xFieldName = "answerMessageSize";
		String yFieldName = "consumedEnergy";
		System.out
				.println(resultCollections.toXYString(xFieldName, yFieldName));

		String frameTitle = "GKNNEx VS IKNNEx";
		String xAxisLabel = "��֪������Ϣ��С";
		String yAxisLabel = "���ĵ�����";
		String chartTitle = "��֪������Ϣ��С��������Ӱ��";
		ChartFrame.showParamResultPairCollections(resultCollections,
				xFieldName, yFieldName, frameTitle, xAxisLabel, yAxisLabel,
				chartTitle);

	}

}
