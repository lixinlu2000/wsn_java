package NHSensor.NHSensorSim.algorithm;

import java.awt.Color;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.NodeAttachment;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.core.Time;
import NHSensor.NHSensorSim.events.BroadcastEvent;
import NHSensor.NHSensorSim.events.BuildTreeEvent;
import NHSensor.NHSensorSim.events.QueryResultEvent;
import NHSensor.NHSensorSim.events.TreeEvent;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.query.HistogramInfo;
import NHSensor.NHSensorSim.query.HistogramQuery;
import NHSensor.NHSensorSim.query.ValueRange;
import NHSensor.NHSensorSim.sensor.Scene;
import NHSensor.NHSensorSim.sensor.SensorBoard;
import NHSensor.NHSensorSim.shape.Rect;

public class HistogramNaiveAlg extends Algorithm {
	protected TreeEvent treeEvent;
	protected Vector attachmentsWithSameDepth = new Vector();
	protected Scene scene;
	protected SensorBoard sensorBoard;

	public final static String NAME = "HistogramNaive";

	public HistogramNaiveAlg(HistogramQuery query, Network network,
			Simulator simulator, Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
	}

	public HistogramNaiveAlg(HistogramQuery query) {
		super(query);
	}

	public HistogramNaiveAlg(HistogramQuery query, Network network,
			Simulator simulator, Param param, Statistics statistics) {
		super(query, network, simulator, param, HistogramNaiveAlg.NAME,
				statistics);
	}

	public HistogramQuery getHistogramQuery() {
		return (HistogramQuery) this.getQuery();
	}

