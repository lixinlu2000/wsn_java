package NHSensor.NHSensorSim.papers.RESA;

import java.io.IOException;
import java.util.Vector;

import NHSensor.NHSensorSim.chart.ChartFrame;
import NHSensor.NHSensorSim.experiment.AlgorithmExperiment;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPairCollections;
import NHSensor.NHSensorSim.util.FileAndSceenStream;
import NHSensor.NHSensorSim.util.Util;

public class ExperimentUtil {
	private boolean showEachExperimentResult = true;
	ParamsFactory paramsFactory;
	// final int ALG_COUNT = 5;
	// String[] algNames = new String[]
	// {"IWQE","RESAUseGrid","RESA","RESACAAUseGrid", "RESACAA"};
	final int ALG_COUNT = 3;
	String[] algNames = new String[] { "IWQE", "RESA_LA", "RESA_CA" };
	AlgorithmExperiment[] algorithmExperiments = new AlgorithmExperiment[ALG_COUNT];
	boolean isShowAnimator = false;

	public ExperimentUtil() {
		paramsFactory = new ParamsFactory();
	}

	public static ExperimentUtil getDefaultUtil() {
		return new ExperimentUtil();
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
				this.algNames);

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

			// algorithmExperiments[0] = new
			// IWQEIdealUseICEnergyExperiment(param);
			// algorithmExperiments[1] = new RESAUseGridEnergyExperiment(param);
			// algorithmExperiments[2] = new RESAEnergyExperiment(param);
			// algorithmExperiments[3] = new
			// RESACAAUseGridEnergyExperiment(param);
			// algorithmExperiments[4] = new RESACAAEnergyExperiment(param);

			algorithmExperiments[0] = new IWQEIdealUseICEnergyExperiment(param);
			algorithmExperiments[1] = new RESACAAUseGridEnergyExperiment(param);
			algorithmExperiments[2] = new RESACAAEnergyExperiment(param);

			for (int j = 0; j < this.ALG_COUNT; j++) {
				algorithmExperiments[j].setShowAnimator(showAnimator);
				algorithmExperiments[j].run();
				resultCollections.add(algNames[j], algorithmExperiments[j]
						.getExperimentParamResultPair());
			}

			if (this.isShowEachExperimentResult()) {
				for (int j = 0; j < this.ALG_COUNT; j++) {
					System.out.println(algorithmExperiments[j].toString());
				}
			}
		}
		return resultCollections;

	}

	public void showAndLogEnergy(String adjustedVariable, String filePath,
			String xFieldName, String yFieldName, String frameTitle,
			String xAxisLabel, String yAxisLabel, String chartTitle) {
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

	public void showAndLogQueryCorrectnessRate(String adjustedVariable,
			String filePath, String xFieldName, String yFieldName,
			String frameTitle, String xAxisLabel, String yAxisLabel,
			String chartTitle) {
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
