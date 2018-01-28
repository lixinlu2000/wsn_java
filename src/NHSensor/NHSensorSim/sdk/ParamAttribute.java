package NHSensor.NHSensorSim.sdk;

public class ParamAttribute {
	String name;
	String type;
	String defaultValue; //default value
	String begin;
	String end;
	String step;
	boolean adjustable = false;
	boolean alwaysAdjustable = false; //like networkID
	
	public ParamAttribute(String name, String type, String defaultValue,
			String begin, String end, String step, boolean adjustable) {
		super();
		this.name = name;
		this.type = type;
		this.defaultValue = defaultValue;
		this.begin = begin;
		this.end = end;
		this.step = step;
		this.adjustable = adjustable;
	}

	public ParamAttribute(String name, String type, String defaultValue,
			String begin, String end, String step, boolean adjustable,
			boolean alwaysAdjustable) {
		super();
		this.name = name;
		this.type = type;
		this.defaultValue = defaultValue;
		this.begin = begin;
		this.end = end;
		this.step = step;
		this.adjustable = adjustable;
		this.alwaysAdjustable = alwaysAdjustable;
	}

	public ParamAttribute(String name, String type, String defaultValue) {
		super();
		this.name = name;
		this.type = type;
		this.defaultValue = defaultValue;
		this.adjustable = false;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public boolean isAdjustable() {
		return adjustable;
	}

	public void setAdjustable(boolean adjustable) {
		this.adjustable = adjustable;
	}
	
	public boolean isAlwaysAdjustable() {
		return alwaysAdjustable;
	}

	public void setAlwaysAdjustable(boolean alwaysAdjustable) {
		this.alwaysAdjustable = alwaysAdjustable;
	}

	public String getSequenceClassName() {
		if(this.getType()=="double") return "DoubleSequence";
		else if(this.getType()=="int") return "IntSequence";
		else return null;
	}
}
