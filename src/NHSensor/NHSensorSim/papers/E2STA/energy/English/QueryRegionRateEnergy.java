package NHSensor.NHSensorSim.papers.E2STA.energy.English;

import NHSensor.NHSensorSim.papers.E2STA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class QueryRegionRateEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryRegionRate";
		String yFieldName = "consumedEnergy";
		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] { false, false, true, false, false,  false, false, true, false, false });
		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		String xAxisLabel = "The ratio of query region to area covered";
		String yAxisLabel = "Energy consumption(mJ)";
		String chartTitle = "��ѯ�������ռ���縲������ı�����������Ӱ��";
		String adjustedVariable = "queryRegionRate";
		String filePath = "E2STA\\energy\\English";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
