package NHSensor.NHSensorSim.papers.LSA;

import java.io.IOException;
import java.util.Vector;

import NHSensor.NHSensorSim.chart.ChartFrame;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPairCollections;
import NHSensor.NHSensorSim.util.FileAndSceenStream;
import NHSensor.NHSensorSim.util.Util;

public class LSAUtil {
	private boolean showEachExperimentResult = true;
	LSAParamsFactory lSAParamsFactory = new LSAParamsFactory();

	public LSAUtil() {

	}

	public static LSAUtil getDefaultLSAUtil() {
		return new LSAUtil();
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
			boolean useTraversedNodeSumForSuccessRate) {
		AllParam param;
		IWQEIdealUseICEnergyExperiment iwqe;
		LSAEnergyExperiment lsa;

		// chart code
		String colName1 = "IWQE";
		String colName2 = "LSA";
		ExperimentParamResultPairCollections resultCollections = new ExperimentParamResultPairCollections(
				colName1, colName2/* ,colName3 */);

		Vector params = new Vector();
		try {
			params = this.lSAParamsFactory.variable(variableName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < params.size(); i++) {
			param = (AllParam) params.elementAt(i);
			iwqe = new IWQEIdealUseICEnergyExperiment(param,
					useTraversedNodeSumForSuccessRate);
			lsa = new LSAEnergyExperiment(param);
			iwqe.run();
			lsa.run();

			// chart code
			resultCollections
					.add(colName1, iwqe.getExperimentParamResultPair());
			resultCollections.add(colName2, lsa.getExperimentParamResultPair());
			// DGSANew Random
			// resultCollections.add(colName3,experimentDGSANewRandom.
			// getExperimentParamResultPair());

			if (this.isShowEachExperimentResult()) {
				System.out.print((i + 1) + "/" + params.size());
				System.out.print(" isSuccess(" + iwqe.isSuccess() + ")"
						+ " IWQE energy(" + iwqe.getConsumedEnergy() + ")");
				System.out.println(" isSuccess(" + lsa.isSuccess() + ")"
						+ " LSA energy(" + lsa.getConsumedEnergy() + ")");
				// System.out.println(" isSuccess(" +
				// experimentDGSANewRandom.isSuccess()
				// + ")" + " DGSANewenergy("
				// + experimentDGSANewRandom.getConsumedEnergy() + ")");
			}
		}
		return resultCollections;
	}

	public ExperimentParamResultPairCollections lsaEnergy(String variableName,
			boolean useTraversedNodeSumForSuccessRate) {
		AllParam param;
		LSAEnergyExperiment lsa;

		// chart code
		String colName2 = "LSA";
		ExperimentParamResultPairCollections resultCollections = new ExperimentParamResultPairCollections(
				colName2/* ,colName3 */);

		Vector params = new Vector();
		try {
			params = this.lSAParamsFactory.variable(variableName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < params.size(); i++) {
			param = (AllParam) params.elementAt(i);
			lsa = new LSAEnergyExperiment(param);
			lsa.run();

			// chart code
			resultCollections.add(colName2, lsa.getExperimentParamResultPair());
			// DGSANew Random
			// resultCollections.add(colName3,experimentDGSANewRandom.
			// getExperimentParamResultPair());

			if (this.isShowEachExperimentResult()) {
				System.out.print((i + 1) + "/" + params.size());
				System.out.println(" isSuccess(" + lsa.isSuccess() + ")"
						+ " LSA energy(" + lsa.getConsumedEnergy() + ")");
				// System.out.println(" isSuccess(" +
				// experimentDGSANewRandom.isSuccess()
				// + ")" + " DGSANewenergy("
				// + experimentDGSANewRandom.getConsumedEnergy() + ")");
			}
		}
		return resultCollections;
	}

	public ExperimentParamResultPairCollections iwqeEnergy(String variableName,
			boolean useTraversedNodeSumForSuccessRate) {
		AllParam param;
		IWQEIdealUseICEnergyExperiment iwqe;

		// chart code
		String colName1 = "IWQE";
		ExperimentParamResultPairCollections resultCollections = new ExperimentParamResultPairCollections(
				colName1/* ,colName3 */);

		Vector params = new Vector();
		try {
			params = this.lSAParamsFactory.variable(variableName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < params.size(); i++) {
			param = (AllParam) params.elementAt(i);
			iwqe = new IWQEIdealUseICEnergyExperiment(param,
					useTraversedNodeSumForSuccessRate);
			iwqe.run();

			// chart code
			resultCollections
					.add(colName1, iwqe.getExperimentParamResultPair());
			// DGSANew Random
			// resultCollections.add(colName3,experimentDGSANewRandom.
			// getExperimentParamResultPair());

			if (this.isShowEachExperimentResult()) {
				System.out.print((i + 1) + "/" + params.size());
				System.out.println(" isSuccess(" + iwqe.isSuccess() + ")"
						+ " IWQE energy(" + iwqe.getConsumedEnergy() + ")");
				// System.out.println(" isSuccess(" +
				// experimentDGSANewRandom.isSuccess()
				// + ")" + " DGSANewenergy("
				// + experimentDGSANewRandom.getConsumedEnergy() + ")");
			}
		}
		return resultCollections;
	}

	public LSAParamsFactory getLSAParamsFactory() {
		return lSAParamsFactory;
	}

	public void setLSAParamsFactory(LSAParamsFactory paramsFactory) {
		lSAParamsFactory = paramsFactory;
	}

	public void showAndLogEnergy(String adjustedVariable, String filePath,
			String xFieldName, String yFieldName, String frameTitle,
			String xAxisLabel, String yAxisLabel, String chartTitle) {
		FileAndSceenStream.outputToFile(Util.generateFileName(filePath,
				xFieldName, yFieldName));

		ExperimentParamResultPairCollections resultCollections = LSAUtil
				.getDefaultLSAUtil().energy(adjustedVariable, true);

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

	public void showAndLogIWQEEnergy(String adjustedVariable, String filePath,
			String xFieldName, String yFieldName, String frameTitle,
			String xAxisLabel, String yAxisLabel, String chartTitle) {
		FileAndSceenStream.outputToFile(Util.generateFileName(filePath,
				xFieldName, yFieldName));

		ExperimentParamResultPairCollections resultCollections = LSAUtil
				.getDefaultLSAUtil().iwqeEnergy(adjustedVariable, true);

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

	public void showAndLogLSAEnergy(String adjustedVariable, String filePath,
			String xFieldName, String yFieldName, String frameTitle,
			String xAxisLabel, String yAxisLabel, String chartTitle) {
		FileAndSceenStream.outputToFile(Util.generateFileName(filePath,
				xFieldName, yFieldName));

		ExperimentParamResultPairCollections resultCollections = LSAUtil
				.getDefaultLSAUtil().lsaEnergy(adjustedVariable, true);

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

	public void showAndLogQueryCorrectnessRate(String adjustedVariable,
			String filePath, String xFieldName, String yFieldName,
			String frameTitle, String xAxisLabel, String yAxisLabel,
			String chartTitle) {
		FileAndSceenStream.outputToFile(Util.generateFileName(filePath,
				xFieldName, yFieldName));

		ExperimentParamResultPairCollections resultCollections = LSAUtil
				.getDefaultLSAUtil().energy(adjustedVariable, false); // or true
																		// is
																		// ok!

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

	public void showAndLogIWQEQueryCorrectnessRate(String adjustedVariable,
			String filePath, String xFieldName, String yFieldName,
			String frameTitle, String xAxisLabel, String yAxisLabel,
			String chartTitle) {
		FileAndSceenStream.outputToFile(Util.generateFileName(filePath,
				xFieldName, yFieldName));

		ExperimentParamResultPairCollections resultCollections = LSAUtil
				.getDefaultLSAUtil().iwqeEnergy(adjustedVariable, false); // or
																			// true
																			// is
																			// ok
																			// !

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

	public void showAndLogLSAQueryCorrectnessRate(String adjustedVariable,
			String filePath, String xFieldName, String yFieldName,
			String frameTitle, String xAxisLabel, String yAxisLabel,
			String chartTitle) {
		FileAndSceenStream.outputToFile(Util.generateFileName(filePath,
				xFieldName, yFieldName));

		ExperimentParamResultPairCollections resultCollections = LSAUtil
				.getDefaultLSAUtil().lsaEnergy(adjustedVariable, false); // or
																			// true
																			// is
																			// ok
																			// !

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

	public void showAndLogSuccessRate(String adjustedVariable, String filePath,
			String xFieldName, String yFieldName, String frameTitle,
			String xAxisLabel, String yAxisLabel, String chartTitle) {
		FileAndSceenStream.outputToFile(Util.generateFileName(filePath,
				xFieldName, yFieldName));

		ExperimentParamResultPairCollections resultCollections = LSAUtil
				.getDefaultLSAUtil().energy(adjustedVariable, false);// false is
																		// different

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

}
