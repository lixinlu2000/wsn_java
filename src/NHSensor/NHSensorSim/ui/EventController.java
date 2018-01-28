package NHSensor.NHSensorSim.ui;

import org.apache.log4j.Logger;

public class EventController implements Runnable {
	static Logger logger = Logger.getLogger(EventController.class);
	SensornetModel sensornetModel;
	Boolean started = new Boolean(false);
	double speed = 1;

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public EventController(SensornetModel sensornetModel) {
		this.sensornetModel = sensornetModel;
	}

	public synchronized boolean previous(int step) throws Exception {
		this.sensornetModel.movePrevious(step);
		return true;
	}

	public synchronized boolean next(int step) throws Exception {
		this.sensornetModel.moveNext(step);
		return true;
	}

	public void run() {
		if (!this.getStarted().booleanValue()) {
			try {
				synchronized (this) {
					this.wait();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		}

		logger.debug("event controller is running");
		while (true) {
			if (this.sensornetModel.getEventsDatabase().isEnd()) {
				this.setStarted(false);
			}

			if (!this.getStarted().booleanValue()) {
				try {
					synchronized (this) {
						this.wait();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
			}

			// events.clear();
			try {
				this.next(1);
				Thread.sleep((int) (1000 * this.getSpeed()));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// events.clear();
			// sensornetModel.setEvents(events);

		}
	}

	public Boolean getStarted() {
		return started;
	}

	public synchronized void setStarted(boolean started) {
		this.started = new Boolean(started);
		if (this.started.booleanValue()) {
			this.notify();
		}
	}
}
