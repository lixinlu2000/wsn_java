package NHSensor.NHSensorSim.papers.ESA.esa;

import NHSensor.NHSensorSim.papers.ESA.ESAUtil;
import NHSensor.NHSensorSim.papers.LSA.LSAUtil;
import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class QueryRegionRateEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryRegionRate";
		String yFieldName = "consumedEnergy";
		String frameTitle = "ESAGBA VS ESAGAA";
		String xAxisLabel = "��ѯ�������ռ���縲������ı���";
		String yAxisLabel = "���ĵ�������mJ��";
		String chartTitle = "��ѯ�������ռ���縲������ı����Բ�ѯ�����С��������Ӱ��";
		String adjustedVariable = "queryRegionRate";
		String filePath = "ESA\\esa";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ESAUtil.getDefaultESAUtil().showAndLogTwoESAEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
