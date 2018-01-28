package NHSensor.NHSensorSim.papers.RISC.averageEnergy;

import NHSensor.NHSensorSim.papers.RISC.GridTraverseRingExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NeighborNodeNumEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String xFieldName = "neighborNodeNum";
		String yFieldName = "consumedEnergy";
		String frameTitle = "GKNN VS IKNN";
		String xAxisLabel = "节点邻居节点数目（个）";
		String yAxisLabel = "消耗的能量（mJ）";
		String chartTitle = "节点数目对能量的影响";
		String adjustedVariable = "nodeNum";
		String filePath = "GridTraverseRing\\averageEnergy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		GridTraverseRingExperimentUtil.getDefaultUtil()
				.showAndLogAverageEnergy(adjustedVariable, absoluteFilePath,
						xFieldName, yFieldName, frameTitle, xAxisLabel,
						yAxisLabel, chartTitle);
	}

}
