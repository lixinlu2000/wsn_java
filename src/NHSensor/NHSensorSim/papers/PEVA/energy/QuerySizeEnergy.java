package NHSensor.NHSensorSim.papers.PEVA.energy;

import NHSensor.NHSensorSim.papers.PEVA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class QuerySizeEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryMessageSize";
		String yFieldName = "consumedEnergy";
		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		String xAxisLabel = "��ѯ��Ϣ��С���ֽڣ�";
		String yAxisLabel = "���ĵ�������mJ��";
		String chartTitle = "��ѯ��Ϣ��С��������Ӱ��";
		String adjustedVariable = "queryMessageSize";
		String filePath = "PEVA\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
