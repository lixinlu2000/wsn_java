package NHSensor.NHSensorSim.papers.HSA.self;

import NHSensor.NHSensorSim.papers.HSA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class KQueryResultCorrectRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "subAreaNum";
		String yFieldName = "queryResultCorrectRate";

		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] { false, false, false, false, true});
		String xAxisLabel = "��ѯ��������Ŀ";
		String yAxisLabel = "��ѯ�������";
		String chartTitle = "��ѯ��������Ŀ�Բ�ѯ���������Ӱ��";
		String adjustedVariable = "subAreaNum";
		String filePath = "HSA\\self";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogSuccessRateHasFailNode(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}
}
