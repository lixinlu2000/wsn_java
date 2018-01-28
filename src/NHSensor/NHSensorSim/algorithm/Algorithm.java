package NHSensor.NHSensorSim.algorithm;

import java.awt.Color;
import java.util.Hashtable;
import java.util.Vector;

import NHSensor.NHSensorSim.core.AlgBlackBox;
import NHSensor.NHSensorSim.core.AlgParam;
import NHSensor.NHSensorSim.core.Attachment;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.NodeAttachment;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.QueryResultCorrectness;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.core.TraversedNodes;
import NHSensor.NHSensorSim.dataset.DataSet;
import NHSensor.NHSensorSim.events.DrawLinkEvent;
import NHSensor.NHSensorSim.events.DrawLostNodesEvent;
import NHSensor.NHSensorSim.events.DrawNodeEvent;
import NHSensor.NHSensorSim.events.DrawStringEvent;
import NHSensor.NHSensorSim.events.Event;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.query.QueryBase;
import NHSensor.NHSensorSim.shape.Shape;

public abstract class Algorithm {
	private static String NAME = "Algorithm";
	protected String name;
	protected QueryBase query;
	protected Network network;
	protected Simulator simulator;
	// common node,energy param
	protected Param param;
	// algorithm param
	protected AlgParam algParam;
	protected Statistics statistics;
	protected Vector ans = new Vector();
	protected AlgBlackBox algBlackBox = new AlgBlackBox();
	protected String description = null;
	// the nodes that algorithm has traversed
	protected TraversedNodes traversedAnswerNodes = new TraversedNodes();
	// iwqe lsa gsa itinerary node including send query message and return query
	// result back itinerary
	// algorithm itinerary
	private Vector route = new Vector();
	private boolean considerLinkQuality = false;
	private Attachment firstNodeHasNoEnergy = null;
	private DataSet dataset = null;
	private double totalCommunicationTime = 0;
	private boolean useAlgorithmRadioRangeToCalEnergy = false;
	private double algorithmRadioRange = 0;
	private boolean radioBroadcastNature = true;

	public String getDescription() {
		if (this.description == null) {
			return this.getName();
		} else
			return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isRadioBroadcastNature() {
		return radioBroadcastNature;
	}

	public void setRadioBroadcastNature(boolean radioBroadcastNature) {
		this.radioBroadcastNature = radioBroadcastNature;
	}

	public boolean isUseAlgorithmRadioRangeToCalEnergy() {
		return useAlgorithmRadioRangeToCalEnergy;
	}

	public void setUseAlgorithmRadioRangeToCalEnergy(
			boolean useAlgorithmRadioRangeToCalEnergy) {
		this.useAlgorithmRadioRangeToCalEnergy = useAlgorithmRadioRangeToCalEnergy;
	}

	public double getAlgorithmRadioRange() {
		return algorithmRadioRange;
	}

	public void setAlgorithmRadioRange(double algorithmRadioRange) {
		this.algorithmRadioRange = algorithmRadioRange;
	}

	public DataSet getDataset() {
		return dataset;
	}

	public void setDataset(DataSet dataset) {
		this.dataset = dataset;
	}

	public Vector getAns() {
		return ans;
	}

	public AlgBlackBox getAlgBlackBox() {
		return algBlackBox;
	}

	public void setAlgBlackBox(AlgBlackBox algBlackBox) {
		this.algBlackBox = algBlackBox;
	}

	public boolean isaNodeHasNoEnergy() {
		return this.firstNodeHasNoEnergy != null;
	}

	public void setFirstNodeHasNoEnergy(Attachment aNodeHasNoEnergy) {
		this.firstNodeHasNoEnergy = aNodeHasNoEnergy;
	}

	public boolean isConsiderLinkQuality() {
		return considerLinkQuality;
	}

	public void setConsiderLinkQuality(boolean considerLinkQuality) {
		this.considerLinkQuality = considerLinkQuality;
	}

	public TraversedNodes getTraversedAnswerNodes() {
		return traversedAnswerNodes;
	}

	public void setTraversedAnswerNodes(TraversedNodes traversedAnswerNodes) {
		this.traversedAnswerNodes = traversedAnswerNodes;
	}

	public void setAns(Vector ans) {
		this.ans = ans;
	}

	public Statistics getStatistics() {
		return statistics;
	}

	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}

	public Algorithm(QueryBase query) {
		this.query = query;
	}

	public Algorithm() {
	}

