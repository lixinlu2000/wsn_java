package NHSensor.NHSensorSim.papers.EDC.CDA_GBA.energy;

import NHSensor.NHSensorSim.papers.EDC.CDA_GBA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "consumedEnergy";
		String frameTitle = "ESA-GBA VS CSA-CDA";
		String xAxisLabel = "����ڵ���Ŀ������";
		String yAxisLabel = "���ĵ�������mJ��";
		String chartTitle = "����ڵ���Ŀ��������Ӱ��";
		String adjustedVariable = "nodeNum";
		String filePath = "CSACDA\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}