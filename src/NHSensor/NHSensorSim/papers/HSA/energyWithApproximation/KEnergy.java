package NHSensor.NHSensorSim.papers.HSA.energyWithApproximation;

import NHSensor.NHSensorSim.papers.HSA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class KEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "subAreaNum";
		String yFieldName = "consumedEnergy";

		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] { true, true, false, false, true});
		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		String xAxisLabel = "��ѯ��������Ŀ";
		String yAxisLabel = "���ĵ�������mJ��";
		String chartTitle = "��ѯ��������Ŀ��������Ӱ��";
		String adjustedVariable = "subAreaNum";
		String filePath = "HSA\\energyWithApproximation";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergyWithFail(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}
}
