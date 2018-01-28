package NHSensor.NHSensorSim.chart;

import java.awt.Color;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

import NHSensor.NHSensorSim.experiment.ExperimentParamResultPairCollections;

public class JFreeChartFrame extends ApplicationFrame {
	private XYDataset dataset;
	private String chartTitle;
	private String xAxisLabel;
	private String yAxisLabel;
	private int width = 500;
	private int height = 300;

	public JFreeChartFrame(XYDataset dataset, String title, String xAxisLabel,
			String yAxisLabel, String chartTitle) {
		super(title);
		this.dataset = dataset;
		this.xAxisLabel = xAxisLabel;
		this.yAxisLabel = yAxisLabel;
		this.chartTitle = chartTitle;
		ChartPanel chartPanel = (ChartPanel) createChartPanel();
		chartPanel.setPreferredSize(new java.awt.Dimension(this.width,
				this.height));
		chartPanel.setMouseZoomable(true, false);
		setContentPane(chartPanel);
	}

	private JFreeChart createChart() {

		JFreeChart chart = ChartFactory.createXYLineChart(this.chartTitle, // title
				this.xAxisLabel, // x-axis label
				this.yAxisLabel, // y-axis label
				this.dataset, // data
				PlotOrientation.VERTICAL, true, // create legend?
				true, // generate tooltips?
				false // generate URLs?
				);

		chart.setBackgroundPaint(Color.white);

		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}
		return chart;

	}

	public JPanel createChartPanel() {
		JFreeChart chart = createChart();
		return new ChartPanel(chart);
	}

	public void showFrame() {
		this.pack();
		RefineryUtilities.centerFrameOnScreen(this);
		this.setVisible(true);

	}

	public XYDataset getDataset() {
		return dataset;
	}

	public void setDataset(XYDataset dataset) {
		this.dataset = dataset;
	}

	public static void showDataset(XYDataset dataset, String frameTitle,
			String xAxisLabel, String yAxisLabel, String chartTitle) {
		JFreeChartFrame chartFrame = new JFreeChartFrame(dataset, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
		chartFrame.showFrame();
	}

	public static void showParamResultPairCollections(
			ExperimentParamResultPairCollections paramResultPairCollections,
			String xFieldName, String yFieldName, String frameTitle,
			String xAxisLabel, String yAxisLabel, String chartTitle) {
		JFreeChartFrame.showParamResultPairCollections(
				paramResultPairCollections, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle, true);
	}

	public static void showParamResultPairCollections(
			ExperimentParamResultPairCollections paramResultPairCollections,
			String xFieldName, String yFieldName, String frameTitle,
			String xAxisLabel, String yAxisLabel, String chartTitle,
			boolean filterNonSuccessed) {
		try {
			XYDataset dataset = paramResultPairCollections.generateXYDataset(
					xFieldName, yFieldName, filterNonSuccessed);
			JFreeChartFrame.showDataset(dataset, frameTitle, xAxisLabel,
					yAxisLabel, chartTitle);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
