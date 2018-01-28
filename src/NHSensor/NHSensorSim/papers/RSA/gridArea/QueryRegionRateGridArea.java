package NHSensor.NHSensorSim.papers.RSA.gridArea;

import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class QueryRegionRateGridArea {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryRegionRate";
		String yFieldName = "result";
		String frameTitle = "RSA queryRegionRate -> gridArea";
		String xAxisLabel = "��ѯ�������ռ��������ı���";
		String yAxisLabel = "��̬���ɵ��������ƽ��ֵ";
		String chartTitle = "��ѯ�������ռ��������ı����Զ�̬���ɵ��������ƽ��ֵ��Ӱ��";
		String adjustedVariable = "queryRegionRate";
		String filePath = "RSA\\gridArea";
		String absoluteFilePath = Util.generateFilePath(filePath);
		RSAUtil.getDefaultRSAUtil().showAndLogAverageGridArea(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);

	}

}