	public Algorithm(QueryBase query, Network network, Simulator simulator,
			Param param, String name, Statistics statistics) {
		this.query = query;
		this.network = network;
		this.simulator = simulator;
		this.param = param;
		this.name = name;
		this.statistics = statistics;
	}

	public Algorithm(QueryBase query, Network network, Simulator simulator,
			Param param, Statistics statistics) {
		this.query = query;
		this.network = network;
		this.simulator = simulator;
		this.param = param;
		this.name = Algorithm.NAME;
		this.statistics = statistics;
	}

	/*
	 * init node attachment
	 */
	public void init() {
		initAttachment();
		initOthers();
	}

	public void reInit() {
		this.initOthers();
	}

	public abstract void initAttachment();

	public void initOthers() {
	}
	
	public int getHopCountToSink(Node node) {
		NodeAttachment na = (NodeAttachment)node.getAttachment(this.getName());
		return na.getHopCountToSink();
	}

	public abstract boolean run();

	public boolean run(Event event) {
		try {
			this.getSimulator().handle(event);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean run(QueryBase newQuery) {
		QueryBase oldQuery = this.getQuery();
		this.setQuery(newQuery);
		this.run();
		this.setQuery(oldQuery);
		return true;
	}
	
	public void formParentChilds(NodeAttachment start, Vector ancestors) {
		NodeAttachment child, parent;
		child = start;
		
		for(int i=0;i<ancestors.size();i++) {
			parent = (NodeAttachment)ancestors.elementAt(i);
			child.setParent(parent);
			parent.addChild(child);
			child = parent;
		}
	}
	
	public Vector compareParentEvents(Algorithm alg, Color nodeColor, Color linkColor) {
		Vector nodesWithDiffrentParent = this.compareParent(alg);
		Vector events = new Vector();
		Node node0;
		NodeAttachment na0,node0Parent;
		
		for(int i=0;i<nodesWithDiffrentParent.size();i++) {
			node0 = (Node)nodesWithDiffrentParent.elementAt(i);
			na0 = (NodeAttachment) node0.getAttachment(this.getName());
			node0Parent = (NodeAttachment)na0.getParent();
			
			if(node0Parent==null) {
				DrawNodeEvent dne = new DrawNodeEvent(this,node0,3,nodeColor);
				events.add(dne);
			}
			else {
				DrawNodeEvent dne = new DrawNodeEvent(this,node0,3,nodeColor);
				events.add(dne);
				DrawLinkEvent dle = new DrawLinkEvent(this, node0, node0Parent.getNode(), linkColor);
				events.add(dle);
			}
		}
		return events;
	}
	
	public Vector compareParent(Algorithm alg) {
		Vector nodesWithDiffrentParent = new Vector();
		
		int nodeNum = this.getNetwork().getNodeNum();
		Node node0,node1;
		NodeAttachment node0Parent,node1Parent;
		NodeAttachment na0,na1;

		for (int i=0;i<nodeNum;i++) {
			node0 = (Node) this.getNetwork().getNode(i);
			node1 = (Node) alg.getNetwork().getNode(i);
			na0 = (NodeAttachment) node0.getAttachment(this.getName());
			na1 = (NodeAttachment) node1.getAttachment(alg.getName());
			node0Parent = (NodeAttachment)na0.getParent();
			node1Parent = (NodeAttachment)na1.getParent();
			if(node0Parent==null&&node1Parent!=null) {
				nodesWithDiffrentParent.add(node0);
			}
			else if(node0Parent!=null&&node1Parent==null) {
				nodesWithDiffrentParent.add(node0);
			}
			else if(node0Parent!=null&&node1Parent!=null) {
				if(node0Parent.getNode().getId()!=node1Parent.getNode().getId())
					nodesWithDiffrentParent.add(node0);
			}			
		}
		return nodesWithDiffrentParent;
	}
	
	public Vector compareHopCountToSinkEvents(Algorithm alg, Color nodeColor, Color stringColor) {
		Vector events = new Vector();
		
		int nodeNum = this.getNetwork().getNodeNum();
		Node node0,node1;
		int hopCount0, hopCount1;
		NodeAttachment na0,na1;
		StringBuffer sb;

		for (int i=0;i<nodeNum;i++) {
			node0 = (Node) this.getNetwork().getNode(i);
			node1 = (Node) alg.getNetwork().getNode(i);
			na0 = (NodeAttachment) node0.getAttachment(this.getName());
			na1 = (NodeAttachment) node1.getAttachment(alg.getName());
			hopCount0 = this.getHopCountToSink(node0);
			hopCount1 = alg.getHopCountToSink(node1);
			
			if(hopCount0!=hopCount1) {
				DrawNodeEvent dne = new DrawNodeEvent(this,node0,3,nodeColor);
				events.add(dne);
				
				sb = new StringBuffer();
				sb.append(hopCount0);
				sb.append("|");
				sb.append(hopCount1);
				DrawStringEvent dse = new DrawStringEvent(this, node0.getPos(), sb.toString(),
						stringColor, 1,1);
				events.add(dse);
			}
		}
		return events;
	}

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

	public Simulator getSimulator() {
		return simulator;
	}

	public void setSimulator(Simulator simulator) {
		this.simulator = simulator;
	}

	public QueryBase getQuery() {
		return query;
	}

	public void setQuery(QueryBase query) {
		this.query = query;
	}

	public Param getParam() {
		return param;
	}

	public void setParam(Param param) {
		this.param = param;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AlgParam getAlgParam() {
		return algParam;
	}

	public void setAlgParam(AlgParam algParam) {
		this.algParam = algParam;
	}

	public void addAnswerNode(Node node) {
		this.traversedAnswerNodes.add(node);
	}

	public void addAnswerAttachment(Attachment attachment) {
		this.traversedAnswerNodes.addAttachment(attachment);
	}

	public void addAnswerAttachment(Attachment attachment, double rate) {
		this.traversedAnswerNodes.addAttachment(attachment, rate);
	}

	public QueryResultCorrectness getQueryResultCorrectness() {
		TraversedNodes realTraversedAnswerNodes = this.getQuery()
				.getTraversedNodes(this.getNetwork());
		return this.traversedAnswerNodes.compare(realTraversedAnswerNodes);
	}
	
	public QueryResultCorrectness getQueryResultCorrectness(Shape shape) {
		TraversedNodes realTraversedAnswerNodes = TraversedNodes.getTraversedNodes(this.getNetwork(), shape);
		return this.traversedAnswerNodes.compare(realTraversedAnswerNodes);
	}


	public Vector caculateLostNodes() {
		TraversedNodes realTraversedAnswerNodes = this.getQuery()
				.getTraversedNodes(this.getNetwork());
		return this.traversedAnswerNodes
				.caculateLostNodes(realTraversedAnswerNodes);
	}
	
	public Vector caculateLostNodes(Shape shape) {
		TraversedNodes realTraversedAnswerNodes = TraversedNodes.getTraversedNodes(this.getNetwork(),
				shape);
		return this.traversedAnswerNodes
				.caculateLostNodes(realTraversedAnswerNodes);
	}


	public void insertLostNodesEventToSimulatorEventsHead() {
		Vector lostNodes = this.caculateLostNodes();
		DrawLostNodesEvent drawLostNodesEvent = new DrawLostNodesEvent(this,
				lostNodes);
		try {
			this.getSimulator().headHandle(drawLostNodesEvent);
		} catch (SensornetBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertLostNodesEventToSimulatorEventsHead(Shape shape) {
		Vector lostNodes = this.caculateLostNodes(shape);
		DrawLostNodesEvent drawLostNodesEvent = new DrawLostNodesEvent(this,
				lostNodes);
		try {
			this.getSimulator().headHandle(drawLostNodesEvent);
		} catch (SensornetBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public QueryResultCorrectness getQueryResultCorrectness(
			TraversedNodes shouldTraversedNodes) {
		return this.traversedAnswerNodes.compare(shouldTraversedNodes);
	}

	public Vector getRoute() {
		return route;
	}

	public void setRoute(Vector route) {
		this.route = route;
	}

	public int getRouteSize() {
		return this.route.size();
	}

	public int getItineraryNodeSize() {
		if (this.route.size() == 0)
			return 0;
		else
			return this.route.size() - 1;
	}
	
	public void addCommunicationTime(double time) {
		this.totalCommunicationTime += time;
	}

	public double getTotalCommunicationTime() {
		return totalCommunicationTime;
	}

	public Hashtable getCoreParameters() {
		Hashtable parameters = new Hashtable();
		parameters
				.put("networkWidth", new Double(this.getNetwork().getWidth()));
		parameters.put("networkHeight", new Double(this.getNetwork()
				.getHeigth()));
		parameters.put("nodeNum", new Double(this.getNetwork().getNodeNum()));
		parameters.put("radioRange", new Double(this.getParam()
				.getRADIO_RANGE()));
		return parameters;
	}
	
	

}
