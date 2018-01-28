package NHSensor.NHSensorSim.papers.E2STA.lifetime.times;

import NHSensor.NHSensorSim.papers.E2STA.lifetime.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class QueryRegionRateTimes {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryRegionRate";
		String yFieldName = "queryNodeNum";
		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] { false, true, true, true, true, true, true});
		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		String xAxisLabel = "��ѯ�������ռ���縲������ı���";
		String yAxisLabel = "ִ�еĲ�ѯ����";
		String chartTitle = "��ѯ�������ռ���縲������ı����������������ڵ�Ӱ��";
		String adjustedVariable = "queryRegionRate";
		String filePath = "E2STA\\lifeTime\\times";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergyWithFail(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
