package NHSensor.NHSensorSim.papers.LSA.lsa;

import NHSensor.NHSensorSim.papers.LSA.LSAUtil;
import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class GridWidthRateItineraryNodeSize {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "gridWidthRate";
		String yFieldName = "itineraryNodeSize";
		String frameTitle = "LSA";
		String xAxisLabel = "网格宽度";
		String yAxisLabel = "查询节点数目";
		String chartTitle = "网格宽度对查询节点数目的影响";
		String adjustedVariable = "gridWidthRate";
		String filePath = "LSA\\lsa";
		String absoluteFilePath = Util.generateFilePath(filePath);
		LSAUtil.getDefaultLSAUtil().showAndLogLSAEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
