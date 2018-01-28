package NHSensor.NHSensorSim.papers.CSA.time.English;

import NHSensor.NHSensorSim.papers.CSA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class QueryRegionRateTime {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryRegionRate";
		String yFieldName = "communicationTime";
		String frameTitle = "ESA-GBA VS CSA-CDA";
		String xAxisLabel = "The ratio of query region to area covered";
		String yAxisLabel = "Time latency (s)";
		String chartTitle = "查询区域面积占网络覆盖区域的比例对能量的影响";
		String adjustedVariable = "queryRegionRate";
		String filePath = "CSA\\time\\English";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
