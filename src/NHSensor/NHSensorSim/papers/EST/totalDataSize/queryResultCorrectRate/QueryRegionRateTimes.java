package NHSensor.NHSensorSim.papers.EST.totalDataSize.queryResultCorrectRate;

import NHSensor.NHSensorSim.papers.EST.totalDataSize.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class QueryRegionRateTimes {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryRegionRate";
		String yFieldName = "queryResultCorrectRate";
		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] { true, true, true, false, false, false, true,
						true });
		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		String xAxisLabel = "��ѯ�������ռ���縲������ı���";
		String yAxisLabel = "ִ�еĲ�ѯ����";
		String chartTitle = "��ѯ�������ռ���縲������ı����������������ڵ�Ӱ��";
		String adjustedVariable = "queryRegionRate";
		String filePath = "EST\\totalDataSize\\times";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergyWithFail(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
