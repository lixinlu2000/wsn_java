package NHSensor.NHSensorSim.papers.RISC.energy;

import NHSensor.NHSensorSim.papers.RISC.GridTraverseRingExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class QuerySizeEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryMessageSize";
		String yFieldName = "consumedEnergy";
		String frameTitle = "GKNN VS IKNN";
		String xAxisLabel = "��ѯ��Ϣ��С���ֽڣ�";
		String yAxisLabel = "���ĵ�������mJ��";
		String chartTitle = "��ѯ��Ϣ��С��������Ӱ��";
		String adjustedVariable = "queryMessageSize";
		String filePath = "GridTraverseRing\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		GridTraverseRingExperimentUtil.getDefaultUtil().showAndLogEnergy(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);

	}

}
