package NHSensor.NHSensorSim.papers.RSA.gridHeight;

import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class QueryRegionRateGridHeight {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryRegionRate";
		String yFieldName = "result";
		String frameTitle = "RSA queryRegionRate -> gridHeight";
		String xAxisLabel = "��ѯ�����Сռ��������ı���";
		String yAxisLabel = "��̬���ɵ�����߶�ƽ��ֵ";
		String chartTitle = "����ڵ���Ŀ�Զ�̬���ɵ�����߶�ƽ��ֵ��Ӱ��";
		String adjustedVariable = "queryRegionRate";
		String filePath = "RSA\\gridHeight";
		String absoluteFilePath = Util.generateFilePath(filePath);
		RSAUtil.getDefaultRSAUtil().showAndLogAverageGridHeight(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
