package NHSensor.NHSensorSim.papers.EST.lifeTime.times;

import NHSensor.NHSensorSim.papers.EST.lifeTime.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumTimes {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "queryNodeNum";
		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] { false, true, true, false, false, false, true,
						false });
		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		String xAxisLabel = "����ڵ���Ŀ������";
		String yAxisLabel = "ִ�еĲ�ѯ����";
		String chartTitle = "����ڵ���Ŀ�������������ڵ�Ӱ��";
		String adjustedVariable = "nodeNum";
		String filePath = "EST\\lifeTime\\times";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergyWithFail(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}
}
