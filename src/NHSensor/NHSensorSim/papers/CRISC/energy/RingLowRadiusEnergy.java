package NHSensor.NHSensorSim.papers.CRISC.energy;

import NHSensor.NHSensorSim.papers.CRISC.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class RingLowRadiusEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "ringLowRadius";
		String yFieldName = "consumedEnergy";
		String frameTitle = "CRISC VS ERISC VS IC";
		String xAxisLabel = "��СԲ�뾶���ף�";
		String yAxisLabel = "���ĵ�������mJ��";
		String chartTitle = "��СԲ�뾶��������Ӱ��";
		String adjustedVariable = "ringLowRadius";
		String filePath = "CRISC\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
