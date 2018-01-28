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
		String xAxisLabel = "�ڵ���Ŀ";
		String yAxisLabel = "���ĵ�����";
		String chartTitle = "�ڵ���Ŀ��������Ӱ��";
		ChartFrame.showParamResultPairCollections(resultCollections,
				xFieldName, yFieldName, frameTitle, xAxisLabel, yAxisLabel,
				chartTitle);
	}

}
