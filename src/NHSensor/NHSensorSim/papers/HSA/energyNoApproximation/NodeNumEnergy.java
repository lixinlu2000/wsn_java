package NHSensor.NHSensorSim.papers.HSA.energyNoApproximation;

import NHSensor.NHSensorSim.papers.HSA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "consumedEnergy";

		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] { true, true, false, false, true});
		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		String xAxisLabel = "����ڵ���Ŀ������";
		String yAxisLabel = "���ĵ�������mJ��";
		String chartTitle = "����ڵ���Ŀ��������Ӱ��";
		String adjustedVariable = "nodeNum";
		String filePath = "HSA\\energyNoApproximation";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}
