package NHSensor.NHSensorSim.papers.LSA.iwqe;

import NHSensor.NHSensorSim.papers.LSA.LSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumQueryResultCorrectRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "queryResultCorrectRate";
		String frameTitle = "IWQE";
		String xAxisLabel = "����ڵ���Ŀ������";
		String yAxisLabel = "��ѯ�����ȷ��";
		String chartTitle = "����ڵ���Ŀ�Բ�ѯ�����ȷ�Ե�Ӱ��";
		String adjustedVariable = "nodeNum";
		String filePath = "LSA\\iwqe";
		String absoluteFilePath = Util.generateFilePath(filePath);
		LSAUtil.getDefaultLSAUtil().showAndLogIWQEQueryCorrectnessRate(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}
}
