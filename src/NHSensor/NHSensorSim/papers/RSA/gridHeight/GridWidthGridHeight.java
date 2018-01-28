package NHSensor.NHSensorSim.papers.RSA.gridHeight;

import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class GridWidthGridHeight {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "gridWidthRate";
		String yFieldName = "result";
		String frameTitle = "RSA gridWith -> gridHeight";
		String xAxisLabel = "������ռͨ�Ű뾶�ı���";
		String yAxisLabel = "��̬���ɵ�����߶�ƽ��ֵ";
		String chartTitle = "������ռͨ�Ű뾶�ı����Զ�̬���ɵ�����߶�ƽ��ֵ��Ӱ��";
		String adjustedVariable = "gridWidthRate";
		String filePath = "RSA\\gridHeight";
		String absoluteFilePath = Util.generateFilePath(filePath);
		RSAUtil.getDefaultRSAUtil().showAndLogAverageGridHeight(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
