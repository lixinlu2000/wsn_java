package NHSensor.NHSensorSim.papers.RSA.gridArea;

import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class GridWidthGridArea {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "gridWidthRate";
		String yFieldName = "result";
		String frameTitle = "RSA gridWidthRate -> gridArea";
		String xAxisLabel = "������ռͨ�Ű뾶�ı���";
		String yAxisLabel = "��̬���ɵ��������ƽ��ֵ";
		String chartTitle = "������ռͨ�Ű뾶�ı����Զ�̬���ɵ��������ƽ��ֵ��Ӱ��";
		String adjustedVariable = "gridWidthRate";
		String filePath = "RSA\\gridArea";
		String absoluteFilePath = Util.generateFilePath(filePath);
		RSAUtil.getDefaultRSAUtil().showAndLogAverageGridArea(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
