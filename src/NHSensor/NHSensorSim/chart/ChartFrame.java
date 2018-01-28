package NHSensor.NHSensorSim.chart;

import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.analysis.UnivariateRealFunction;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import NHSensor.NHSensorSim.experiment.ExperimentParamResultPairCollections;
import NHSensor.NHSensorSim.util.MatlabClient;
import NHSensor.NHSensorSim.util.Util;

public class ChartFrame {
	private static boolean useMatlab = false;

	public static boolean isUseMatlab() {
		return useMatlab;
	}

	public ChartFrame() {
	}

	public static void showDataset(XYDataset dataset, String frameTitle,
			String xAxisLabel, String yAxisLabel, String chartTitle) {
		if (!ChartFrame.isUseMatlab()) {
			JFreeChartFrame jFreeChartFrame = new JFreeChartFrame(dataset,
					frameTitle, xAxisLabel, yAxisLabel, chartTitle);
			jFreeChartFrame.showFrame();
		} else {
			MatlabClient matlabClient = new MatlabClient();
			try {
				String programme = Util.xYDatasetToMatlabPlotProgramme(dataset,
						"x", "", xAxisLabel, yAxisLabel);
				System.out.println(programme);
				matlabClient.eval(programme);
				matlabClient.eval(MatlabClient.BYE);
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}

	public static void showDatasetByPlot(XYDataset dataset, String frameTitle,
			String xAxisLabel, String yAxisLabel, String chartTitle) {
		JFreeChart chart = ChartFactory.createScatterPlot(frameTitle,
				xAxisLabel, yAxisLabel, dataset, PlotOrientation.VERTICAL,
				true, // legend? yes
				true, // tooltips? yes
				false // URLs? no
				);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		ApplicationFrame af = new ApplicationFrame("hello");
		af.setContentPane(chartPanel);
		af.pack();
		RefineryUtilities.centerFrameOnScreen(af);
		af.setVisible(true);

	}

	/*
	 * fix the value of the field xFieldName and calculate the average value of
	 * the field yFieldName
	 */
	public static void showParamResultPairCollections(
			ExperimentParamResultPairCollections paramResultPairCollections,
			String xFieldName, String yFieldName, String frameTitle,
			String xAxisLabel, String yAxisLabel, String chartTitle) {
		ChartFrame.showParamResultPairCollections(paramResultPairCollections,
				xFieldName, yFieldName, frameTitle, xAxisLabel, yAxisLabel,
				chartTitle, true);
	}

	/*
	 * fix the value of the field xFieldName and calculate the average value of
	 * the field yFieldName
	 */
	public static void showParamResultPairCollections(
			ExperimentParamResultPairCollections paramResultPairCollections,
			String xFieldName, String yFieldName, String frameTitle,
			String xAxisLabel, String yAxisLabel, String chartTitle,
			boolean filterNonSuccessed) {
		try {
			XYDataset dataset = paramResultPairCollections.generateXYDataset(
					xFieldName, yFieldName, filterNonSuccessed);
			ChartFrame.showDataset(dataset, frameTitle, xAxisLabel, yAxisLabel,
					chartTitle);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * fix the value of the field xFieldName and calculate a value of the field
	 * yFieldName to minimize the value of field minimizeeFieldName
	 */
	public static void showParamResultPairCollections(
			ExperimentParamResultPairCollections paramResultPairCollections,
			String xFieldName, String yFieldName, String minimizeFieldName,
			String frameTitle, String xAxisLabel, String yAxisLabel,
			String chartTitle) {
		ChartFrame.showParamResultPairCollections(paramResultPairCollections,
				xFieldName, yFieldName, minimizeFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle, true);
	}

	/*
	 * fix the value of the field xFieldName and calculate a value of the field
	 * yFieldName to minimize the value of field minimizeeFieldName
	 */
	public static void showParamResultPairCollections(
			ExperimentParamResultPairCollections paramResultPairCollections,
			String xFieldName, String yFieldName, String minimizeFieldName,
			String frameTitle, String xAxisLabel, String yAxisLabel,
			String chartTitle, boolean filterNonSuccessed) {
		try {
			XYDataset dataset = paramResultPairCollections.generateXYDataset(
					xFieldName, yFieldName, minimizeFieldName,
					filterNonSuccessed);
			ChartFrame.showDataset(dataset, frameTitle, xAxisLabel, yAxisLabel,
					chartTitle);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void showUnivariateRealFunction(UnivariateRealFunction f,
			double min, double max, double step) {
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries xYSeries = new XYSeries(f.toString());
		dataset.addSeries(xYSeries);
		double x, y = 0;
		for (x = min; x <= max; x += step) {
			try {
				y = f.value(x);
			} catch (FunctionEvaluationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			xYSeries.add(x, y);
		}
		ChartFrame.showDataset(dataset, f.toString(), "x", "y", f.toString());
	}

}
