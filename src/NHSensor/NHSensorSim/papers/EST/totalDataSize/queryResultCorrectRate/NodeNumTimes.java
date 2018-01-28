package NHSensor.NHSensorSim.papers.EST.totalDataSize.queryResultCorrectRate;

import NHSensor.NHSensorSim.papers.EST.totalDataSize.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumTimes {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "queryResultCorrectRate";
		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] { true, true, true, false, false, false, true,
						true });
		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		String xAxisLabel = "����ڵ���Ŀ������";
		String yAxisLabel = "ִ�еĲ�ѯ����";
		String chartTitle = "����ڵ���Ŀ�������������ڵ�Ӱ��";
		String adjustedVariable = "nodeNum";
		String filePath = "EST\\totalDataSize\\times";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergyWithFail(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}
}
