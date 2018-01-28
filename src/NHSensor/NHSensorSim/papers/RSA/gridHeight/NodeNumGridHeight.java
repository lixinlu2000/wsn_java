package NHSensor.NHSensorSim.papers.RSA.gridHeight;

import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumGridHeight {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "result";
		String frameTitle = "RSA nodeNum -> gridHeight";
		String xAxisLabel = "����ڵ���Ŀ������";
		String yAxisLabel = "��̬���ɵ�����߶�ƽ��ֵ";
		String chartTitle = "����ڵ���Ŀ�Զ�̬���ɵ�����߶�ƽ��ֵ��Ӱ��";
		String adjustedVariable = "nodeNum";
		String filePath = "RSA\\gridHeight";
		String absoluteFilePath = Util.generateFilePath(filePath);
		RSAUtil.getDefaultRSAUtil().showAndLogAverageGridHeight(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
