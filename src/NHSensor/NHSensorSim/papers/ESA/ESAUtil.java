package NHSensor.NHSensorSim.papers.ESA;

import java.io.IOException;
import java.util.Vector;

import NHSensor.NHSensorSim.chart.ChartFrame;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPairCollections;
import NHSensor.NHSensorSim.util.FileAndSceenStream;
import NHSensor.NHSensorSim.util.Util;

public class ESAUtil {
	private boolean showEachExperimentResult = true;
	ESAParamsFactory eSAParamsFactory = new ESAParamsFactory();

	public ESAUtil() {

	}

	public static ESAUtil getDefaultESAUtil() {
		return new ESAUtil();
	}

	public boolean isShowEachExperimentResult() {
		return showEachExperimentResult;
	}

	public void setShowEachExperimentResult(boolean showEachExperimentResult) {
		this.showEachExperimentResult = showEachExperimentResult;
	}

	public ExperimentParamResultPairCollections energy(String variableName) {
		AllParam param;
		IWQEIdealUseICEnergyExperiment iwqe;
		ESAGBAEnergyExperiment esa;

		// DGSANewAlg Random
		// DGSANewAlgEnergyExperiment experimentDGSANewRandom;
		// DGSANewAlg dGSANewAlgRandom;
		// DGSANewAlg Random

		// chart code
		String colName1 = "IWQE";
		String colName2 = "ESA-GBA";
		// DGSANewAlg Random
		// String colName3 = "RSA_Random";
		ExperimentParamResultPairCollections resultCollections = new ExperimentParamResultPairCollections(
				colName1, colName2/* ,colName3 */);

		Vector params = new Vector();
		try {
			params = this.eSAParamsFactory.variable(variableName);
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

			iwqe = new IWQEIdealUseICEnergyExperiment(param);
			esa = new ESAGBAEnergyExperiment(param);
			iwqe.run();
			esa.run();

			// chart code
			resultCollections
					.add(colName1, iwqe.getExperimentParamResultPair());
			resultCollections.add(colName2, esa.getExperimentParamResultPair());
			// DGSANew Random
			// resultCollections.add(colName3,experimentDGSANewRandom.
			// getExperimentParamResultPair());

			if (this.isShowEachExperimentResult()) {
				System.out.print(" isSuccess(" + iwqe.isSuccess() + ")"
						+ " IWQE energy(" + iwqe.getConsumedEnergy() + ")");
				System.out.println(" isSuccess(" + esa.isSuccess() + ")"
						+ " ESA energy(" + esa.getConsumedEnergy() + ")");
				// System.out.println(" isSuccess(" +
				// experimentDGSANewRandom.isSuccess()
				// + ")" + " DGSANewenergy("
				// + experimentDGSANewRandom.getConsumedEnergy() + ")");
			}
		}
		return resultCollections;
	}

	public ExperimentParamResultPairCollections energyHasFailNode(
			String variableName) {
		AllParam param;
		IWQEIdealUseICEnergyExperimentHasFailNodes iwqe;
		ESAGBAEnergyExperimentHasFailNodes esa;

		// DGSANewAlg Random
		// DGSANewAlgEnergyExperiment experimentDGSANewRandom;
		// DGSANewAlg dGSANewAlgRandom;
		// DGSANewAlg Random

		// chart code
		String colName1 = "IWQE";
		String colName2 = "ESA-GBA";
		// DGSANewAlg Random
		// String colName3 = "RSA_Random";
		ExperimentParamResultPairCollections resultCollections = new ExperimentParamResultPairCollections(
				colName1, colName2/* ,colName3 */);

		Vector params = new Vector();
		try {
			params = this.eSAParamsFactory.variable(variableName);
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

			iwqe = new IWQEIdealUseICEnergyExperimentHasFailNodes(param);
			esa = new ESAGBAEnergyExperimentHasFailNodes(param);
			iwqe.run();
			esa.run();

			// chart code
			resultCollections
					.add(colName1, iwqe.getExperimentParamResultPair());
			resultCollections.add(colName2, esa.getExperimentParamResultPair());
			// DGSANew Random
			// resultCollections.add(colName3,experimentDGSANewRandom.
			// getExperimentParamResultPair());

			if (this.isShowEachExperimentResult()) {
				System.out.print(" isSuccess(" + iwqe.isSuccess() + ")"
						+ " IWQE energy(" + iwqe.getConsumedEnergy() + ")");
				System.out.println(" isSuccess(" + esa.isSuccess() + ")"
						+ " ESA energy(" + esa.getConsumedEnergy() + ")");
				// System.out.println(" isSuccess(" +
				// experimentDGSANewRandom.isSuccess()
				// + ")" + " DGSANewenergy("
				// + experimentDGSANewRandom.getConsumedEnergy() + ")");
			}
		}
		return resultCollections;
	}

