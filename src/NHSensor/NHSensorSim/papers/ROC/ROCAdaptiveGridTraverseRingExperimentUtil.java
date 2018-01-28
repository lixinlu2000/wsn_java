package NHSensor.NHSensorSim.papers.ROC;

import java.io.IOException;
import java.util.Vector;

import NHSensor.NHSensorSim.chart.ChartFrame;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPairCollections;
import NHSensor.NHSensorSim.util.FileAndSceenStream;
import NHSensor.NHSensorSim.util.Util;

public class ROCAdaptiveGridTraverseRingExperimentUtil {
	private boolean showEachExperimentResult = true;
	ROCAdaptiveGridTraverseRingParamsFactory paramsFactory;

	public ROCAdaptiveGridTraverseRingExperimentUtil() {
		paramsFactory = new ROCAdaptiveGridTraverseRingParamsFactory();
	}

	public static ROCAdaptiveGridTraverseRingExperimentUtil getDefaultUtil() {
		return new ROCAdaptiveGridTraverseRingExperimentUtil();
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
		TraverseRingExperimentParam param;
		ROCAdaptiveGridTraverseRingEventEnergyExperiment experimentGKNN;
		ItineraryTraverseRingEventEnergyExperiment experimentIKNN;

		// chart code
		String colName1 = "ROC";
		String colName2 = "IC";
		ExperimentParamResultPairCollections resultCollections = new ExperimentParamResultPairCollections(
				colName1, colName2);

		Vector params = new Vector();
		try {
			params = this.paramsFactory.variable(variableName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < params.size(); i++) {
			param = (TraverseRingExperimentParam) params.elementAt(i);
			experimentGKNN = new ROCAdaptiveGridTraverseRingEventEnergyExperiment(
					param);
			experimentIKNN = new ItineraryTraverseRingEventEnergyExperiment(
					param);
			experimentGKNN.setShowAnimator(showAnimator);
			experimentIKNN.setShowAnimator(showAnimator);
			experimentGKNN.run();
			experimentIKNN.run();

			// chart code
			resultCollections.add(colName1, experimentGKNN
					.getExperimentParamResultPair());
			resultCollections.add(colName2, experimentIKNN
					.getExperimentParamResultPair());

			if (this.isShowEachExperimentResult()) {
				System.out.print(" isSuccess(" + experimentGKNN.isSuccess()
						+ ")" + " ROCAdaptiveGridTraverseRingEvent Eenergy("
						+ experimentGKNN.getConsumedEnergy() + ")");
				System.out.println(" isSuccess(" + experimentIKNN.isSuccess()
						+ ")" + " ItineraryTraverseRingEvent energy("
						+ experimentIKNN.getConsumedEnergy() + ")");
			}
		}
		return resultCollections;
	}

	public ExperimentParamResultPairCollections averageEnergy(
			String variableName) {
		return this.averageEnergy(variableName, false);
	}

	public ExperimentParamResultPairCollections averageEnergy(
			String variableName, boolean showAnimator) {
		TraverseRingExperimentParam param;
		ROCAdaptiveGridTraverseRingEventAverageEnergyExperiment experimentGKNN;
		ItineraryTraverseRingEventAverageEnergyExperiment experimentIKNN;

		// chart code
		String colName1 = "ROC";
		String colName2 = "IC";
		ExperimentParamResultPairCollections resultCollections = new ExperimentParamResultPairCollections(
				colName1, colName2);

		Vector params = new Vector();
		try {
			params = this.paramsFactory.variable(variableName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < params.size(); i++) {
			param = (TraverseRingExperimentParam) params.elementAt(i);
			experimentGKNN = new ROCAdaptiveGridTraverseRingEventAverageEnergyExperiment(
					param);
			experimentIKNN = new ItineraryTraverseRingEventAverageEnergyExperiment(
					param);
			experimentGKNN.setShowAnimator(showAnimator);
			experimentIKNN.setShowAnimator(showAnimator);
			experimentGKNN.run();
			experimentIKNN.run();

			// chart code
			resultCollections.add(colName1, experimentGKNN
					.getExperimentParamResultPair());
			resultCollections.add(colName2, experimentIKNN
					.getExperimentParamResultPair());

			if (this.isShowEachExperimentResult()) {
				System.out.print(" isSuccess(" + experimentGKNN.isSuccess()
						+ ")" + " ROCAdaptiveGridTraverseRingEvent Eenergy("
						+ experimentGKNN.getConsumedEnergy() + ")");
				System.out.println(" isSuccess(" + experimentIKNN.isSuccess()
						+ ")" + " ItineraryTraverseRingEvent energy("
						+ experimentIKNN.getConsumedEnergy() + ")");
			}
		}
		return resultCollections;
	}

	public ExperimentParamResultPairCollections robustness(String variableName) {
		return this.robustness(variableName, false);
	}

	public ExperimentParamResultPairCollections robustness(String variableName,
			boolean showAnimator) {
		TraverseRingExperimentParam param;
		ROCAdaptiveGridTraverseRingEventRobustnessExperiment experimentGKNN;
		ItineraryTraverseRingEventRobustnessExperiment experimentIKNN;
		// chart code
		String colName1 = "ROC";
		String colName2 = "IC";
		ExperimentParamResultPairCollections resultCollections = new ExperimentParamResultPairCollections(
				colName1, colName2);

		Vector params = new Vector();
		try {
			params = this.paramsFactory.variable(variableName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < params.size(); i++) {
			param = (TraverseRingExperimentParam) params.elementAt(i);
			experimentGKNN = new ROCAdaptiveGridTraverseRingEventRobustnessExperiment(
					param);
			experimentIKNN = new ItineraryTraverseRingEventRobustnessExperiment(
					param);
			experimentGKNN.setShowAnimator(showAnimator);
			experimentIKNN.setShowAnimator(showAnimator);
			experimentGKNN.run();
			experimentIKNN.run();

			// chart code
			resultCollections.add(colName1, experimentGKNN
					.getExperimentParamResultPair());
			resultCollections.add(colName2, experimentIKNN
					.getExperimentParamResultPair());

			if (this.isShowEachExperimentResult()) {
				System.out
						.print(" isSuccess("
								+ experimentGKNN.isSuccess()
								+ ")"
								+ " ROCAdaptiveGridTraverseRingEvent SuccessProbability("
								+ experimentGKNN.getSuccessProbability() + ")");
				System.out.println(" isSuccess(" + experimentIKNN.isSuccess()
						+ ")"
						+ " ItineraryTraverseRingEvent SuccessProbability("
						+ experimentIKNN.getSuccessProbability() + ")");
			}
		}
		return resultCollections;
	}

	public void showAndLogAverageEnergy(String adjustedVariable,
			String filePath, String xFieldName, String yFieldName,
			String frameTitle, String xAxisLabel, String yAxisLabel,
			String chartTitle) {
		FileAndSceenStream.outputToFile(Util.generateFileName(filePath,
				xFieldName, yFieldName));

		ExperimentParamResultPairCollections resultCollections = ROCAdaptiveGridTraverseRingExperimentUtil
				.getDefaultUtil().averageEnergy(adjustedVariable);

		// chart code
		System.out.println(resultCollections.toString());
		System.out
				.println(resultCollections.toXYString(xFieldName, yFieldName));
		try {
			Util.writeStringToFile(Util.generateMatlabFileName(filePath,
					xFieldName, yFieldName), Util
					.experimentsToMatlabPlotProgramme(resultCollections,
							xFieldName, yFieldName, xAxisLabel, yAxisLabel));
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

	public void showAndLogRobustness(String adjustedVariable, String filePath,
			String xFieldName, String yFieldName, String frameTitle,
			String xAxisLabel, String yAxisLabel, String chartTitle) {
		FileAndSceenStream.outputToFile(Util.generateFileName(filePath,
				xFieldName, yFieldName));

		ExperimentParamResultPairCollections resultCollections = ROCAdaptiveGridTraverseRingExperimentUtil
				.getDefaultUtil().robustness(adjustedVariable);

		// chart code
		System.out.println(resultCollections.toString());
		System.out
				.println(resultCollections.toXYString(xFieldName, yFieldName));
		try {
			Util.writeStringToFile(Util.generateMatlabFileName(filePath,
					xFieldName, yFieldName), Util
					.experimentsToMatlabPlotProgramme(resultCollections,
							xFieldName, yFieldName, xAxisLabel, yAxisLabel));
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

	public void showAndLogEnergy(String adjustedVariable, String filePath,
			String xFieldName, String yFieldName, String frameTitle,
			String xAxisLabel, String yAxisLabel, String chartTitle) {
		FileAndSceenStream.outputToFile(Util.generateFileName(filePath,
				xFieldName, yFieldName));

		ExperimentParamResultPairCollections resultCollections = ROCAdaptiveGridTraverseRingExperimentUtil
				.getDefaultUtil().energy(adjustedVariable);

		// chart code
		System.out.println(resultCollections.toString());
		System.out
				.println(resultCollections.toXYString(xFieldName, yFieldName));
		try {
			Util.writeStringToFile(Util.generateMatlabFileName(filePath,
					xFieldName, yFieldName), Util
					.experimentsToMatlabPlotProgramme(resultCollections,
							xFieldName, yFieldName, xAxisLabel, yAxisLabel));
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

	public ROCAdaptiveGridTraverseRingParamsFactory getParamsFactory() {
		return paramsFactory;
	}

	public void setParamsFactory(
			ROCAdaptiveGridTraverseRingParamsFactory paramsFactory) {
		this.paramsFactory = paramsFactory;
	}
}
