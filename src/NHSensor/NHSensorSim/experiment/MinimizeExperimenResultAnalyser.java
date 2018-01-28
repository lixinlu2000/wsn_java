package NHSensor.NHSensorSim.experiment;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/*
 * 
 */
public class MinimizeExperimenResultAnalyser extends ExperimenResultAnalyser {
	String xFieldName;
	String yFieldName;
	String minimizeFieldName;

	public MinimizeExperimenResultAnalyser(
			ExperimentParamResultPairCollections pairCollections,
			String xFieldName, String yFieldName, String minimizeFieldName) {
		this(pairCollections, xFieldName, yFieldName, minimizeFieldName, true);
	}

	public MinimizeExperimenResultAnalyser(
			ExperimentParamResultPairCollections pairCollections,
			String xFieldName, String yFieldName, String minimizeFieldName,
			boolean filterNonSuccessed) {
		super(pairCollections, filterNonSuccessed);
		this.xFieldName = xFieldName;
		this.yFieldName = yFieldName;
		this.minimizeFieldName = minimizeFieldName;
	}

	public String getMinimizeFieldName() {
		return minimizeFieldName;
	}

	public void setMinimizeFieldName(String minimizeFieldName) {
		this.minimizeFieldName = minimizeFieldName;
	}

	public XYDataset generateXYDataset() throws Exception {
		XYSeriesCollection dataset = new XYSeriesCollection();
		ExperimentParamResultPairCollection pairCollection;
		XYSeries xYSeries;

		for (int i = 0; i < this.pairCollections.size(); i++) {
			pairCollection = this.pairCollections.get(i);
			xYSeries = pairCollection.generateXYSeries(xFieldName, yFieldName,
					minimizeFieldName, this.isFilterNonSuccessed());
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
