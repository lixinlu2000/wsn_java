package NHSensor.NHSensorSim.papers.ESA.energy;

import NHSensor.NHSensorSim.papers.ESA.ESAUtil;
import NHSensor.NHSensorSim.util.Util;

public class GridWidthRateQueryNodeNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "gridWidthRate";
		String yFieldName = "queryNodeNum";
		String frameTitle = "ESA-GBA VS IWQE";
		String xAxisLabel = "网格宽度占通信半径的比率";
		String yAxisLabel = "查询节点数目";
		String chartTitle = "网格宽度占通信半径的比率对查询节点数目的影响";
		String adjustedVariable = "gridWidthRate";
		String filePath = "ESA\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ESAUtil.getDefaultESAUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
