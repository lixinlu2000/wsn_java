package NHSensor.NHSensorSim.papers.EST.self.queryResultCorrectRate;

import NHSensor.NHSensorSim.papers.EST.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeIDQueryResultCorrectRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "networkID";
		String yFieldName = "queryResultCorrectRate";
		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] { false, false, false, true, true, true, true,
						false });
		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		String xAxisLabel = "����ID";
		String yAxisLabel = "��ѯ�������";
		String chartTitle = "����ID�Բ�ѯ���������Ӱ��";
		String adjustedVariable = "networkID";
		String filePath = "EST\\self\\queryResultCorrectRate";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogSuccessRateHasFailNode(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}
}
