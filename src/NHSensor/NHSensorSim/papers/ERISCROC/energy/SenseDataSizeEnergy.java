package NHSensor.NHSensorSim.papers.ERISCROC.energy;

import NHSensor.NHSensorSim.papers.ERISCROC.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class SenseDataSizeEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "answerMessageSize";
		String yFieldName = "consumedEnergy";
		String frameTitle = "GKNN VS IKNN";
		String xAxisLabel = "��֪������Ϣ��С���ֽڣ�";
		String yAxisLabel = "���ĵ�������mJ��";
		String chartTitle = "��֪������Ϣ��С��������Ӱ��";
		String adjustedVariable = "answerMessageSize";
		String filePath = "ERISCROC\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);

	}

}