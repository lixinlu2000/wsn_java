package NHSensor.NHSensorSim.end;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.cds.CDSAttachment;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.events.ChangeLinkColorInfoEvent;
import NHSensor.NHSensorSim.events.ChildChangeLinkColorInfoEvent;
import NHSensor.NHSensorSim.exception.SensornetBaseException;

public class EndAttachment extends CDSAttachment {
	StartEndObject startEndObject = new StartEndObject();
	Vector startEndObjects;
	Hashtable linkColors;
	boolean isLinkColorsInitialized = false;
	Hashtable childStartEndObjects = new Hashtable();

	public EndAttachment(Node node, Algorithm algorithm, double energy,
			double radioRange) {
		super(node, algorithm, energy, radioRange);
	}

	public int getStart() {
		return startEndObject.getStart();
	}

	public int getEnd() {
		return startEndObject.getEnd();
	}

	public void setStartEndObject(StartEndObject startEndObject) {
		this.startEndObject = startEndObject;
	}

	public Hashtable getChildStartEndObjects() {
		return childStartEndObjects;
	}

	public StartEndObject getStartEndObject() {
		return this.startEndObject;
	}

	public boolean isAllStartEndObjectsReceived() {
		return this.childStartEndObjects.keySet().size() == this.getChilds()
				.size();
	}

	public void initLinkColors() {
		if (this.isLinkColorsInitialized)
			return;
		EndAttachment child;

		linkColors = new Hashtable();
		if (this.getParent() != null) {
			linkColors.put(this.getParent(), new Integer(this.getChilds()
					.size()));
		}
		for (int i = 0; i < this.getChilds().size(); i++) {
			child = (EndAttachment) this.getChilds().elementAt(i);
			linkColors.put(child, new Integer(i));
		}
		this.isLinkColorsInitialized = true;
	}

	protected void caculateStartEndObjects() {
		Vector friends = this.getFriends();
		EndAttachment na;
		startEndObjects = new Vector();

		for (Iterator it = friends.iterator(); it.hasNext();) {
			na = (EndAttachment) it.next();
			this.startEndObjects.add(new StartEndObject(na.getStart(), na
					.getEnd()));
		}
	}

	public Vector getStartEndObjects() {
		if (this.startEndObjects == null)
			this.caculateStartEndObjects();
		return this.startEndObjects;
	}

	public Vector getStartEndObjectsRemoveMyChild() {
		Vector result = new Vector();
		result.addAll(this.getStartEndObjects());
		return this.removeMyChild(this.getStartEndObject(), result);
	}

	public Vector getStartEndObjectsRemoveMyChildAndWhichHasParent() {
		Vector result = new Vector();
		result.addAll(this.getStartEndObjects());
		result = this.removeMyChild(this.getStartEndObject(), result);
		return this.removeWhichHasParent(result);
	}

	protected Vector removeWhichHasParent(Vector startEndObjects) {
		Vector result = new Vector();
		StartEndObject seo1, seo2;
		boolean notChild;
		for (int i = 0; i < startEndObjects.size(); i++) {
			seo1 = (StartEndObject) startEndObjects.elementAt(i);
			notChild = true;
			for (int j = 0; j < result.size(); j++) {
				seo2 = (StartEndObject) result.elementAt(j);
				if (seo2.isParentOf(seo1)) {
					notChild = false;
					break;
				}
			}
			if (notChild)
				result.add(seo1);
		}
		return result;
	}

	protected Vector removeMyChild(StartEndObject me, Vector startEndObjects) {
		Vector result = new Vector();
		StartEndObject seo1;
		for (int i = 0; i < startEndObjects.size(); i++) {
			seo1 = (StartEndObject) startEndObjects.elementAt(i);
			if (!me.isParentOf(seo1)) {
				result.add(seo1);
			}
		}
		return result;
	}

	public Vector getStartEndObjectsSentAll() {
		Vector startEndObjects = new Vector();
		startEndObjects.addAll(this.getStartEndObjects());
		EndAttachment na;

		if (this.isLeaf())
			return startEndObjects;
		for (Iterator it = this.getChilds().iterator(); it.hasNext();) {
			na = (EndAttachment) it.next();
			startEndObjects.addAll(na.getStartEndObjectsSentAll());
		}
		return startEndObjects;
	}

	public Vector getStartEndObjectsSentRemoveChild() {
		Vector startEndObjects = new Vector();
		startEndObjects.addAll(this.getStartEndObjectsRemoveMyChild());
		EndAttachment na;

		if (this.isLeaf())
			return startEndObjects;
		for (Iterator it = this.getChilds().iterator(); it.hasNext();) {
			na = (EndAttachment) it.next();
			startEndObjects.addAll(na.getStartEndObjectsSentRemoveChild());
		}
		return this.removeMyChild(this.getStartEndObject(), startEndObjects);
	}

