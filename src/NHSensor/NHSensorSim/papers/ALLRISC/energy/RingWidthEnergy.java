package NHSensor.NHSensorSim.papers.ALLRISC.energy;

import NHSensor.NHSensorSim.papers.ALLRISC.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class RingWidthEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "ringWidth";
		String yFieldName = "consumedEnergy";
		String frameTitle = "GKNN VS IKNN";
		String xAxisLabel = "����ȣ��ף�";
		String yAxisLabel = "���ĵ�������mJ��";
		String chartTitle = "����ȶ�������Ӱ��";
		String adjustedVariable = "ringWidth";
		String filePath = "ETDC\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
