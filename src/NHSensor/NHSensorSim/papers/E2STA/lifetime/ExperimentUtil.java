package NHSensor.NHSensorSim.papers.E2STA.lifetime;

import java.io.IOException;
import java.util.Vector;

import NHSensor.NHSensorSim.chart.ChartFrame;
import NHSensor.NHSensorSim.experiment.AlgorithmExperiment;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPairCollections;
import NHSensor.NHSensorSim.util.FileAndSceenStream;
import NHSensor.NHSensorSim.util.Util;

public class ExperimentUtil {
	private boolean showEachExperimentResult = true;
	private static ExperimentUtil eu = new ExperimentUtil();
	ParamsFactory paramsFactory;
	String[] algNames = new String[] { "FullFlood", "SWinFlood", "EcstaFlood",
			"EST_MQP_IDC", "EST_MQP_IDC_ByArea", "E2STA", "E2STAEx" };
	boolean[] algRunOrNot = new boolean[] { true, true, true, true, true, true, true };
	AlgorithmExperiment[] algorithmExperiments = new AlgorithmExperiment[algNames.length];
	boolean isShowAnimator = false;

	public ExperimentUtil() {
		paramsFactory = new ParamsFactory();
	}

	public static ExperimentUtil getDefaultUtil() {
		return eu;
	}

	public void setAlgRunOrNot(boolean[] algRunOrNot) {
		this.algRunOrNot = algRunOrNot;
	}

	public String getVsString() {
		StringBuffer sb = new StringBuffer();
		boolean first = true;

		for (int i = 0; i < algNames.length; i++) {
			if (algRunOrNot[i]) {
				if (first) {
					sb.append(algNames[i]);
					first = false;
				} else {
					sb.append(" vs ");
					sb.append(algNames[i]);
				}
			}
		}
		return sb.toString();
	}

	public boolean isShowEachExperimentResult() {
		return showEachExperimentResult;
	}

	public void setShowEachExperimentResult(boolean showEachExperimentResult) {
		this.showEachExperimentResult = showEachExperimentResult;
	}

	public ExperimentParamResultPairCollections energy(String variableName) {
		return this.energy(variableName, false);
	}