	public boolean run() {
		try {
			this.regionBroadcast();
			this.regionCollects();

		} catch (SensornetBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		this.getStatistics().setSuccess(true);
		return true;
	}

	protected void calculateAttachmentsWithSameDepth() {
		Vector attachments = this.getTreeEvent().getAns();
		Vector sameDepthAttachments;
		NodeAttachment nodeAttachment;
		int depth;

		for (int i = 0; i < attachments.size(); i++) {
			nodeAttachment = (NodeAttachment) attachments.elementAt(i);
			depth = nodeAttachment.getHopCount();
			if (depth >= this.attachmentsWithSameDepth.size()) {
				this.attachmentsWithSameDepth.setSize(depth + 1);
			}
			sameDepthAttachments = (Vector) this.attachmentsWithSameDepth
					.elementAt(depth);
			if (sameDepthAttachments == null) {
				sameDepthAttachments = new Vector();
			}
			sameDepthAttachments.add(nodeAttachment);
			this.attachmentsWithSameDepth.set(depth, sameDepthAttachments);
		}
	}

	public void regionBroadcast() throws SensornetBaseException {
		Rect rect = this.getNetwork().getRect();
		Message queryMessage = new Message(param.getQUERY_MESSAGE_SIZE(), query);

		Node node = query.getOrig();
		NodeAttachment root = (NodeAttachment) node.getAttachment(this
				.getName());
		BuildTreeEvent bte = new BuildTreeEvent(root, rect, queryMessage, this);
		this.getSimulator().handle(bte);
		this.setTreeEvent(bte);
		this.calculateAttachmentsWithSameDepth();
	}

	public void regionCollects() throws SensornetBaseException {
		HistogramInfo currentEpochHistogramInfo;
		QueryResultEvent qre;
		Color[] colors = { Color.red, Color.blue, Color.green, Color.yellow };

		for (int i = 0; i < this.getHistogramQuery().getSampleTimes(); i++) {
			currentEpochHistogramInfo = this.regionCollect(colors[i
					% colors.length]);
			qre = new QueryResultEvent(this, currentEpochHistogramInfo);
			this.getSimulator().handle(qre);
			Time.tick(this.getHistogramQuery().getSampleEpoch());
		}
	}

	public HistogramInfo regionCollect(Color color)
			throws SensornetBaseException {
		int maxDepth = this.attachmentsWithSameDepth.size() - 1;
		Vector sameDepthAttachments;
		NodeAttachment child, parent;
		HistogramInfo histogramInfo = this.getHistogramQuery()
				.getHistogramInfo();
		HistogramInfo localHistogramInfo;
		Message partialAnswerMessage;
		BroadcastEvent sendPartialAnswerEvent;
		double sampleValue;
		Node node = query.getOrig();
		NodeAttachment root = (NodeAttachment) node.getAttachment(this
				.getName());

		for (int depth = maxDepth; depth >= 0; depth--) {
			sameDepthAttachments = (Vector) this.attachmentsWithSameDepth
					.elementAt(depth);
			for (int i = 0; i < sameDepthAttachments.size(); i++) {
				child = (NodeAttachment) sameDepthAttachments.elementAt(i);
				parent = child.getParent();

				if (child.isLeaf()) {
					if (parent != null) {
						localHistogramInfo = (HistogramInfo) histogramInfo
								.clone();
						sampleValue = ((Number) child.sampleByNodeID(scene,
								sensorBoard, localHistogramInfo
										.getAttributeName())).doubleValue();
						localHistogramInfo.addValue(sampleValue);
						partialAnswerMessage = new Message(this.getParam()
								.getANSWER_SIZE(), localHistogramInfo);
						sendPartialAnswerEvent = new BroadcastEvent(child,
								parent, partialAnswerMessage, this, color);
						this.getSimulator().handle(sendPartialAnswerEvent);
						parent.storeObjectFromChild(child, localHistogramInfo);
					}
				} else {
					HistogramInfo partialAnswerHistogramInfo;
					localHistogramInfo = (HistogramInfo) histogramInfo.clone();
					sampleValue = ((Number) child.sampleByNodeID(scene,
							sensorBoard, localHistogramInfo.getAttributeName()))
							.doubleValue();
					localHistogramInfo.addValue(sampleValue);
					partialAnswerHistogramInfo = this.mergeHistogramInfos(
							localHistogramInfo, child.getObjectsFromChilds());

					if (child != root && parent != null) {
						partialAnswerMessage = new Message(this.getParam()
								.getANSWER_SIZE(), partialAnswerHistogramInfo);
						sendPartialAnswerEvent = new BroadcastEvent(child,
								parent, partialAnswerMessage, this, color);
						this.getSimulator().handle(sendPartialAnswerEvent);
						parent.storeObjectFromChild(child,
								partialAnswerHistogramInfo);

					} else {
						// reach the root so return the answer
						return partialAnswerHistogramInfo;
					}
				}
			}
		}
		return null;
	}

	protected HistogramInfo mergeHistogramInfos(HistogramInfo local,
			Hashtable objectsFromChilds) {
		Vector valueRanges = local.getValueRanges();
		ValueRange valueRange;
		double value;
		HistogramInfo resultHistogramInfo = (HistogramInfo) local.clone();
		HistogramInfo childHistogramInfo;

		for (int i = 0; i < valueRanges.size(); i++) {
			valueRange = (ValueRange) valueRanges.elementAt(i);
			value = local.getValueRangeValue(valueRange);
			Enumeration enumeration = objectsFromChilds.keys();
			while (enumeration.hasMoreElements()) {
				childHistogramInfo = (HistogramInfo) objectsFromChilds
						.get(enumeration.nextElement());
				value += childHistogramInfo.getValueRangeValue(valueRange);
			}
			resultHistogramInfo.setValueRangeValue(valueRange, value);
		}
		return resultHistogramInfo;
	}

	public void initAttachment() {
		Vector nodes = this.getNetwork().getNodes();
		Node node;
		NodeAttachment na;

		for (Iterator it = nodes.iterator(); it.hasNext();) {
			node = (Node) it.next();
			na = new NodeAttachment(node, this, this.getParam()
					.getINIT_ENERGY(), this.getParam().getRADIO_RANGE());
			node.attach(this.getName(), na);
		}
		this.initNeighbors();
	}

	public void initNeighbors() {
		Vector nodes = this.getNetwork().getNodes();
		Node node;
		NodeAttachment na;

		for (Iterator it = nodes.iterator(); it.hasNext();) {
			node = (Node) it.next();
			na = (NodeAttachment) node.getAttachment(this.getName());
			// NOTE initial neighbors
			na.initNeighbors();
		}
	}

	public TreeEvent getTreeEvent() {
		return treeEvent;
	}

	protected void setTreeEvent(TreeEvent treeEvent) {
		this.treeEvent = treeEvent;
	}

	public void setSceneAndSensorBoard(Scene scene, SensorBoard sensorBoard) {
		this.scene = scene;
		this.sensorBoard = sensorBoard;
	}
}