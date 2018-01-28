package NHSensor.NHSensorSim.papers.RSA.energy;

import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class GridWidthRateAdjustIWQEEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "gridWidthRate";
		String yFieldName = "consumedEnergy";
		String frameTitle = "RSA VS IWQE";
		String xAxisLabel = "�������ռͨ�Ű뾶�ı���";
		String yAxisLabel = "���ĵ�������mJ��";
		String chartTitle = "�������ռͨ�Ű뾶�ı��ʶ�������Ӱ��";
		String adjustedVariable = "gridWidthRate";
		String filePath = "RSA\\energyAdjustIWQEWidth";
		String absoluteFilePath = Util.generateFilePath(filePath);
		RSAUtil.getDefaultRSAUtil().showAndLogEnergyAdjustIWQEWidth(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}