	public ExperimentParamResultPairCollections twoESAEnergy(String variableName) {
		AllParam param;
		ESAGAAEnergyExperiment esaGAA;
		ESAGBAEnergyExperiment esaGBA;

		// DGSANewAlg Random
		// DGSANewAlgEnergyExperiment experimentDGSANewRandom;
		// DGSANewAlg dGSANewAlgRandom;
		// DGSANewAlg Random

		// chart code
		String colName1 = "ESAGAA";
		String colName2 = "ESAGBA";
		// DGSANewAlg Random
		// String colName3 = "RSA_Random";
		ExperimentParamResultPairCollections resultCollections = new ExperimentParamResultPairCollections(
				colName1, colName2/* ,colName3 */);

		Vector params = new Vector();
		try {
			params = this.eSAParamsFactory.variable(variableName);
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

			esaGAA = new ESAGAAEnergyExperiment(param);
			esaGBA = new ESAGBAEnergyExperiment(param);
			esaGAA.run();
			esaGBA.run();

			// chart code
			resultCollections.add(colName1, esaGAA
					.getExperimentParamResultPair());
			resultCollections.add(colName2, esaGBA
					.getExperimentParamResultPair());

			if (this.isShowEachExperimentResult()) {
				System.out.print(" isSuccess(" + esaGAA.isSuccess() + ")"
						+ " IWQE energy(" + esaGAA.getConsumedEnergy() + ")");
				System.out.println(" isSuccess(" + esaGBA.isSuccess() + ")"
						+ " ESA energy(" + esaGBA.getConsumedEnergy() + ")");
			}
		}
		return resultCollections;
	}

	public ExperimentParamResultPairCollections eSAGAAEnergy(String variableName) {
		AllParam param;
		ESAGAAEnergyExperiment esaGAA;

		// DGSANewAlg Random
		// DGSANewAlgEnergyExperiment experimentDGSANewRandom;
		// DGSANewAlg dGSANewAlgRandom;
		// DGSANewAlg Random

		// chart code
		String colName1 = "ESAGAA";
		// DGSANewAlg Random
		// String colName3 = "RSA_Random";
		ExperimentParamResultPairCollections resultCollections = new ExperimentParamResultPairCollections(
				colName1/* ,colName3 */);

		Vector params = new Vector();
		try {
			params = this.eSAParamsFactory.variable(variableName);
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

			esaGAA = new ESAGAAEnergyExperiment(param);
			esaGAA.run();

			// chart code
			resultCollections.add(colName1, esaGAA
					.getExperimentParamResultPair());

			if (this.isShowEachExperimentResult()) {
				System.out.println(esaGAA.getConsumedEnergy());
			}
		}
		return resultCollections;
	}

	public ExperimentParamResultPairCollections eSAGBAEnergy(String variableName) {
		AllParam param;
		ESAGBAEnergyExperiment esaGBA;

		// DGSANewAlg Random
		// DGSANewAlgEnergyExperiment experimentDGSANewRandom;
		// DGSANewAlg dGSANewAlgRandom;
		// DGSANewAlg Random

		// chart code
		String colName1 = "ESAGBA";
		// DGSANewAlg Random
		// String colName3 = "RSA_Random";
		ExperimentParamResultPairCollections resultCollections = new ExperimentParamResultPairCollections(
				colName1/* ,colName3 */);

		Vector params = new Vector();
		try {
			params = this.eSAParamsFactory.variable(variableName);
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

			esaGBA = new ESAGBAEnergyExperiment(param);
			esaGBA.run();

			// chart code
			resultCollections.add(colName1, esaGBA
					.getExperimentParamResultPair());

			if (this.isShowEachExperimentResult()) {
				System.out.println(esaGBA.getConsumedEnergy());
			}
		}
		return resultCollections;
	}