	public Vector getStartEndObjectsSentRemoveChildAndWhichHasParent() {
		Vector startEndObjects = new Vector();
		startEndObjects.addAll(this
				.getStartEndObjectsRemoveMyChildAndWhichHasParent());
		EndAttachment na;

		if (this.isLeaf())
			return startEndObjects;
		for (Iterator it = this.getChilds().iterator(); it.hasNext();) {
			na = (EndAttachment) it.next();
			startEndObjects.addAll(na
					.getStartEndObjectsSentRemoveChildAndWhichHasParent());
		}
		Vector result = this.removeMyChild(this.getStartEndObject(),
				startEndObjects);
		return this.removeWhichHasParent(result);
	}

	public void caculateStartAndEnd() {
		if (this.getParent() == null) {
			this.setStartEndObject(new StartEndObject(0, this
					.getTreeNodeCount() - 1));
		} else {
			int parentStart = ((EndAttachment) this.getParent()).getStart();
			this.setStartEndObject(new StartEndObject(parentStart + 1
					+ this.formerSiblingAndHisDescendantNodesCount(),
					parentStart + 1
							+ this.formerSiblingAndHisDescendantNodesCount()
							+ this.getTreeNodeCount() - 1));
		}
	}

	public EndAttachment whichChildIsAncestorOf(StartEndObject startEndObject) {
		EndAttachment child;
		for (int i = 0; i < this.getChilds().size(); i++) {
			child = (EndAttachment) this.getChilds().elementAt(i);
			if (child.getStartEndObject().contains(startEndObject))
				return child;
		}
		return null;
	}

	protected void updateLinkColors(EndAttachment child,
			EndAttachment anotherChild) {
		Integer newColor = (Integer) this.linkColors.get(anotherChild);
		Integer oldColor = (Integer) this.linkColors.get(child);
		Integer color;
		Object c;

		for (Enumeration e = this.linkColors.keys(); e.hasMoreElements();) {
			c = e.nextElement();
			color = (Integer) this.linkColors.get(c);
			if (color.intValue() == oldColor.intValue()) {
				this.linkColors.put(c, new Integer(newColor));
				if (c == child) {
					ChangeLinkColorInfoEvent clce = new ChangeLinkColorInfoEvent(
							this.getAlgorithm(), this.getNode(), child
									.getNode(), anotherChild.getNode(),
							oldColor.intValue(), newColor.intValue());
					try {
						this.getAlgorithm().getSimulator().handle(clce);
					} catch (SensornetBaseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					ChildChangeLinkColorInfoEvent cclce = new ChildChangeLinkColorInfoEvent(
							this.getAlgorithm(), this.getNode(),
							((EndAttachment) c).getNode(), anotherChild
									.getNode(), oldColor.intValue(), newColor
									.intValue(), child.getNode());
					try {
						this.getAlgorithm().getSimulator().handle(cclce);
					} catch (SensornetBaseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
	}

	public Vector getStartEndObjectsReceivedRemoveChild() {
		Vector startEndObjects = new Vector();
		EndAttachment na;

		if (this.isLeaf())
			return startEndObjects;
		for (Iterator it = this.getChilds().iterator(); it.hasNext();) {
			na = (EndAttachment) it.next();
			startEndObjects.addAll(na.getStartEndObjectsSentRemoveChild());
		}
		return startEndObjects;
	}

	public void caculateLinkColors() {
		Vector childStartEndObjects;
		EndAttachment child, anotherChild;
		StartEndObject startEndObject;

		if (this.isLeaf())
			return;
		for (int i = 0; i < this.getChilds().size(); i++) {
			child = (EndAttachment) this.getChilds().elementAt(i);
			childStartEndObjects = child.getStartEndObjectsSentRemoveChild();
			for (int j = 0; j < childStartEndObjects.size(); j++) {
				startEndObject = (StartEndObject) childStartEndObjects
						.elementAt(j);
				anotherChild = this.whichChildIsAncestorOf(startEndObject);
				if (anotherChild == null) {
					this.updateLinkColors(child, (EndAttachment) this
							.getParent());
				} else {
					this.updateLinkColors(child, anotherChild);
				}
			}
		}
	}

	public boolean detectWhetherOrNotVertex() {
		Integer color = null;
		Integer otherColor;
		this.caculateLinkColors();

		for (Iterator it = linkColors.values().iterator(); it.hasNext();) {
			if (color == null) {
				color = (Integer) it.next();
				continue;
			} else {
				otherColor = (Integer) it.next();
				if (otherColor.intValue() != color.intValue())
					return true;
			}
		}
		return false;
	}
}
