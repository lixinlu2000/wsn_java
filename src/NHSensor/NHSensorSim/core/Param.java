package NHSensor.NHSensorSim.core;

public class Param {
	int QUERY_MESSAGE_SIZE = 24;
	double RADIO_RANGE = 30 * Math.sqrt(3);
	double RADIO_A = 45 * 10E-6;
	double RADIO_B = 135 * 10E-6;
	int RADIO_N = 2;
	double RADIO_R = 0.01 * 10E-6;
	double ET;
	double ER;
	int ANSWER_SIZE = 488;
	// double INIT_ENERGY = 5000;
	// do ERISC Experiment
	double INIT_ENERGY = 500000;
	double frameSize = 100;
	double preambleLength = 0;
	double radioSpeed = 40*1024;//kbps

	public Param() {
		computeE();
	}

	public Param(double radioRange) {
		this.RADIO_RANGE = radioRange;
		computeE();
	}

	protected void computeE() {
		ET = RADIO_A + RADIO_R * Math.pow(RADIO_RANGE, RADIO_N);
		ER = RADIO_B;
	}

	public double getTEnergy(Message msg, double radioRange) {
		return (RADIO_A + RADIO_R * Math.pow(radioRange, RADIO_N))
				* msg.getTotalSize() * 8;
	}

	public double getTEnergy(int messageSize, double radioRange) {
		return (RADIO_A + RADIO_R * Math.pow(radioRange, RADIO_N))
				* messageSize * 8;

	}

	public double getREnergy(Message msg) {
		return RADIO_B * msg.getTotalSize() * 8;
	}

	public double getREnergy(int messageSize) {
		return RADIO_B * messageSize * 8;
	}

	public double getER() {
		return ER;
	}

	public void setER(double er) {
		ER = er;
	}

	public double getET() {
		return ET;
	}

	public void setET(double et) {
		ET = et;
	}

	public int getQUERY_MESSAGE_SIZE() {
		return QUERY_MESSAGE_SIZE;
	}

	public void setQUERY_MESSAGE_SIZE(int query_message_size) {
		this.QUERY_MESSAGE_SIZE = query_message_size;
	}

	public double getRADIO_A() {
		return RADIO_A;
	}

	public void setRADIO_A(double radio_a) {
		RADIO_A = radio_a;
	}

	public double getRADIO_B() {
		return RADIO_B;
	}

	public void setRADIO_B(double radio_b) {
		RADIO_B = radio_b;
	}

	public int getRADIO_N() {
		return RADIO_N;
	}

	public void setRADIO_N(int radio_n) {
		RADIO_N = radio_n;
	}

	public double getRADIO_R() {
		return RADIO_R;
	}

	public void setRADIO_R(double radio_r) {
		RADIO_R = radio_r;
	}

	public double getRADIO_RANGE() {
		return RADIO_RANGE;
	}

	public void setRADIO_RANGE(double radio_range) {
		RADIO_RANGE = radio_range;
		this.computeE();
	}

	public int getANSWER_SIZE() {
		return ANSWER_SIZE;
	}

	public void setANSWER_SIZE(int answer_size) {
		this.ANSWER_SIZE = answer_size;
	}

	public double getFrameSize() {
		return frameSize;
	}

	public void setFrameSize(double frameSize) {
		this.frameSize = frameSize;
	}

	public double getPreambleLength() {
		return preambleLength;
	}

	public void setPreambleLength(double preambleLength) {
		this.preambleLength = preambleLength;
	}

	public double getFramePayloadSize() {
		return this.frameSize - this.preambleLength;
	}

	public double getRadioSpeed() {
		return radioSpeed;
	}

	public void setRadioSpeed(double radioSpeed) {
		this.radioSpeed = radioSpeed;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Param{");
		sb.append("QUERY_MESSAGE_SIZE(");
		sb.append(this.QUERY_MESSAGE_SIZE);
		sb.append(")");
		sb.append(",");
		sb.append("RADIO_RANGE(");
		sb.append(this.RADIO_RANGE);
		sb.append(")");
		sb.append("}");
		return sb.toString();
	}

	public double getINIT_ENERGY() {
		return INIT_ENERGY;
	}

	public void setINIT_ENERGY(double init_energy) {
		INIT_ENERGY = init_energy;
	}

}