	public ExperimentParamResultPairCollections nodeNumGridWidthRateEnergy() {
		AllParam param;
		ESAEnergyExperiment esa;

		// DGSANewAlg Random
		// DGSANewAlgEnergyExperiment experimentDGSANewRandom;
		// DGSANewAlg dGSANewAlgRandom;
		// DGSANewAlg Random

		// chart code
		String colName2 = "ESA";
		// DGSANewAlg Random
		// String colName3 = "DGSANewRandom";
		// ExperimentParamResultPairCollections resultCollections =
		// new ExperimentParamResultPairCollections(colName2,colName3);
		ExperimentParamResultPairCollections resultCollections = new ExperimentParamResultPairCollections(
				colName2);

		Vector params = new Vector();
		try {
			params = this.eSAParamsFactory.nodeNumGridWidthRateVariable();
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

			esa = new ESAEnergyExperiment(param);
			esa.run();

			// DGSANew Random
			// experimentDGSANewRandom = new DGSANewAlgEnergyExperiment(param);
			// dGSANewAlgRandom=
			// (DGSANewAlg)(experimentDGSANewRandom.getAlgorithm());
			// dGSANewAlgRandom.setDescription("DGSANewAlgRandom");
			// dGSANewAlgRandom.setChooseNextNodeStrategy(DGSANewAlg.
			// CHOOSE_NEXT_NODE_BY_RANDOM);
			// experimentDGSANewRandom.run();

			// chart code
			resultCollections.add(colName2, esa.getExperimentParamResultPair());
			// DGSANew Random
			// resultCollections.add(colName3,experimentDGSANewRandom.
			// getExperimentParamResultPair());

			if (this.isShowEachExperimentResult()) {
				System.out.println(" isSuccess(" + esa.isSuccess() + ")"
						+ " ESA energy(" + esa.getConsumedEnergy() + ")");
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
		ESAEnergyExperiment esa;

		// DGSANewAlg Random
		// DGSANewAlgEnergyExperiment experimentDGSANewRandom;
		// DGSANewAlg dGSANewAlgRandom;
		// DGSANewAlg Random

		// chart code
		String colName2 = "ESA";
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
			params = this.eSAParamsFactory.radioRangeGridWidthRateVariable();
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

			esa = new ESAEnergyExperiment(param);
			esa.run();

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
			resultCollections.add(colName2, esa.getExperimentParamResultPair());
			// DGSANew Random
			// resultCollections.add(colName3,experimentDGSANewRandom.
			// getExperimentParamResultPair());

			if (this.isShowEachExperimentResult()) {
				System.out.println(" isSuccess(" + esa.isSuccess() + ")"
						+ " ESA energy(" + esa.getConsumedEnergy() + ")");
				/*
				 * System.out.println(" isSuccess(" +
				 * experimentDGSANewRandom.isSuccess() + ")" + " DGSANewenergy("
				 * + experimentDGSANewRandom.getConsumedEnergy() + ")");
				 */}
		}
		return resultCollections;
	}

	public ESAParamsFactory getESAParamsFactory() {
		return eSAParamsFactory;
	}

	public void setESAParamsFactory(ESAParamsFactory paramsFactory) {
		eSAParamsFactory = paramsFactory;
	}

	public void showAndLogESAGAAEnergy(String adjustedVariable,
			String filePath, String xFieldName, String yFieldName,
			String frameTitle, String xAxisLabel, String yAxisLabel,
			String chartTitle) {
		FileAndSceenStream.outputToFile(Util.generateFileName(filePath,
				xFieldName, yFieldName));

		ExperimentParamResultPairCollections resultCollections = ESAUtil
				.getDefaultESAUtil().eSAGAAEnergy(adjustedVariable);

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

	public void showAndLogESAGBAEnergy(String adjustedVariable,
			String filePath, String xFieldName, String yFieldName,
			String frameTitle, String xAxisLabel, String yAxisLabel,
			String chartTitle) {
		FileAndSceenStream.outputToFile(Util.generateFileName(filePath,
				xFieldName, yFieldName));

		ExperimentParamResultPairCollections resultCollections = ESAUtil
				.getDefaultESAUtil().eSAGBAEnergy(adjustedVariable);

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

	public void showAndLogTwoESAEnergy(String adjustedVariable,
			String filePath, String xFieldName, String yFieldName,
			String frameTitle, String xAxisLabel, String yAxisLabel,
			String chartTitle) {
		FileAndSceenStream.outputToFile(Util.generateFileName(filePath,
				xFieldName, yFieldName));

		ExperimentParamResultPairCollections resultCollections = ESAUtil
				.getDefaultESAUtil().twoESAEnergy(adjustedVariable);

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

	/**
	 * 
	 * @param adjustedVariable
	 * @param filePath
	 * @param xFieldName
	 * @param yFieldName
	 * @param frameTitle
	 * @param xAxisLabel
	 * @param yAxisLabel
	 * @param chartTitle
	 */
	public void showAndLogEnergy(String adjustedVariable, String filePath,
			String xFieldName, String yFieldName, String frameTitle,
			String xAxisLabel, String yAxisLabel, String chartTitle) {
		
		FileAndSceenStream.outputToFile(Util.generateFileName(filePath,
				xFieldName, yFieldName));

		ExperimentParamResultPairCollections resultCollections = ESAUtil
				.getDefaultESAUtil().energy(adjustedVariable);

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

	public void showAndLogSuccessRateHasFailNode(String adjustedVariable,
			String filePath, String xFieldName, String yFieldName,
			String frameTitle, String xAxisLabel, String yAxisLabel,
			String chartTitle) {
		FileAndSceenStream.outputToFile(Util.generateFileName(filePath,
				xFieldName, yFieldName));

		ExperimentParamResultPairCollections resultCollections = ESAUtil
				.getDefaultESAUtil().energyHasFailNode(adjustedVariable);

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
							xAxisLabel, yAxisLabel,
							false));
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

	public void showAndLogNodeNumGridWidthRateMinimizeEnergy(String filePath,
			String frameTitle, String xAxisLabel, String yAxisLabel,
			String chartTitle) {
		String xFieldName = "nodeNum";
		String yFieldName = "gridWidthRate";
		String minimizeFieldName = "consumedEnergy";

		FileAndSceenStream.outputToFile(Util.generateFileName(filePath,
				xFieldName, yFieldName));

		ExperimentParamResultPairCollections resultCollections = ESAUtil
				.getDefaultESAUtil().nodeNumGridWidthRateEnergy();

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

		ExperimentParamResultPairCollections resultCollections = ESAUtil
				.getDefaultESAUtil().radioRangeGridWidthRateEnergy();

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
				xFieldName, yFieldName, minimizeFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);

	}
}
