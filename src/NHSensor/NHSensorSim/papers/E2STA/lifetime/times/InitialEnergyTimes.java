package NHSensor.NHSensorSim.papers.E2STA.lifetime.times;

import NHSensor.NHSensorSim.papers.E2STA.lifetime.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class InitialEnergyTimes {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "initialEnergy";
		String yFieldName = "queryNodeNum";
		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] { false, true, true, false, false, true, true});
		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		String xAxisLabel = "�ڵ��ʼ������mJ��";
		String yAxisLabel = "ִ�еĲ�ѯ����";
		String chartTitle = "�ڵ��ʼ�����������������ڵ�Ӱ��";
		String adjustedVariable = "initialEnergy";
		String filePath = "E2STA\\lifeTime\\times";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergyWithFail(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
