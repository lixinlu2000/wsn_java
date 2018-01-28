package NHSensor.NHSensorSim.papers.GKNN;

import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.DGSANewAlg;
import NHSensor.NHSensorSim.experiment.AllParam;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPairCollections;
import NHSensor.NHSensorSim.experiment.IWQEAlgSuccessRateExperiment;
import NHSensor.NHSensorSim.papers.RSA.DGSANewAlgAverageGridAreaExperiment;
import NHSensor.NHSensorSim.papers.RSA.DGSANewAlgAverageGridHeightExperiment;
import NHSensor.NHSensorSim.papers.RSA.DGSANewAlgSuccessRateExperiment;

public class GKNNExperimentUtil {
	private boolean showEachExperimentResult = true;
	GKNNParamsFactory paramsFactory = new GKNNParamsFactory();

	public GKNNExperimentUtil() {

	}

	public static GKNNExperimentUtil getDefaultUtil() {
		return new GKNNExperimentUtil();
	}

	public boolean isShowEachExperimentResult() {
		return showEachExperimentResult;
	}

	public void setShowEachExperimentResult(boolean showEachExperimentResult) {
		this.showEachExperimentResult = showEachExperimentResult;
	}

	public ExperimentParamResultPairCollections energy(String variableName) {
		AllParam param;
		GKNNAlgEnergyExperiment experimentGKNN;
		IKNNAlgEnergyExperiment experimentIKNN;

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
			experimentGKNN = new GKNNAlgEnergyExperiment(param);
			experimentIKNN = new IKNNAlgEnergyExperiment(param);
			experimentGKNN.run();
			experimentIKNN.run();

			// chart code
			resultCollections.add(colName1, experimentGKNN
					.getExperimentParamResultPair());
			resultCollections.add(colName2, experimentIKNN
					.getExperimentParamResultPair());

			if (this.isShowEachExperimentResult()) {
				System.out.print(" isSuccess(" + experimentGKNN.isSuccess()
						+ ")" + " GKNNEenergy("
						+ experimentGKNN.getConsumedEnergy() + ")");
				System.out.println(" isSuccess(" + experimentIKNN.isSuccess()
						+ ")" + " IKNNenergy("
						+ experimentIKNN.getConsumedEnergy() + ")");
			}
		}
		return resultCollections;
	}

	public ExperimentParamResultPairCollections robustness(String variableName) {
		AllParam param;
		GKNNAlgRobustnessExperiment experimentGKNN;
		IKNNAlgRobustnessExperiment experimentIKNN;
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
			experimentGKNN = new GKNNAlgRobustnessExperiment(param);
			experimentIKNN = new IKNNAlgRobustnessExperiment(param);
			experimentGKNN.run();
			experimentIKNN.run();

			// chart code
			resultCollections.add(colName1, experimentGKNN
					.getExperimentParamResultPair());
			resultCollections.add(colName2, experimentIKNN
					.getExperimentParamResultPair());

			if (this.isShowEachExperimentResult()) {
				System.out.print(" isSuccess(" + experimentGKNN.isSuccess()
						+ ")" + " GKNNSuccessProbability("
						+ experimentGKNN.getSuccessProbability() + ")");
				System.out.println(" isSuccess(" + experimentIKNN.isSuccess()
						+ ")" + " IKNNSuccessProbability("
						+ experimentIKNN.getSuccessProbability() + ")");
			}
		}
		return resultCollections;
	}

	public GKNNParamsFactory getParamsFactory() {
		return paramsFactory;
	}

	public void setParamsFactory(GKNNParamsFactory paramsFactory) {
		this.paramsFactory = paramsFactory;
	}

	public ExperimentParamResultPairCollections successRate(String variableName) {
		AllParam param;
		IWQEAlgSuccessRateExperiment experimentIWQE;
		DGSANewAlgSuccessRateExperiment experimentDGSANew;

		// DGSANewAlg Random
		DGSANewAlgSuccessRateExperiment experimentDGSANewRandom;
		DGSANewAlg dGSANewAlgRandom;
		// DGSANewAlg Random

		// chart code
		String colName1 = "IWQE";
		String colName2 = "DGSANew";
		// DGSANewAlg Random
		String colName3 = "DGSANewRandom";
		ExperimentParamResultPairCollections resultCollections = new ExperimentParamResultPairCollections(
				colName1, colName2, colName3);

		Vector params = new Vector();
		try {
			params = this.paramsFactory.variable(variableName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < params.size(); i++) {
			param = (AllParam) params.elementAt(i);
			experimentIWQE = new IWQEAlgSuccessRateExperiment(param);
			experimentDGSANew = new DGSANewAlgSuccessRateExperiment(param);
			experimentIWQE.run();
			experimentDGSANew.run();

			// DGSANew Random
			experimentDGSANewRandom = new DGSANewAlgSuccessRateExperiment(param);
			dGSANewAlgRandom = (DGSANewAlg) (experimentDGSANewRandom
					.getAlgorithm());
			dGSANewAlgRandom.setDescription("DGSANewAlgRandom");
			dGSANewAlgRandom
					.setChooseNextNodeStrategy(DGSANewAlg.CHOOSE_NEXT_NODE_BY_RANDOM);
			experimentDGSANewRandom.run();

			// chart code
			resultCollections.add(colName1, experimentIWQE
					.getExperimentParamResultPair());
			resultCollections.add(colName2, experimentDGSANew
					.getExperimentParamResultPair());
			resultCollections.add(colName3, experimentDGSANewRandom
					.getExperimentParamResultPair());

			if (this.isShowEachExperimentResult()) {
				System.out.print(" IWQEisSuccess(" + experimentIWQE.isSuccess()
						+ ")");
				System.out.println(" DGSANewisSuccess("
						+ experimentDGSANew.isSuccess() + ")");
				System.out.println(" DGSANewAlgRandom("
						+ experimentDGSANewRandom.isSuccess() + ")");
			}

		}
		return resultCollections;
	}

	public ExperimentParamResultPairCollections averageGridHeight(
			String variableName) {
		AllParam param;
		DGSANewAlgAverageGridHeightExperiment experimentDGSANew;

		// DGSANewAlg Random
		DGSANewAlgAverageGridHeightExperiment experimentDGSANewRandom;
		DGSANewAlg dGSANewAlgRandom;
		// DGSANewAlg Random

		// chart code
		String colName2 = "DGSANew";
		// DGSANewAlg Random
		String colName3 = "DGSANewRandom";
		ExperimentParamResultPairCollections resultCollections = new ExperimentParamResultPairCollections(
				colName2, colName3);

		Vector params = new Vector();
		try {
			params = this.paramsFactory.variable(variableName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < params.size(); i++) {
			param = (AllParam) params.elementAt(i);
			experimentDGSANew = new DGSANewAlgAverageGridHeightExperiment(param);
			experimentDGSANew.run();

			// DGSANew Random
			experimentDGSANewRandom = new DGSANewAlgAverageGridHeightExperiment(
					param);
			dGSANewAlgRandom = (DGSANewAlg) (experimentDGSANewRandom
					.getAlgorithm());
			dGSANewAlgRandom.setDescription("DGSANewAlgRandom");
			dGSANewAlgRandom
					.setChooseNextNodeStrategy(DGSANewAlg.CHOOSE_NEXT_NODE_BY_RANDOM);
			experimentDGSANewRandom.run();

			// chart code
			resultCollections.add(colName2, experimentDGSANew
					.getExperimentParamResultPair());
			resultCollections.add(colName3, experimentDGSANewRandom
					.getExperimentParamResultPair());

			if (this.isShowEachExperimentResult()) {
				System.out.println("gridWidth(" + param.getGridWidthRate()
						+ ")" + " isSuccess(" + experimentDGSANew.isSuccess()
						+ ")" + " average gridHeight("
						+ experimentDGSANew.getAverageGridHeight() + ")");
				System.out.println("gridWidth(" + param.getGridWidthRate()
						+ ")" + " isSuccess("
						+ experimentDGSANewRandom.isSuccess() + ")"
						+ " average gridHeight("
						+ experimentDGSANewRandom.getAverageGridHeight() + ")");
			}

		}
		return resultCollections;
	}

	public ExperimentParamResultPairCollections averageGridArea(
			String variableName) {
		AllParam param;
		DGSANewAlgAverageGridAreaExperiment experimentDGSANew;

		// DGSANewAlg Random
		DGSANewAlgAverageGridAreaExperiment experimentDGSANewRandom;
		DGSANewAlg dGSANewAlgRandom;
		// DGSANewAlg Random

		// chart code
		String colName2 = "DGSANew";
		// DGSANewAlg Random
		String colName3 = "DGSANewRandom";
		ExperimentParamResultPairCollections resultCollections = new ExperimentParamResultPairCollections(
				colName2, colName3);

		Vector params = new Vector();
		try {
			params = this.paramsFactory.variable(variableName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < params.size(); i++) {
			param = (AllParam) params.elementAt(i);
			experimentDGSANew = new DGSANewAlgAverageGridAreaExperiment(param);
			experimentDGSANew.run();

			// DGSANew Random
			experimentDGSANewRandom = new DGSANewAlgAverageGridAreaExperiment(
					param);
			dGSANewAlgRandom = (DGSANewAlg) (experimentDGSANewRandom
					.getAlgorithm());
			dGSANewAlgRandom.setDescription("DGSANewAlgRandom");
			dGSANewAlgRandom
					.setChooseNextNodeStrategy(DGSANewAlg.CHOOSE_NEXT_NODE_BY_RANDOM);
			experimentDGSANewRandom.run();

			// chart code
			resultCollections.add(colName2, experimentDGSANew
					.getExperimentParamResultPair());
			resultCollections.add(colName3, experimentDGSANewRandom
					.getExperimentParamResultPair());

			if (this.isShowEachExperimentResult()) {
				System.out.println("gridWidth(" + param.getGridWidthRate()
						+ ")" + " isSuccess(" + experimentDGSANew.isSuccess()
						+ ")" + " average gridArea("
						+ experimentDGSANew.getAverageGridArea() + ")");
				System.out.println("gridWidth(" + param.getGridWidthRate()
						+ ")" + " isSuccess(" + experimentDGSANew.isSuccess()
						+ ")" + " average gridArea("
						+ experimentDGSANewRandom.getAverageGridArea() + ")");
			}

		}
		return resultCollections;
	}

}
