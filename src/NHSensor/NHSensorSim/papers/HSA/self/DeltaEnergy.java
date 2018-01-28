package NHSensor.NHSensorSim.papers.HSA.self;

import NHSensor.NHSensorSim.papers.HSA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class DeltaEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "delta";
		String yFieldName = "consumedEnergy";

		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] { false, false, false, false, true});
		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		String xAxisLabel = "�û���������Ͻ�";
		String yAxisLabel = "���ĵ�������mJ��";
		String chartTitle = "�û���������Ͻ��������Ӱ��";
		String adjustedVariable = "delta";
		String filePath = "HSA\\self";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}
