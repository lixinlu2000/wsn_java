package NHSensor.NHSensorSim.papers.CSA.time.English;

import NHSensor.NHSensorSim.papers.CSA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class QuerySizeTime {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryMessageSize";
		String yFieldName = "communicationTime";
		String frameTitle = "ESA-GBA VS CSA-CDA";
		String xAxisLabel = "Query message size (bytes)";
		String yAxisLabel = "Time latency (s)";
		String chartTitle = "��ѯ��Ϣ��С��������Ӱ��";
		String adjustedVariable = "queryMessageSize";
		String filePath = "CSA\\time\\English";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
