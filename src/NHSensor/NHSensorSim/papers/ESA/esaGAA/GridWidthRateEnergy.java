package NHSensor.NHSensorSim.papers.ESA.esaGAA;

import NHSensor.NHSensorSim.papers.ESA.ESAUtil;
import NHSensor.NHSensorSim.util.Util;

public class GridWidthRateEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "gridWidthRate";
		String yFieldName = "consumedEnergy";
		String frameTitle = "ESAGAA VS ESAGBA";
		String xAxisLabel = "������ռͨ�Ű뾶�ı���";
		String yAxisLabel = "���ĵ�������mJ��";
		String chartTitle = "������ռͨ�Ű뾶�ı��ʶ�������Ӱ��";
		String adjustedVariable = "gridWidthRate";
		String filePath = "ESA\\esaGAA";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ESAUtil.getDefaultESAUtil().showAndLogESAGAAEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
