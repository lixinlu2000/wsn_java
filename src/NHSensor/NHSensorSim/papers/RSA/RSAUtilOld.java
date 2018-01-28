package NHSensor.NHSensorSim.papers.RSA;

import java.io.IOException;
import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.DGSANewAlg;
import NHSensor.NHSensorSim.chart.ChartFrame;
import NHSensor.NHSensorSim.experiment.AllParam;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPairCollections;
import NHSensor.NHSensorSim.experiment.IWQEAlgAverageEnergyExperiment;
import NHSensor.NHSensorSim.experiment.IWQEAlgEnergyExperiment;
import NHSensor.NHSensorSim.experiment.IWQEAlgRobustnessExperiment;
import NHSensor.NHSensorSim.experiment.IWQEAlgSuccessRateExperiment;
import NHSensor.NHSensorSim.util.FileAndSceenStream;
import NHSensor.NHSensorSim.util.Util;

public class RSAUtilOld {
	private boolean showEachExperimentResult = true;
	RSAParamsFactory rSAParamsFactory = new RSAParamsFactory();

	public RSAUtilOld() {

	}

	public static RSAUtilOld getDefaultRSAUtil() {
		return new RSAUtilOld();
	}

	public boolean isShowEachExperimentResult() {
		return showEachExperimentResult;
	}

	public void setShowEachExperimentResult(boolean showEachExperimentResult) {
		this.showEachExperimentResult = showEachExperimentResult;
	}

