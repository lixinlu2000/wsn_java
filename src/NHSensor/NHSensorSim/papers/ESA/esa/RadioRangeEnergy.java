package NHSensor.NHSensorSim.papers.ESA.esa;

import NHSensor.NHSensorSim.papers.ESA.ESAUtil;
import NHSensor.NHSensorSim.papers.LSA.LSAUtil;
import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class RadioRangeEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "radioRange";
		String yFieldName = "consumedEnergy";
		String frameTitle = "ESAGBA VS ESAGAA";
		String xAxisLabel = "ͨ�Ű뾶";
		String yAxisLabel = "���ĵ�������mJ��";
		String chartTitle = "ͨ�Ű뾶��������Ӱ��";
		String adjustedVariable = "radioRange";
		String filePath = "ESA\\esa";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ESAUtil.getDefaultESAUtil().showAndLogTwoESAEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
