package NHSensor.NHSensorSim.papers.ESA.energy;

import NHSensor.NHSensorSim.papers.ESA.ESAUtil;
import NHSensor.NHSensorSim.util.Util;

public class QueryRegionRateQueryNodeNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryRegionRate";
		String yFieldName = "queryNodeNum";
		String frameTitle = "ESA-GBA VS IWQE";
		String xAxisLabel = "��ѯ�������ռ���縲������ı���";
		String yAxisLabel = "��ѯ�ڵ���Ŀ";
		String chartTitle = "��ѯ�������ռ���縲������ı����Բ�ѯ�ڵ���Ŀ��Ӱ��";
		String adjustedVariable = "queryRegionRate";
		String filePath = "ESA\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ESAUtil.getDefaultESAUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
