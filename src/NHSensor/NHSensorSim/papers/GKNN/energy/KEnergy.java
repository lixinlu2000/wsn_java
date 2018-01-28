package NHSensor.NHSensorSim.papers.GKNN.energy;

import NHSensor.NHSensorSim.chart.ChartFrame;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPairCollections;
import NHSensor.NHSensorSim.papers.GKNN.GKNNExperimentUtil;

public class KEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ExperimentParamResultPairCollections resultCollections = GKNNExperimentUtil
				.getDefaultUtil().energy("k");

		// chart code
		System.out.println(resultCollections.toString());
		String xFieldName = "k";
		String yFieldName = "consumedEnergy";
		System.out
				.println(resultCollections.toXYString(xFieldName, yFieldName));

		String frameTitle = "GKNN VS IKNN";
		String xAxisLabel = "k��Ŀ";
		String yAxisLabel = "���ĵ�����";
		String chartTitle = "k��Ŀ��������Ӱ��";
		ChartFrame.showParamResultPairCollections(resultCollections,
				xFieldName, yFieldName, frameTitle, xAxisLabel, yAxisLabel,
				chartTitle);

	}

}
