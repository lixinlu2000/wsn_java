package NHSensor.NHSensorSim.papers.GKNNEx;

import java.util.Vector;

import NHSensor.NHSensorSim.experiment.AllParam;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPairCollections;

public class GKNNExExperimentUtil {
	private boolean showEachExperimentResult = true;
	GKNNExParamsFactory paramsFactory = new GKNNExParamsFactory();

	public GKNNExExperimentUtil() {

	}

	public static GKNNExExperimentUtil getDefaultUtil() {
		return new GKNNExExperimentUtil();
	}

	public boolean isShowEachExperimentResult() {
		return showEachExperimentResult;
	}

	public void setShowEachExperimentResult(boolean showEachExperimentResult) {
		this.showEachExperimentResult = showEachExperimentResult;
	}

	public ExperimentParamResultPairCollections energy(String variableName) {
		AllParam param;
		GKNNExAlgEnergyExperiment experimentGKNN;
		IKNNExAlgEnergyExperiment experimentIKNN;

		// chart code
		String colName1 = "GKNN";
		String colName2 = "IKNN";
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
			param = (AllParam) params.elementAt(i);
			experimentGKNN = new GKNNExAlgEnergyExperiment(param);
			experimentIKNN = new IKNNExAlgEnergyExperiment(param);
			experimentGKNN.run();
			experimentIKNN.run();

			// chart code
			resultCollections.add(colName1, experimentGKNN
					.getExperimentParamResultPair());
			resultCollections.add(colName2, experimentIKNN
					.getExperimentParamResultPair());

			if (this.isShowEachExperimentResult()) {
				System.out.print(" isSuccess(" + experimentGKNN.isSuccess()
						+ ")" + " GKNNExEenergy("
						+ experimentGKNN.getConsumedEnergy() + ")");
				System.out.println(" isSuccess(" + experimentIKNN.isSuccess()
						+ ")" + " IKNNExEnergy("
						+ experimentIKNN.getConsumedEnergy() + ")");
			}
		}
		return resultCollections;
	}

	public ExperimentParamResultPairCollections robustness(String variableName) {
		AllParam param;
		GKNNExAlgRobustnessExperiment experimentGKNN;
		IKNNExAlgRobustnessExperiment experimentIKNN;
		// chart code
		String colName1 = "GKNN";
		String colName2 = "IKNN";
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
			param = (AllParam) params.elementAt(i);
			experimentGKNN = new GKNNExAlgRobustnessExperiment(param);
			experimentIKNN = new IKNNExAlgRobustnessExperiment(param);
			experimentGKNN.run();
			experimentIKNN.run();

			// chart code
			resultCollections.add(colName1, experimentGKNN
					.getExperimentParamResultPair());
			resultCollections.add(colName2, experimentIKNN
					.getExperimentParamResultPair());

			if (this.isShowEachExperimentResult()) {
				System.out.print(" isSuccess(" + experimentGKNN.isSuccess()
						+ ")" + " GKNNExSuccessProbability("
						+ experimentGKNN.getSuccessProbability() + ")");
				System.out.println(" isSuccess(" + experimentIKNN.isSuccess()
						+ ")" + " IKNNExSuccessProbability("
						+ experimentIKNN.getSuccessProbability() + ")");
			}
		}
		return resultCollections;
	}

	public GKNNExParamsFactory getParamsFactory() {
		return paramsFactory;
	}

	public void setParamsFactory(GKNNExParamsFactory paramsFactory) {
		this.paramsFactory = paramsFactory;
	}
}
