package NHSensor.NHSensorSim.routing.gpsr;

import java.util.Vector;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Attachment;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.events.BroadcastEvent;
import NHSensor.NHSensorSim.events.ForwardToPointEvent;
import NHSensor.NHSensorSim.events.RadioEvent;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.util.Util;

public class GPSR extends ForwardToPointEvent {
	static Logger logger = Logger.getLogger(GPSR.class);

	public static final int GREEDY = 0;
	public static final int PERI = 1;
	// Position destPos;
	Vector path = new Vector();
	GPSRAttachment lastNode = null;
	GPSRAttachment cur = null;
	GPSRAttachment next = null;
	Vector periPath = new Vector();

	public GPSR(GPSRAttachment root, Position dest, Message message,
			Algorithm alg) {
		super(message, alg);
		this.root = root;
		this.shape = dest;
//		System.out.println("This is GPSR.");
	}

	public GPSR(Message message, Algorithm alg) {
		super(message, alg);
	}

	public boolean detectLoop() {
		boolean innerPeriLoop = false;
		for (int i = 0; i < this.periPath.size() - 1; i++) {
			Attachment p = (Attachment) (this.periPath.elementAt(i));
			Attachment n = (Attachment) (this.periPath.elementAt(i + 1));
			if (this.cur.getNode().getId() == p.getNode().getId()
					&& this.next.getNode().getId() == n.getNode().getId()) {
				innerPeriLoop = true;
				break;
			}
		}
		return innerPeriLoop;

	}

