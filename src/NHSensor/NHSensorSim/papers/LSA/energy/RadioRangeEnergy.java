package NHSensor.NHSensorSim.papers.LSA.energy;

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
		String frameTitle = "LSA VS IWQE";
		String xAxisLabel = "ͨ�Ű뾶";
		String yAxisLabel = "���ĵ�������mJ��";
		String chartTitle = "ͨ�Ű뾶��������Ӱ��";
		String adjustedVariable = "radioRange";
		String filePath = "LSA\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		LSAUtil.getDefaultLSAUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