	public ExperimentParamResultPairCollections energy(String variableName,
			boolean showAnimator) {
		AllParam param;

		// chart code
		ExperimentParamResultPairCollections resultCollections = new ExperimentParamResultPairCollections(
				this.algNames, this.algRunOrNot);

		Vector params = new Vector();
		try {
			params = this.paramsFactory.variable(variableName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < params.size(); i++) {
			param = (AllParam) params.elementAt(i);
			if (this.isShowEachExperimentResult()) {
				System.out.println((i + 1) + "/" + params.size());
				System.out.println(param);
			}

			if (algRunOrNot[0])
				algorithmExperiments[0] = new FullFloodEnergyExperiment(param);
			if (algRunOrNot[1])
				algorithmExperiments[1] = new SWinFloodEnergyExperiment(param);
			if (algRunOrNot[2])
				algorithmExperiments[2] = new EcstaFloodEnergyExperiment(param);
			if (algRunOrNot[3])
				algorithmExperiments[3] = new RestaIDCFarMulticastQueryEnergyExperiment(
						param);
			if (algRunOrNot[4])
				algorithmExperiments[4] = new RestaIDCFarMulticastQueryPartitionByAreaEnergyExperiment(
						param);
			if (algRunOrNot[5])
				algorithmExperiments[5] = new E2STAEnergyExperiment(param);
			if (algRunOrNot[6])
				algorithmExperiments[6] = new E2STAExEnergyExperiment(param);
			

			for (int j = 0; j < this.algNames.length; j++) {
				if (algRunOrNot[j]) {
					algorithmExperiments[j].setShowAnimator(showAnimator);
					algorithmExperiments[j].run();
					resultCollections.add(algNames[j], algorithmExperiments[j]
							.getExperimentParamResultPair());
				}
			}

			if (this.isShowEachExperimentResult()) {
				for (int j = 0; j < this.algNames.length; j++) {
					if (algRunOrNot[j]) {
						System.out.println(algorithmExperiments[j].toString());
					}
				}
			}
		}
		return resultCollections;

	}

	public void showAndLogEnergy(String adjustedVariable, String filePath,
			String xFieldName, String yFieldName, String frameTitle,
			String xAxisLabel, String yAxisLabel, String chartTitle) {
		Util.generateCurrentTimeString();
		FileAndSceenStream.outputToFile(Util.generateFileName(filePath,
				xFieldName, yFieldName));

		ExperimentParamResultPairCollections resultCollections = ExperimentUtil
				.getDefaultUtil().energy(adjustedVariable, this.isShowAnimator);

		// chart code
		System.out.println(resultCollections.toString());
		System.out
				.println(resultCollections.toXYString(xFieldName, yFieldName));
		try {
			Util.writeStringToFile(Util.generateMatlabFileName(filePath,
					xFieldName, yFieldName), Util
					.experimentsToMatlabPlotProgramme(resultCollections,
							xFieldName, yFieldName, xAxisLabel, yAxisLabel));
			Util.writeStringToFile(Util.generateMatlabBarFileName(filePath,
					xFieldName, yFieldName), Util
					.experimentsToMatlabBarProgramme(resultCollections,
							xFieldName, yFieldName, xAxisLabel, yAxisLabel));
			Util.writeStringToFile(Util
					.generateMatlabBarHorizontalLegendFileName(filePath,
							xFieldName, yFieldName), Util
					.experimentsToMatlabBarHorizontalLegendProgramme(
							resultCollections, xFieldName, yFieldName,
							xAxisLabel, yAxisLabel));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ChartFrame.showParamResultPairCollections(resultCollections,
				xFieldName, yFieldName, frameTitle, xAxisLabel, yAxisLabel,
				chartTitle);
	}

	public void showAndLogEnergyWithFail(String adjustedVariable,
			String filePath, String xFieldName, String yFieldName,
			String frameTitle, String xAxisLabel, String yAxisLabel,
			String chartTitle) {
		Util.generateCurrentTimeString();
		FileAndSceenStream.outputToFile(Util.generateFileName(filePath,
				xFieldName, yFieldName));

		ExperimentParamResultPairCollections resultCollections = ExperimentUtil
				.getDefaultUtil().energy(adjustedVariable, this.isShowAnimator);

		// chart code
		System.out.println(resultCollections.toString());
		System.out.println(resultCollections.toXYString(xFieldName, yFieldName,
				false));
		try {
			Util.writeStringToFile(Util.generateMatlabFileName(filePath,
					xFieldName, yFieldName), Util
					.experimentsToMatlabPlotProgramme(resultCollections,
							xFieldName, yFieldName, xAxisLabel, yAxisLabel,
							false));
			Util.writeStringToFile(Util.generateMatlabBarFileName(filePath,
					xFieldName, yFieldName), Util
					.experimentsToMatlabBarProgramme(resultCollections,
							xFieldName, yFieldName, xAxisLabel, yAxisLabel,
							false));
			Util.writeStringToFile(Util
					.generateMatlabBarHorizontalLegendFileName(filePath,
							xFieldName, yFieldName), Util
					.experimentsToMatlabBarHorizontalLegendProgramme(
							resultCollections, xFieldName, yFieldName,
							xAxisLabel, yAxisLabel, false));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ChartFrame.showParamResultPairCollections(resultCollections,
				xFieldName, yFieldName, frameTitle, xAxisLabel, yAxisLabel,
				chartTitle, false);
	}

	public void showAndLogSuccessRateHasFailNode(String adjustedVariable,
			String filePath, String xFieldName, String yFieldName,
			String frameTitle, String xAxisLabel, String yAxisLabel,
			String chartTitle) {
		Util.generateCurrentTimeString();
		FileAndSceenStream.outputToFile(Util.generateFileName(filePath,
				xFieldName, yFieldName));

		ExperimentParamResultPairCollections resultCollections = ExperimentUtil
				.getDefaultUtil().energy(adjustedVariable, this.isShowAnimator);

		// chart code
		System.out.println(resultCollections.toString());
		System.out.println(resultCollections.toXYString(xFieldName, yFieldName,
				false));
		try {
			Util.writeStringToFile(Util.generateMatlabFileName(filePath,
					xFieldName, yFieldName), Util
					.experimentsToMatlabPlotProgramme(resultCollections,
							xFieldName, yFieldName, xAxisLabel, yAxisLabel,
							false));
			Util.writeStringToFile(Util.generateMatlabBarFileName(filePath,
					xFieldName, yFieldName), Util
					.experimentsToMatlabBarProgramme(resultCollections,
							xFieldName, yFieldName, xAxisLabel, yAxisLabel,
							false));
			Util.writeStringToFile(Util
					.generateMatlabBarHorizontalLegendFileName(filePath,
							xFieldName, yFieldName), Util
					.experimentsToMatlabBarHorizontalLegendProgramme(
							resultCollections, xFieldName, yFieldName,
							xAxisLabel, yAxisLabel, false));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ChartFrame.showParamResultPairCollections(resultCollections,
				xFieldName, yFieldName, frameTitle, xAxisLabel, yAxisLabel,
				chartTitle, false);
	}

	public ParamsFactory getParamsFactory() {
		return paramsFactory;
	}

	public void setParamsFactory(ParamsFactory paramsFactory) {
		this.paramsFactory = paramsFactory;
	}
}
