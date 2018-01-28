package NHSensor.NHSensorSim.papers.E2STA.energy.English;

import NHSensor.NHSensorSim.papers.E2STA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class MiddleSitaRateEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeFailProbability";
		String yFieldName = "consumedEnergy";
		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] { false, false, true, false, false,  false, false, true, false, false });
		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		String xAxisLabel = "�м�Ƕȴ�С";
		String yAxisLabel = "���ĵ�������mJ��";
		String chartTitle = "���м�Ƕȴ�С��������Ӱ��";
		String adjustedVariable = "nodeFailProbability";
		String filePath = "E2STA\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
