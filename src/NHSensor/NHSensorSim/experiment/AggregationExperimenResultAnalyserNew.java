package NHSensor.NHSensorSim.experiment;

import java.util.Hashtable;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import NHSensor.NHSensorSim.analyser.AggregationAnalyser;
import NHSensor.NHSensorSim.util.FormatUtil;

public class AggregationExperimenResultAnalyserNew extends
		ExperimenResultAnalyser {
	String xFieldName;
	String yFieldName;

	public AggregationExperimenResultAnalyserNew(
			ExperimentParamResultPairCollections pairCollections,
			String xFieldName, String yFieldName) {
		this(pairCollections, xFieldName, yFieldName, true);
		// this.pretreatment();
	}

	public AggregationExperimenResultAnalyserNew(
			ExperimentParamResultPairCollections pairCollections,
			String xFieldName, String yFieldName, boolean filterNonSuccessed) {
		super(pairCollections, filterNonSuccessed);
		this.xFieldName = xFieldName;
		this.yFieldName = yFieldName;
		// this.pretreatment();
	}

	public XYSeries generateXYSeries(
			ExperimentParamResultPairCollection pairCollection)
			throws Exception {
		String seriesName = pairCollection.getName();
		XYSeries xYSeries = new XYSeries(seriesName);

		AggregationAnalyser aggregationAnalyser = new AggregationAnalyser(
				AggregationAnalyser.AVERAGE, pairCollection, this.xFieldName,
				this.yFieldName, this.isFilterNonSuccessed());
		aggregationAnalyser.analyse();
		Hashtable result = aggregationAnalyser.getResult();
		FormatUtil.addHashtableToXYSeries(xYSeries, result);
		return xYSeries;
	}

	/*
	 * generateXYSeries use AggregationAnalyser.AVERAGE
	 */
	public XYDataset generateXYDataset() throws Exception {
		XYSeriesCollection dataset = new XYSeriesCollection();
		ExperimentParamResultPairCollection pairCollection;
		XYSeries xYSeries;

		for (int i = 0; i < this.pairCollections.size(); i++) {
			pairCollection = this.pairCollections.get(i);
			xYSeries = this.generateXYSeries(pairCollection);
			dataset.addSeries(xYSeries);
		}
		return dataset;
	}

	public String getXFieldName() {
		return xFieldName;
	}

	public void setXFieldName(String fieldName) {
		xFieldName = fieldName;
	}

	public String getYFieldName() {
		return yFieldName;
	}

	public void setYFieldName(String fieldName) {
		yFieldName = fieldName;
	}
}
