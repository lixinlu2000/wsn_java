package NHSensor.NHSensorSim.papers.LRISC.energy;

import NHSensor.NHSensorSim.papers.LRISC.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NullEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "consumedEnergy";
		String frameTitle = "RKNN VS IKNN";
		String xAxisLabel = "����ڵ���Ŀ������";
		String yAxisLabel = "���ĵ�������mJ��";
		String chartTitle = "�ڵ���Ŀ��������Ӱ��";
		String adjustedVariable = "null";
		String filePath = "LRISC\\null\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}