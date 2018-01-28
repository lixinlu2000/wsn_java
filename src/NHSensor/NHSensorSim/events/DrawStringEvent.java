package NHSensor.NHSensorSim.events;

import java.awt.Color;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Position;

public class DrawStringEvent extends Event {
	private Position pos;
	private String string;
	private Color color;
	private double xMove = 0;
	private double yMove = 0;

	public DrawStringEvent(Algorithm alg, Position pos, String string,
			Color color) {
		super(alg);
		this.pos = pos;
		this.string = string;
		this.color = color;
	}

	public DrawStringEvent(Algorithm alg, Position pos, String string,
			Color color, double xMove, double yMove) {
		super(alg);
		this.pos = pos;
		this.string = string;
		this.color = color;
		this.xMove = xMove;
		this.yMove = yMove;
	}


	public void handle() throws SensornetBaseException {
		// TODO Auto-generated method stub
	}

	public Position getPos() {
		return pos;
	}


	public void setPos(Position pos) {
		this.pos = pos;
	}


	public Color getColor() {
		return color;
	}


	public void setColor(Color color) {
		this.color = color;
	}

	public double getxMove() {
		return xMove;
	}

	public void setxMove(double xMove) {
		this.xMove = xMove;
	}

	public double getyMove() {
		return yMove;
	}

	public void setyMove(double yMove) {
		this.yMove = yMove;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}


	public String toString() {
		return "DrawStringEvent  " + this.string;
	}
}
