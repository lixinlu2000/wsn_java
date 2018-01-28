package NHSensor.NHSensorSim.papers.AdaptiveGridTraverseRing;

import java.io.IOException;
import java.util.Vector;

import NHSensor.NHSensorSim.chart.ChartFrame;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPairCollections;
import NHSensor.NHSensorSim.util.FileAndSceenStream;
import NHSensor.NHSensorSim.util.Util;

/*
 * 
 */
public class AdaptiveGridTraverseRingExperimentUtil {
	private boolean showEachExperimentResult = true;
	AdaptiveGridTraverseRingParamsFactory paramsFactory;

	public AdaptiveGridTraverseRingExperimentUtil() {
		paramsFactory = new AdaptiveGridTraverseRingParamsFactory();
	}

	public static AdaptiveGridTraverseRingExperimentUtil getDefaultUtil() {
		return new AdaptiveGridTraverseRingExperimentUtil();
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
		AdaptiveGridTraverseRingEventEnergyExperiment experimentGKNN;
		ItineraryTraverseRingEventEnergyExperiment experimentIKNN;

		// chart code
		String colName1 = "ARISC";
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
			experimentGKNN = new AdaptiveGridTraverseRingEventEnergyExperiment(
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
						+ ")" + " AdaptiveGridTraverseRingEvent Eenergy("
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
		AdaptiveGridTraverseRingEventAverageEnergyExperiment experimentGKNN;
		ItineraryTraverseRingEventAverageEnergyExperiment experimentIKNN;

		// chart code
		String colName1 = "ARISC";
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
			experimentGKNN = new AdaptiveGridTraverseRingEventAverageEnergyExperiment(
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
						+ ")" + " AdaptiveGridTraverseRingEvent Eenergy("
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
		AdaptiveGridTraverseRingEventRobustnessExperiment experimentGKNN;
		ItineraryTraverseRingEventRobustnessExperiment experimentIKNN;
		// chart code
		String colName1 = "ARISC";
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
			experimentGKNN = new AdaptiveGridTraverseRingEventRobustnessExperiment(
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
				System.out.print(" isSuccess(" + experimentGKNN.isSuccess()
						+ ")"
						+ " AdaptiveGridTraverseRingEvent SuccessProbability("
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

		ExperimentParamResultPairCollections resultCollections = AdaptiveGridTraverseRingExperimentUtil
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

		ExperimentParamResultPairCollections resultCollections = AdaptiveGridTraverseRingExperimentUtil
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

		ExperimentParamResultPairCollections resultCollections = AdaptiveGridTraverseRingExperimentUtil
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

	public AdaptiveGridTraverseRingParamsFactory getParamsFactory() {
		return paramsFactory;
	}

	public void setParamsFactory(
			AdaptiveGridTraverseRingParamsFactory paramsFactory) {
		this.paramsFactory = paramsFactory;
	}
}
