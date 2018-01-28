package NHSensor.NHSensorSim.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class ScatterPlotDemo extends ApplicationFrame {

	public ScatterPlotDemo(String title) {

		super(title);

		XYSeriesCollection dataset = new XYSeriesCollection();

		XYSeries series1 = new XYSeries("Series 1");
		series1.add(1.0, 4.5);
		series1.add(4.4, 3.2);
		dataset.addSeries(series1);

		XYSeries series2 = new XYSeries("Series 2");
		series2.add(3.2, 8.5);
		series2.add(4.9, 3.7);
		dataset.addSeries(series2);
		JFreeChart chart = ChartFactory.createScatterPlot("Scatter Plot Demo", // title
				"X", "Y", // axis labels
				dataset, // dataset
				PlotOrientation.VERTICAL, true, // legend? yes
				true, // tooltips? yes
				false // URLs? no
				);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		setContentPane(chartPanel);

	}

	public static void main(String[] args) {
		ScatterPlotDemo demo = new ScatterPlotDemo("Scatter Plot Demo");
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
	}

}
