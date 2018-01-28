package NHSensor.NHSensorSim.chart;

import org.jfree.data.xy.XYSeries;

public class XYColumnSeries extends XYSeries {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String xColumnName;
	private String yColumnName;
	private String description = "";

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public XYColumnSeries(Comparable key) {
		super(key);
	}

	public XYColumnSeries(Comparable key, String xColumnName, String yColumnName) {
		super(key);
		this.xColumnName = xColumnName;
		this.yColumnName = yColumnName;
	}

	public String getXColumnName() {
		return xColumnName;
	}

	public void setXColumnName(String columnName) {
		xColumnName = columnName;
	}

	public String getYColumnName() {
		return yColumnName;
	}

	public void setYColumnName(String columnName) {
		yColumnName = columnName;
	}
}
