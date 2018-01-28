package NHSensor.NHSensorSim.papers.RESALifeTime.timesSameInitialEnergy;

import NHSensor.NHSensorSim.papers.RESALifeTime.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class QueryRegionRateTimes {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryRegionRate";
		String yFieldName = "queryNodeNum";
		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] {true, true, true, true, true});
		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		String xAxisLabel = "��ѯ�������ռ���縲������ı���";
		String yAxisLabel = "ִ�еĲ�ѯ����";
		String chartTitle = "��ѯ�������ռ���縲������ı����������������ڵ�Ӱ��";
		String adjustedVariable = "queryRegionRate";
		String filePath = "RESA\\lifeTime\\timesSameInitialEnergy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergyWithFail(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
