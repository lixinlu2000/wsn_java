package NHSensor.NHSensorSim.papers.CSA.energy;

import NHSensor.NHSensorSim.papers.CSA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class QueryRegionRateEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryRegionRate";
		String yFieldName = "consumedEnergy";
		String frameTitle = "ESA-GBA VS CSA-CDA";
		String xAxisLabel = "查询区域面积占网络覆盖区域的比例";
		String yAxisLabel = "消耗的能量（mJ）";
		String chartTitle = "查询区域面积占网络覆盖区域的比例对能量的影响";
		String adjustedVariable = "queryRegionRate";
		String filePath = "CSA\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
