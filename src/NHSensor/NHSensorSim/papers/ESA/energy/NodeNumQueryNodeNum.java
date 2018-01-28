package NHSensor.NHSensorSim.papers.ESA.energy;

import NHSensor.NHSensorSim.papers.ESA.ESAUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumQueryNodeNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "queryNodeNum";
		String frameTitle = "ESA-GBA VS IWQE";
		String xAxisLabel = "����ڵ���Ŀ������";
		String yAxisLabel = "��ѯ�ڵ���Ŀ";
		String chartTitle = "����ڵ���Ŀ�Բ�ѯ�ڵ���Ŀ��Ӱ��";
		String adjustedVariable = "nodeNum";
		String filePath = "ESA\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ESAUtil.getDefaultESAUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}
