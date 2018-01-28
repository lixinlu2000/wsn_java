package NHSensor.NHSensorSim.papers.CSA.successProbability;

import NHSensor.NHSensorSim.papers.CSA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class GridWidthRateSuccessRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "gridWidthRate";
		String yFieldName = "isSuccess";
		String frameTitle = "ESA-GBA VS CSA-CDA";
		String xAxisLabel = "������ռͨ�Ű뾶�ı���";
		String yAxisLabel = "��ѯ�ɹ���";
		String chartTitle = "������ռͨ�Ű뾶�ı��ʶԲ�ѯ�ɹ��ʵ�Ӱ��";
		String adjustedVariable = "gridWidthRate";
		String filePath = "CSA\\isSuccess";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogSuccessRateHasFailNode(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