	public void handle() throws SensornetBaseException {
		this.setSuccess(false);
		double bearing;
		this.cur = (GPSRAttachment) this.getRoot();
		this.path.add(cur);
		this.next = null;
		GPSRAttachment lastNext = null;
		int mode = GPSR.GREEDY;
		GPSRAttachment prevAttachment = null;
		Position firstPeriPosition = null;

		Message msg = new Message(this.getAlg().getParam()
				.getQUERY_MESSAGE_SIZE(), null);
		RadioEvent event;

		Position curPos, nextPos;
		Position crossPosition = new Position();
		boolean isCross = false;

		logger.debug("start");
		logger.debug("forward to");
		logger.debug(String.valueOf(cur.getNode().getId()));
		while (!cur.getNode().getPos().equals(this.getDest())) {
			// if(path.size()==38)return;
			switch (mode) {
			case GPSR.GREEDY:
				next = cur.getNextGPSRAttachmentToDest(this.getDest());
				if (cur == next) {
					logger.debug("cur.findFirstCCW(bearing,null)");
					mode = GPSR.PERI;
					bearing = cur.getNode().getPos().bearing(this.getDest());
					next = cur.findFirstCCW(bearing, null);
					if (next == cur) {
						logger.debug("cannot findFirstCCW");
						this.setLastNode(cur);
						return;
					}
					// broadcast
					event = new BroadcastEvent(cur, next, msg, this.getAlg());
					event.setParent(this);
					this.getAlg().getSimulator().handle(event);
					logger.debug("find first ccw success");
					logger.debug("greedy to");
					logger.debug(String.valueOf(next.getNode().getId()));
					firstPeriPosition = cur.getNode().getPos();
					periPath.clear();
					periPath.add(cur);
					periPath.add(next);
					cur = next;
					path.add(next);
				} else {
					logger.debug("forward to");
					logger.debug(String.valueOf(next.getNode().getId()));
					event = new BroadcastEvent(cur, next, msg, this.getAlg());
					event.setParent(this);
					this.getAlg().getSimulator().handle(event);
					cur = next;
					// broadcast
					path.add(next);

				}
				break;
			case GPSR.PERI:
				// change to greedy mode
				// next = cur.getNextGPSRAttachmentToDest(this.getDestPos());
				if (cur.getNode().getPos().distance(this.getDest()) < firstPeriPosition
						.distance(this.getDest())) {
					logger.debug("greedy change to forward");
					mode = GPSR.GREEDY;
					// firstPeriAttachment = null;
					firstPeriPosition = null;
					periPath.clear();
					continue;
				} else {
					logger.debug("continue greedy");
					prevAttachment = (GPSRAttachment) periPath
							.elementAt(periPath.size() - 2);
					bearing = cur.getNode().getPos().bearing(
							prevAttachment.getNode().getPos());
					next = cur.findFirstCCW(bearing, prevAttachment);

					if (cur == next) {
						this.setLastNode(cur);
						logger.debug("greedy cannot find first ccw");
						return;
					}

					if (this.detectLoop()) {
						// fix loop
						System.out.println("inner peri loop");
						bearing = cur.getNode().getPos()
								.bearing(this.getDest());
						next = cur.findFirstCCW(bearing, null);
						if (next == cur || this.detectLoop()) {
							logger.debug("cannot fix inner peri loop");
							this.setLastNode(cur);
							return;
						} else {
							logger.debug("fix inner peri loop");
							logger
									.debug(String.valueOf(next.getNode()
											.getId()));
							// broadcast
							event = new BroadcastEvent(cur, next, msg, this
									.getAlg());
							event.setParent(this);
							this.getAlg().getSimulator().handle(event);
							firstPeriPosition = cur.getNode().getPos();
							periPath.clear();
							periPath.add(cur);
							periPath.add(next);
							cur = next;
							path.add(next);

						}
					} else {
						logger.debug("check more close ccw");
						lastNext = null;
						curPos = cur.getNode().getPos();
						nextPos = next.getNode().getPos();
						crossPosition = (Position) firstPeriPosition.clone();
						isCross = Util.crossSegment(curPos.getX(), curPos
								.getY(), nextPos.getX(), nextPos.getY(),
								firstPeriPosition.getX(), firstPeriPosition
										.getY(), this.getDest().getX(), this
										.getDest().getY(), crossPosition);
						while (isCross
								&& (crossPosition.distance(this.getDest()) < firstPeriPosition
										.distance(this.getDest()))) {
							logger.debug("cross");
							lastNext = next;
							bearing = curPos.bearing(nextPos);
							next = cur.findFirstCCW(bearing, lastNext);
							if (next == cur) {
								this.setLastNode(cur);
								logger.debug("cannot findFirstCCW");
								return;
							}
							nextPos = next.getNode().getPos();
							firstPeriPosition = crossPosition;
							isCross = Util.crossSegment(curPos.getX(), curPos
									.getY(), nextPos.getX(), nextPos.getY(),
									firstPeriPosition.getX(), firstPeriPosition
											.getY(), this.getDest().getX(),
									this.getDest().getY(), crossPosition);
						}
						//
						if (lastNext == null) {
							logger.debug("not cross");
							logger
									.debug(String.valueOf(next.getNode()
											.getId()));
							path.add(next);
							periPath.add(next);
							// broadcast
							event = new BroadcastEvent(cur, next, msg, this
									.getAlg());
							event.setParent(this);
							this.getAlg().getSimulator().handle(event);
							cur = next;
						} else {
							logger.debug("cross find one");
							logger.debug("greedy to");
							logger
									.debug(String.valueOf(next.getNode()
											.getId()));
							periPath.clear();
							periPath.add(lastNext);
							periPath.add(cur);
							periPath.add(next);
							event = new BroadcastEvent(cur, next, msg, this
									.getAlg());
							event.setParent(this);
							this.getAlg().getSimulator().handle(event);
							firstPeriPosition = crossPosition;
							cur = next;
						}
					}
				}
				break;
			}
		}
		this.setLastNode(cur);
		this.setSuccess(true);
		this.setOutput(path);
	}

	public static int getPERI() {
		return PERI;
	}

	public Vector getPath() {
		return path;
	}

	public void setLastNode(GPSRAttachment lastNode) {
		this.lastNode = lastNode;
	}
}
