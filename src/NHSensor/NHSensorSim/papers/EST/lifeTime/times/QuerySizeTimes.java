package NHSensor.NHSensorSim.papers.EST.lifeTime.times;

import NHSensor.NHSensorSim.papers.EST.lifeTime.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class QuerySizeTimes {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryMessageSize";
		String yFieldName = "queryNodeNum";
		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] { false, true, true, false, false, false, true,
						false });
		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		String xAxisLabel = "��ѯ��Ϣ��С���ֽڣ�";
		String yAxisLabel = "ִ�еĲ�ѯ����";
		String chartTitle = "��ѯ��Ϣ��С�������������ڵ�Ӱ��";
		String adjustedVariable = "queryMessageSize";
		String filePath = "EST\\lifeTime\\times";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergyWithFail(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
