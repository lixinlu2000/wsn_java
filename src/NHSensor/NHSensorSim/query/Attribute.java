package NHSensor.NHSensorSim.query;

public class Attribute {
	String name;

	public Attribute(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object clone() {
		return new Attribute(this.getName());
	}

	public boolean equals(Attribute attribute) {
		return this.name.equals(attribute.getName());
	}
}
