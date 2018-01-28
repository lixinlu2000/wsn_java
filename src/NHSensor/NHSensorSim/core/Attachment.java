package NHSensor.NHSensorSim.core;

import java.io.Serializable;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.exception.HasNoEnergyException;
import NHSensor.NHSensorSim.sensor.Scene;
import NHSensor.NHSensorSim.sensor.SensorBoard;

public class Attachment implements Serializable{
	protected Node node;
	protected Algorithm algorithm;
	protected EnergySource energySource;
	protected Object userObject;
	protected SensorBoard sensorBoard;
	protected boolean hasSendAnswer = false;
	protected Scene scene;

	public boolean isHasSendAnswer() {
		return hasSendAnswer;
	}

	public Object sample(String sensorName) throws HasNoEnergyException {
		return this.sensorBoard.sample(sensorName);
	}

	public Object sample(Scene scene, SensorBoard sensorBoard, String sensorName)
			throws HasNoEnergyException {
		return sensorBoard.sample(this, scene, sensorName, this.getNode()
				.getPos(), Time.getCurrentTime());
	}

	public Object sampleByNodeID(Scene scene, SensorBoard sensorBoard,
			String sensorName) throws HasNoEnergyException {
		return sensorBoard.sample(this, scene, sensorName, this.getNode()
				.getId(), Time.getCurrentTime());
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public void setHasSendAnswer(boolean hasSendAnswer) {
		this.hasSendAnswer = hasSendAnswer;
	}

	public Object getUserObject() {
		return userObject;
	}

	public void setUserObject(Object userObject) {
		this.userObject = userObject;
	}

	public void attachSensorBoard(SensorBoard sensorBoard) {
		this.sensorBoard = sensorBoard;
		sensorBoard.setAttachment(this);
	}

	public Attachment(Node node, Algorithm algorithm, double energy) {
		this.node = node;
		this.algorithm = algorithm;
		this.energySource = new EnergySource(energy);
		this.attachSensorBoard(new SensorBoard());
	}

	public Attachment(Node node, Algorithm algorithm, double energy,
			SensorBoard sensorBoard) {
		this.node = node;
		this.algorithm = algorithm;
		this.energySource = new EnergySource(energy);
		this.attachSensorBoard(sensorBoard);
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public Algorithm getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
	}

	public boolean attach() {
		if (this.node.getAttachment(this.getAlgorithm().getName()) != null) {
			System.out.println("has attached");
			return false;
		}
		this.node.getAttachments().put(this.getAlgorithm().getName(), this);
		return true;
	}

	public EnergySource getEnergySource() {
		return energySource;
	}

	public void setEnergySource(EnergySource energySource) {
		this.energySource = energySource;
	}

	public double getEnergy() {
		return this.getEnergySource().getEnergy();
	}

	public void setEnergy(double energy) {
		this.getEnergySource().setEnergy(energy);
	}

	public void consumeEnergy(double e) throws HasNoEnergyException {
		boolean success = this.getEnergySource().consumeEnergy(e);
		if (!success) {
			throw new HasNoEnergyException(this);
		}
	}

	public SensorBoard getSensorBoard() {
		return sensorBoard;
	}

	public void setSensorBoard(SensorBoard sensorBoard) {
		this.sensorBoard = sensorBoard;
	}

	public double getLinkQuality(Attachment other) {
		return this.getAlgorithm().getNetwork().getLinkPRR(this.getNode(),
				other.getNode());
	}

	public double getSendTimes(Attachment other) {
		if (this == other) {
			return 0;
		}
		return this.getAlgorithm().getNetwork().getSendTimes(this.getNode(),
				other.getNode());
	}
	
	public double getOneSendEnergy(Attachment receiver, double messageSize) {
		if(this==receiver)return 0;

		double framePayloadSize = this.getAlgorithm().getParam().getFramePayloadSize();
		double frameNum = Math.ceil(messageSize / framePayloadSize);
		double frameSize = this.getAlgorithm().getParam().getFrameSize();
		double et = this.getAlgorithm().getParam().getTEnergy(
				(int) (frameNum * frameSize),
				this.getAlgorithm().getParam().getRADIO_RANGE());
		return et;
	}

	public double getOneReceiveEnergy(Attachment send, double messageSize) {
		if(this==send)return 0;
		
		double framePayloadSize = this.getAlgorithm().getParam().getFramePayloadSize();
		double frameNum = Math.ceil(messageSize / framePayloadSize);
		double frameSize = this.getAlgorithm().getParam().getFrameSize();

		double er = this.getAlgorithm().getParam().getREnergy(
				(int) (frameNum * frameSize));
		return er;
	}
	
	public double getSendEnergy(Attachment receiver, double messageSize) {
		if(this==receiver)return 0;

		double framePayloadSize = this.getAlgorithm().getParam().getFramePayloadSize();
		double frameNum = Math.ceil(messageSize / framePayloadSize);
		double frameSize = this.getAlgorithm().getParam().getFrameSize();
		double et = this.getAlgorithm().getParam().getTEnergy(
				(int) (frameNum * frameSize),
				this.getAlgorithm().getParam().getRADIO_RANGE());
		return et*this.getSendTimes(receiver);
	}

	public double getReceiveEnergy(Attachment send, double messageSize) {
		if(this==send)return 0;
		
		double framePayloadSize = this.getAlgorithm().getParam().getFramePayloadSize();
		double frameNum = Math.ceil(messageSize / framePayloadSize);
		double frameSize = this.getAlgorithm().getParam().getFrameSize();

		double er = this.getAlgorithm().getParam().getREnergy(
				(int) (frameNum * frameSize));
		return er*send.getSendTimes(this);
	}

	
	public double getSendTimesOld(Attachment other) {
		if (this == other) {
			return 0;
		}
		return 1;
	}
	
	public double getDefaultData() {
		return this.getAlgorithm().getDataset().getValue(this.getNode().getPos(), new Time(0), 0);
	}
	
	public boolean equals(Object obj) {
		Attachment na = (Attachment)obj;
		return this==na||na.getNode().getId()==this.getNode().getId();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("<Attachment>");
		sb.append("<Node>");
		sb.append(this.getNode().getPos());
		sb.append("</Node>");
		sb.append(this.getEnergySource());
		sb.append("</Attachment>");
		return sb.toString();
	}
	
	

	public boolean isAlive() {
		return this.node.isAlive();
	}

	public void destroy() {
		this.node.destroy();
	}

	public boolean isDestroy() {
		return this.node.isDestroy();
	}
}