	public ExperimentParamResultPairCollections energy(String variableName) {
		AllParam param;
		IWQEAlgEnergyExperiment experimentIWQE;
		DGSANewAlgEnergyExperiment experimentDGSANew;

		// DGSANewAlg Random
		DGSANewAlgEnergyExperiment experimentDGSANewRandom;
		DGSANewAlg dGSANewAlgRandom;
		// DGSANewAlg Random

		// chart code
		String colName1 = "IWQE";
		String colName2 = "RSA";
		// DGSANewAlg Random
		String colName3 = "RSA_Random";
		ExperimentParamResultPairCollections resultCollections = new ExperimentParamResultPairCollections(
				colName1, colName2, colName3);

		Vector params = new Vector();
		try {
			params = this.rSAParamsFactory.variable(variableName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < params.size(); i++) {
			param = (AllParam) params.elementAt(i);
			experimentIWQE = new IWQEAlgEnergyExperiment(param);
			experimentDGSANew = new DGSANewAlgEnergyExperiment(param);
			experimentIWQE.run();
			experimentDGSANew.run();

			// DGSANew Random
			experimentDGSANewRandom = new DGSANewAlgEnergyExperiment(param);
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
			// DGSANew Random
			resultCollections.add(colName3, experimentDGSANewRandom
					.getExperimentParamResultPair());

			if (this.isShowEachExperimentResult()) {
				System.out.print(" isSuccess(" + experimentIWQE.isSuccess()
						+ ")" + " IWQEenergy("
						+ experimentIWQE.getConsumedEnergy() + ")");
				System.out.println(" isSuccess("
						+ experimentDGSANew.isSuccess() + ")"
						+ " DGSANewenergy("
						+ experimentDGSANew.getConsumedEnergy() + ")");
				System.out.println(" isSuccess("
						+ experimentDGSANewRandom.isSuccess() + ")"
						+ " DGSANewenergy("
						+ experimentDGSANewRandom.getConsumedEnergy() + ")");
			}
		}
		return resultCollections;
	}

	public ExperimentParamResultPairCollections nodeNumGridWidthRateEnergy() {
		AllParam param;
		DGSANewAlgEnergyExperiment experimentDGSANew;

		// DGSANewAlg Random
		// DGSANewAlgEnergyExperiment experimentDGSANewRandom;
		// DGSANewAlg dGSANewAlgRandom;
		// DGSANewAlg Random

		// chart code
		String colName2 = "RSA";
		// DGSANewAlg Random
		// String colName3 = "DGSANewRandom";
		// ExperimentParamResultPairCollections resultCollections =
		// new ExperimentParamResultPairCollections(colName2,colName3);
		ExperimentParamResultPairCollections resultCollections = new ExperimentParamResultPairCollections(
				colName2);

		Vector params = new Vector();
		try {
			params = this.rSAParamsFactory.nodeNumGridWidthRateVariable();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < params.size(); i++) {
			param = (AllParam) params.elementAt(i);
			experimentDGSANew = new DGSANewAlgEnergyExperiment(param);
			experimentDGSANew.run();

			// DGSANew Random
			// experimentDGSANewRandom = new DGSANewAlgEnergyExperiment(param);
			// dGSANewAlgRandom=
			// (DGSANewAlg)(experimentDGSANewRandom.getAlgorithm());
			// dGSANewAlgRandom.setDescription("DGSANewAlgRandom");
			// dGSANewAlgRandom.setChooseNextNodeStrategy(DGSANewAlg.
			// CHOOSE_NEXT_NODE_BY_RANDOM);
			// experimentDGSANewRandom.run();

			// chart code
			resultCollections.add(colName2, experimentDGSANew
					.getExperimentParamResultPair());
			// DGSANew Random
			// resultCollections.add(colName3,experimentDGSANewRandom.
			// getExperimentParamResultPair());

			if (this.isShowEachExperimentResult()) {
				System.out.println(" isSuccess("
						+ experimentDGSANew.isSuccess() + ")"
						+ " DGSANewenergy("
						+ experimentDGSANew.getConsumedEnergy() + ")");
				// System.out.println(" isSuccess(" +
				// experimentDGSANewRandom.isSuccess()
				// + ")" + " DGSANewenergy("
				// + experimentDGSANewRandom.getConsumedEnergy() + ")");
			}
		}
		return resultCollections;
	}

	public ExperimentParamResultPairCollections radioRangeGridWidthRateEnergy() {
		AllParam param;
		DGSANewAlgEnergyExperiment experimentDGSANew;

		// DGSANewAlg Random
		// DGSANewAlgEnergyExperiment experimentDGSANewRandom;
		// DGSANewAlg dGSANewAlgRandom;
		// DGSANewAlg Random

		// chart code
		String colName2 = "RSA";
		// DGSANewAlg Random
		// String colName3 = "DGSANewRandom";
		/*
		 * ExperimentParamResultPairCollections resultCollections = new
		 * ExperimentParamResultPairCollections(colName2,colName3);
		 */
		ExperimentParamResultPairCollections resultCollections = new ExperimentParamResultPairCollections(
				colName2);

		Vector params = new Vector();
		try {
			params = this.rSAParamsFactory.radioRangeGridWidthRateVariable();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < params.size(); i++) {
			param = (AllParam) params.elementAt(i);
			experimentDGSANew = new DGSANewAlgEnergyExperiment(param);
			experimentDGSANew.run();

			// DGSANew Random
			/*
			 * experimentDGSANewRandom = new DGSANewAlgEnergyExperiment(param);
			 * dGSANewAlgRandom=
			 * (DGSANewAlg)(experimentDGSANewRandom.getAlgorithm());
			 * dGSANewAlgRandom.setDescription("DGSANewAlgRandom");
			 * dGSANewAlgRandom
			 * .setChooseNextNodeStrategy(DGSANewAlg.CHOOSE_NEXT_NODE_BY_RANDOM
			 * ); experimentDGSANewRandom.run();
			 */
			// chart code
			resultCollections.add(colName2, experimentDGSANew
					.getExperimentParamResultPair());
			// DGSANew Random
			// resultCollections.add(colName3,experimentDGSANewRandom.
			// getExperimentParamResultPair());

			if (this.isShowEachExperimentResult()) {
				System.out.println(" isSuccess("
						+ experimentDGSANew.isSuccess() + ")"
						+ " DGSANewenergy("
						+ experimentDGSANew.getConsumedEnergy() + ")");
				/*
				 * System.out.println(" isSuccess(" +
				 * experimentDGSANewRandom.isSuccess() + ")" + " DGSANewenergy("
				 * + experimentDGSANewRandom.getConsumedEnergy() + ")");
				 */}
		}
		return resultCollections;
	}

	public ExperimentParamResultPairCollections robustness(String variableName) {
		AllParam param;
		IWQEAlgRobustnessExperiment experimentIWQE;
		DGSANewAlgRobustnessExperiment experimentDGSANew;

		// DGSANewAlg Random
		DGSANewAlgRobustnessExperiment experimentDGSANewRandom;
		DGSANewAlg dGSANewAlgRandom;
		// DGSANewAlg Random

		// chart code
		String colName1 = "IWQE";
		String colName2 = "RSA";
		// DGSANewAlg Random
		String colName3 = "RSA_Random";
		ExperimentParamResultPairCollections resultCollections = new ExperimentParamResultPairCollections(
				colName1, colName2, colName3);

		Vector params = new Vector();
		try {
			params = this.rSAParamsFactory.variable(variableName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < params.size(); i++) {
			param = (AllParam) params.elementAt(i);
			experimentIWQE = new IWQEAlgRobustnessExperiment(param);
			experimentDGSANew = new DGSANewAlgRobustnessExperiment(param);
			experimentIWQE.run();
			experimentDGSANew.run();

			// DGSANew Random
			experimentDGSANewRandom = new DGSANewAlgRobustnessExperiment(param);
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
			// DGSANew Random
			resultCollections.add(colName3, experimentDGSANewRandom
					.getExperimentParamResultPair());

			if (this.isShowEachExperimentResult()) {
				System.out.print(" isSuccess(" + experimentIWQE.isSuccess()
						+ ")" + " IWQEenergy("
						+ experimentIWQE.getSuccessProbability() + ")");
				System.out.println(" isSuccess("
						+ experimentDGSANew.isSuccess() + ")"
						+ " DGSANewenergy("
						+ experimentDGSANew.getSuccessProbability() + ")");
				System.out
						.println(" isSuccess("
								+ experimentDGSANewRandom.isSuccess()
								+ ")"
								+ " DGSANewenergy("
								+ experimentDGSANewRandom
										.getSuccessProbability() + ")");
			}
		}
		return resultCollections;
	}

	public RSAParamsFactory getRSAParamsFactory() {
		return rSAParamsFactory;
	}

	public void setRSAParamsFactory(RSAParamsFactory paramsFactory) {
		rSAParamsFactory = paramsFactory;
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
		String colName2 = "RSA";
		// DGSANewAlg Random
		String colName3 = "RSA_Random";
		ExperimentParamResultPairCollections resultCollections = new ExperimentParamResultPairCollections(
				colName1, colName2, colName3);

		Vector params = new Vector();
		try {
			params = this.rSAParamsFactory.variable(variableName);
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
		String colName2 = "RSA";
		// DGSANewAlg Random
		String colName3 = "RSA_Random";
		ExperimentParamResultPairCollections resultCollections = new ExperimentParamResultPairCollections(
				colName2, colName3);

		Vector params = new Vector();
		try {
			params = this.rSAParamsFactory.variable(variableName);
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
		String colName2 = "RSA";
		// DGSANewAlg Random
		String colName3 = "RSA_Random";
		ExperimentParamResultPairCollections resultCollections = new ExperimentParamResultPairCollections(
				colName2, colName3);

		Vector params = new Vector();
		try {
			params = this.rSAParamsFactory.variable(variableName);
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

	public ExperimentParamResultPairCollections averageEnergy(
			String variableName) {
		AllParam param;
		IWQEAlgAverageEnergyExperiment experimentIWQE;
		DGSANewAlgAverageEnergyExperiment experimentDGSANew;

		// DGSANewAlg Random
		DGSANewAlgAverageEnergyExperiment experimentDGSANewRandom;
		DGSANewAlg dGSANewAlgRandom;
		// DGSANewAlg Random

		// chart code
		String colName1 = "IWQE";
		String colName2 = "RSA";
		// DGSANewAlg Random
		String colName3 = "RSA-Random";
		ExperimentParamResultPairCollections resultCollections = new ExperimentParamResultPairCollections(
				colName1, colName2, colName3);

		Vector params = new Vector();
		try {
			params = this.rSAParamsFactory.variable(variableName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < params.size(); i++) {
			param = (AllParam) params.elementAt(i);
			experimentIWQE = new IWQEAlgAverageEnergyExperiment(param);
			experimentDGSANew = new DGSANewAlgAverageEnergyExperiment(param);
			experimentIWQE.run();
			experimentDGSANew.run();

			// DGSANew Random
			experimentDGSANewRandom = new DGSANewAlgAverageEnergyExperiment(
					param);
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
			// DGSANew Random
			resultCollections.add(colName3, experimentDGSANewRandom
					.getExperimentParamResultPair());

			if (this.isShowEachExperimentResult()) {
				System.out.print(" isSuccess(" + experimentIWQE.isSuccess()
						+ ")" + " IWQEenergy("
						+ experimentIWQE.getConsumedEnergy() + ")");
				System.out.println(" isSuccess("
						+ experimentDGSANew.isSuccess() + ")"
						+ " DGSANewenergy("
						+ experimentDGSANew.getConsumedEnergy() + ")");
				System.out.println(" isSuccess("
						+ experimentDGSANewRandom.isSuccess() + ")"
						+ " DGSANewenergy("
						+ experimentDGSANewRandom.getConsumedEnergy() + ")");
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

		ExperimentParamResultPairCollections resultCollections = RSAUtilOld
				.getDefaultRSAUtil().averageEnergy(adjustedVariable);

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

		ExperimentParamResultPairCollections resultCollections = RSAUtilOld
				.getDefaultRSAUtil().energy(adjustedVariable);

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

	public void showAndLogNodeNumGridWidthRateMinimizeEnergy(String filePath,
			String frameTitle, String xAxisLabel, String yAxisLabel,
			String chartTitle) {
		String xFieldName = "nodeNum";
		String yFieldName = "gridWidthRate";
		String minimizeFieldName = "consumedEnergy";

		FileAndSceenStream.outputToFile(Util.generateFileName(filePath,
				xFieldName, yFieldName));

		ExperimentParamResultPairCollections resultCollections = RSAUtilOld
				.getDefaultRSAUtil().nodeNumGridWidthRateEnergy();

		// chart code
		System.out.println(resultCollections.toString());
		System.out
				.println(resultCollections.toXYString(xFieldName, yFieldName));
		try {
			Util.writeStringToFile(Util.generateMatlabFileName(filePath,
					xFieldName, yFieldName), Util
					.experimentsToMatlabPlotProgramme(resultCollections,
							xFieldName, yFieldName, minimizeFieldName,
							xAxisLabel, yAxisLabel));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ChartFrame.showParamResultPairCollections(resultCollections,
				xFieldName, yFieldName, minimizeFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);

	}

	public void showAndLogRadioRangeGridWidthRateMinimizeEnergy(
			String filePath, String frameTitle, String xAxisLabel,
			String yAxisLabel, String chartTitle) {
		String xFieldName = "radioRange";
		String yFieldName = "gridWidthRate";
		String minimizeFieldName = "consumedEnergy";

		FileAndSceenStream.outputToFile(Util.generateFileName(filePath,
				xFieldName, yFieldName));

		ExperimentParamResultPairCollections resultCollections = RSAUtilOld
				.getDefaultRSAUtil().radioRangeGridWidthRateEnergy();

		// chart code
		System.out.println(resultCollections.toString());
		System.out.println(resultCollections.toXYString(xFieldName, yFieldName,
				minimizeFieldName));
		try {
			Util.writeStringToFile(Util.generateMatlabFileName(filePath,
					xFieldName, yFieldName), Util
					.experimentsToMatlabPlotProgramme(resultCollections,
							xFieldName, yFieldName, minimizeFieldName,
							xAxisLabel, yAxisLabel));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ChartFrame.showParamResultPairCollections(resultCollections,
				xFieldName, yFieldName, minimizeFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);

	}

	public void showAndLogRobustness(String adjustedVariable, String filePath,
			String xFieldName, String yFieldName, String frameTitle,
			String xAxisLabel, String yAxisLabel, String chartTitle) {
		FileAndSceenStream.outputToFile(Util.generateFileName(filePath,
				xFieldName, yFieldName));

		ExperimentParamResultPairCollections resultCollections = RSAUtilOld
				.getDefaultRSAUtil().robustness(adjustedVariable);

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

	public void showAndLogAverageGridArea(String adjustedVariable,
			String filePath, String xFieldName, String yFieldName,
			String frameTitle, String xAxisLabel, String yAxisLabel,
			String chartTitle) {
		FileAndSceenStream.outputToFile(Util.generateFileName(filePath,
				xFieldName, yFieldName));

		ExperimentParamResultPairCollections resultCollections = RSAUtilOld
				.getDefaultRSAUtil().averageGridArea(adjustedVariable);

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

	public void showAndLogAverageGridHeight(String adjustedVariable,
			String filePath, String xFieldName, String yFieldName,
			String frameTitle, String xAxisLabel, String yAxisLabel,
			String chartTitle) {
		FileAndSceenStream.outputToFile(Util.generateFileName(filePath,
				xFieldName, yFieldName));

		ExperimentParamResultPairCollections resultCollections = RSAUtilOld
				.getDefaultRSAUtil().averageGridHeight(adjustedVariable);

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

	public void showAndLogSuccessRate(String adjustedVariable, String filePath,
			String xFieldName, String yFieldName, String frameTitle,
			String xAxisLabel, String yAxisLabel, String chartTitle) {
		FileAndSceenStream.outputToFile(Util.generateFileName(filePath,
				xFieldName, yFieldName));

		ExperimentParamResultPairCollections resultCollections = RSAUtilOld
				.getDefaultRSAUtil().successRate(adjustedVariable);

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
}
