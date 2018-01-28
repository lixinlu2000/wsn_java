package NHSensor.NHSensorSim.core;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import NHSensor.NHSensorSim.failure.NodeFailureModel;
import NHSensor.NHSensorSim.link.FullIsolatedLinkEstimator;
import NHSensor.NHSensorSim.link.IdealRadioRangeLinkEstimator;
import NHSensor.NHSensorSim.link.LinkEstimator;
import NHSensor.NHSensorSim.query.Query;
import NHSensor.NHSensorSim.shape.Circle;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.shape.Ring;
import NHSensor.NHSensorSim.shape.Shape;
import NHSensor.NHSensorSim.util.Util;

public class Network implements Serializable{
	public static final String INTELLAB_MOC_LOC_FILEPATH = ".\\dataset\\intelLab_mote_locs.txt";
	private Rect rect;
	public static final String ANRG_NODE_LOC_FILEPATH1 = "./dataset/ANRG/linkQuality/dataset1/xy.txt";
	public static final String ANRG_NODE_LOC_FILEPATH2 = "./dataset/ANRG/linkQuality/dataset2/xy.txt";

	private Vector nodes;
	private Hashtable nodeIDNodes;
	private LinkEstimator linkEstimator = new FullIsolatedLinkEstimator();
	private Vector failNodes = new Vector();
	private Circle[] holes;
	private int maxNodeID = Integer.MIN_VALUE;
	
	public Network() {
		super();
	}

	public static String getIntellabMocLocFilepath() {
		return INTELLAB_MOC_LOC_FILEPATH;
	}

	public static String getAnrgNodeLocFilepath1() {
		return ANRG_NODE_LOC_FILEPATH1;
	}

	public static String getAnrgNodeLocFilepath2() {
		return ANRG_NODE_LOC_FILEPATH2;
	}

	private String description = "";
	

	public LinkEstimator getLinkEstimator() {
		return linkEstimator;
	}

	public void setLinkEstimator(LinkEstimator linkEstimator) {
		this.linkEstimator = linkEstimator;
		this.initNeighbors();
	}
	
	public void setRadioRange(double radioRange) {
		this.linkEstimator = new IdealRadioRangeLinkEstimator(this, radioRange);
		this.initNeighbors();
	}

	public Network(double width, double height) {
		this.rect = new Rect(width, height);
	}

	public Network(Rect rect) {
		this.rect = rect;
	}

	public int getNodeNum() {
		return this.nodes.size();
	}
	
	public void addNode(int x, int y) {
		Node node = new Node(++this.maxNodeID, x, y);
		this.nodes.add(node);
		this.nodeIDNodes.put(node.getId(), node);
		this.setNodes(this.nodes);		
	}
	
	public void deleteNodes(Vector deleteNodes) {
		this.nodes.removeAll(deleteNodes);
		
		Node node;
		for(int i=0;i<deleteNodes.size();i++) {
			node = (Node)deleteNodes.elementAt(i);
			this.nodeIDNodes.remove(node.getId());
		}
		this.setNodes(this.nodes);		
	}


	public Hashtable getNodeIDNodes() {
		return nodeIDNodes;
	}

	public void setNodeIDNodes(Hashtable nodeIDNodes) {
		this.nodeIDNodes = nodeIDNodes;
	}

	public double getLinkPRR(Node node1, Node node2) {
		return this.linkEstimator.getLinkPRR(node1.getId(), node2.getId());
	}

	public double getSendTimes(Node node1, Node node2) {
		return 1.0 / this.linkEstimator
				.getLinkPRR(node1.getId(), node2.getId());
	}
	
	public Network getSubNetwork(Rect rect) {
		Vector subNetworkNodes = new Vector();
		Node node;
		for (int i = 0; i < this.getNodeNum(); i++) {
			node = (Node) this.getNode(i);
			if (node.getPos().in(rect)) {
				subNetworkNodes.add(node);
			}
		}
		Network network = new Network(rect);
		network.setNodes(subNetworkNodes);
		network.setLinkEstimator(this.getLinkEstimator());
		return network;
	}

	public static Network createNetwork(double width, double height,
			Vector nodes, Neighborhoods neighborhoods) {
		Network n = new Network(width, height);
		Hashtable nodeIDNodes = new Hashtable();
		Node node;

		for (int i = 0; i < nodes.size(); i++) {
			node = (Node) nodes.elementAt(i);
			nodeIDNodes.put(node.getId(), node);
			node.setNeighborIDs(neighborhoods.getNeighborIds(node.getId()));
		}
		n.setNodes(nodes);
		n.setNodeIDNodes(nodeIDNodes);
		return n;
	}

	public static Network createNetwork(double width, double height,
			Node[] nodes, Neighborhoods neighborhoods) {
		Network n = new Network(width, height);
		Hashtable nodeIDNodes = new Hashtable();
		Vector nodesVector = new Vector();

		for (int i = 0; i < nodes.length; i++) {
			nodesVector.add(nodes[i]);
			nodeIDNodes.put(nodes[i].getId(), nodes[i]);
			nodes[i].setNeighborIDs(neighborhoods.getNeighborIds(nodes[i]
					.getId()));
		}
		n.setNodes(nodesVector);
		n.setNodeIDNodes(nodeIDNodes);
		return n;
	}

	public static Network createRandomNetwork(double width, double height,
			int num, int randomNumber) {
		Network n = new Network(width, height);
		Vector nodes = new Vector();
		Hashtable nodeIDNodes = new Hashtable();
		Node node;
		Position pos;
		Random r = new Random(randomNumber);

		for (int i = 0; i < num; i++) {
			pos = new Position(r.nextDouble() * width, r.nextDouble() * height);
			node = new Node(i, pos);
			nodes.add(node);
			nodeIDNodes.put(new Integer(i), node);
		}
		n.setNodes(nodes);
		n.setNodeIDNodes(nodeIDNodes);
		return n;
	}
	
	public static Circle[] createHolesOld(double width, double height,
			int holeNumber, double maxHoleRadius, int holeModelID) {
		Random holeCentresRandom = new Random(holeModelID);
		Position[] holeCentres = new Position[holeNumber];
		Circle[] holes = new Circle[holeNumber];
		for (int i = 0; i < holeNumber; i++) {
			holeCentres[i] = new Position(holeCentresRandom.nextDouble() * width, holeCentresRandom.nextDouble() * height);
			holes[i] = new Circle(holeCentres[i], holeCentresRandom.nextDouble()*maxHoleRadius);
		}
		return holes;
	}
	
	public static Circle[] createHoles(double width, double height,
			int holeNumber, double maxHoleRadius, int holeModelID) {
		Random holeCentresRandom = new Random(holeModelID);
		Position[] holeCentres = new Position[holeNumber];
		Circle[] holes = new Circle[holeNumber];
		
		if(holes.length>0) {
			holes[0] = new Circle(new Position(width/2, height/2), holeCentresRandom.nextDouble()*maxHoleRadius);
			for (int i = 1; i < holeNumber; i++) {
				holeCentres[i] = new Position(holeCentresRandom.nextDouble() * width, holeCentresRandom.nextDouble() * height);
				holes[i] = new Circle(holeCentres[i], holeCentresRandom.nextDouble()*maxHoleRadius);
			}
		}
		return holes;
	}
	
	public static boolean isInCircles(Position pos, Circle[] circles) {
		for(int i=0;i<circles.length;i++) {
			if(circles[i].contains(pos)) return true;
		}
		return false;
	}
	
	public static Network createRandomNetworkWithHole(double width, double height,
			int num, int randomNumber, int holeNumber, double maxHoleRadius, int holeModelID) {
		Network n = new Network(width, height);
		Vector nodes = new Vector();
		Hashtable nodeIDNodes = new Hashtable();
		Node node;
		Position pos;
		Random r = new Random(randomNumber);
		
		Circle[] holes = Network.createHoles(width, height, holeNumber, maxHoleRadius, holeModelID);
		int curNodeNum = 0;
		while(curNodeNum<num) {
			pos = new Position(r.nextDouble() * width, r.nextDouble() * height);
			if(Network.isInCircles(pos, holes)) {
				continue;
			}
			node = new Node(curNodeNum, pos);
			nodes.add(node);
			nodeIDNodes.put(new Integer(curNodeNum), node);
			curNodeNum++;
		}
		n.setNodes(nodes);
		n.setNodeIDNodes(nodeIDNodes);
		n.setHoles(holes);
		return n;
	}


	public static Network createRandomNetworkHasAMiddleTopNode(double width,
			double height, int num, int randomNumber) {
		Network n = new Network(width, height);
		Vector nodes = new Vector();
		Hashtable nodeIDNodes = new Hashtable();
		Node node;
		Position pos;
		Random r = new Random(randomNumber);

		for (int i = 0; i < num; i++) {
			if (i == 0) {
				pos = new Position(0.5 * width, height);
			} else {
				pos = new Position(r.nextDouble() * width, r.nextDouble()
						* height);
			}
			node = new Node(i, pos);
			nodes.add(node);
			nodeIDNodes.put(new Integer(i), node);
		}
		n.setNodes(nodes);
		n.setNodeIDNodes(nodeIDNodes);
		return n;
	}

	public static Network createRandomNetwork(double width, double height,
			int num) {
		Network n = new Network(width, height);
		Vector nodes = new Vector();
		Hashtable nodeIDNodes = new Hashtable();
		Node node;
		Position pos;
		Random r = new Random(1000);

		for (int i = 0; i < num; i++) {
			pos = new Position(r.nextDouble() * width, r.nextDouble() * height);
			node = new Node(i, pos);
			nodes.add(node);
			nodeIDNodes.put(new Integer(i), node);
		}
		n.setNodes(nodes);
		n.setNodeIDNodes(nodeIDNodes);
		return n;
	}

	public static Network createRandomNetwork(double width, double height,
			int num, double radioRange) {
		Network n = new Network(width, height);
		Vector nodes = new Vector();
		Hashtable nodeIDNodes = new Hashtable();
		Node node;
		Position pos;
		Random r = new Random(1000);

		for (int i = 0; i < num; i++) {
			pos = new Position(r.nextDouble() * width, r.nextDouble() * height);
			node = new Node(i, pos);
			nodes.add(node);
			nodeIDNodes.put(new Integer(i), node);
		}
		n.setNodes(nodes);
		n.setNodeIDNodes(nodeIDNodes);
		return n;
	}

	public static Network createRandomNetworkWithHole(double width,
			double height, int num, Position circleCenter, double radius) {
		Network n = new Network(width, height);
		Vector nodes = new Vector();
		Hashtable nodeIDNodes = new Hashtable();
		Node node;
		Position pos;
		Random r = new Random();
		r.setSeed(110);

		for (int i = 0; i < num; i++) {
			pos = new Position(r.nextDouble() * width, r.nextDouble() * height);
			if (pos.distance(circleCenter) <= radius) {
				i--;
				continue;
			}
			node = new Node(i, pos);
			nodes.add(node);
			nodeIDNodes.put(new Integer(i), node);
		}
		n.setNodes(nodes);
		n.setNodeIDNodes(nodeIDNodes);
		return n;
	}

	public static Network createGridNetwork(double width, double height,
			int xgap, int ygap) {
		Network n = new Network(width, height);
		Vector nodes = new Vector();
		Hashtable nodeIDNodes = new Hashtable();
		Node node;
		Position pos;

		int xnum = (int) (width / xgap) + 1;
		int ynum = (int) (height / ygap) + 1;

		for (int i = 0; i < xnum; i++) {
			for (int j = 0; j < ynum; j++) {
				pos = new Position(i * xgap, j * ygap);
				node = new Node(i * ynum + j + 1, pos);
				nodes.add(node);
				nodeIDNodes.put(new Integer(i), node);
			}
		}
		n.setNodes(nodes);
		n.setNodeIDNodes(nodeIDNodes);
		return n;
	}
	
	public static Vector generateNodesInt(Rect rect, int num, int beginID) {
		Vector nodes = new Vector();
		Node node;
		Position pos;
		Random r = new Random();
		r.setSeed(beginID);

		for (int i = 0; i < num; i++) {
			pos = new Position(rect.getMinX()+(int)(r.nextDouble() * rect.getWidth()), rect.getMinY()+(int)(r.nextDouble() * rect.getHeight()));
			node = new Node(beginID+i, pos);
			nodes.add(node);
		}
		return nodes;
	}
	
	/*
	 * create a line network in the middle of the region
	 */
	public static Network createMiddleLineNetwork(double width, double height,
			int num) {
		Network n = new Network(width, height);
		Vector nodes = new Vector();
		Hashtable nodeIDNodes = new Hashtable();
		Node node;
		Position pos;
		double xgap = width / (num - 1);

		for (int i = 0; i < num; i++) {
			pos = new Position(i * xgap, height / 2);
			node = new Node(i, pos);
			nodes.add(node);
			nodeIDNodes.put(new Integer(i), node);
		}
		n.setNodes(nodes);
		n.setNodeIDNodes(nodeIDNodes);
		return n;
	}

	public static Network createGPSRTestNetwork(double width, double height,
			int num, int lineNum, Position circleCenter, double radius) {
		Network n = Network.createRandomNetworkWithHole(width, height, num,
				circleCenter, radius);

		Node node;
		Position pos;
		double xgap = width / (lineNum - 1);
		int id = 0;
		int nNodesNum = n.getNodes().size();

		for (int i = 0; i < lineNum; i++) {
			pos = new Position(i * xgap, height / 2);
			if (pos.distance(circleCenter) <= radius)
				continue;
			node = new Node(id + nNodesNum, pos);
			id++;
			n.getNodes().add(node);
			n.getNodeIDNodes().put(new Integer(node.getId()), node);
		}
		return n;
	}

	public static Network cvdFig4Network() {
		Network n = new Network(6, 5);
		Vector nodes = new Vector();
		Hashtable nodeIDNodes = new Hashtable();
		Node node;

		node = new Node(0, new Position(4, 5));
		nodeIDNodes.put(new Integer(0), node);
		nodes.add(node);

		node = new Node(1, new Position(5, 4));
		nodeIDNodes.put(new Integer(1), node);
		nodes.add(node);

		node = new Node(2, new Position(6, 4));
		nodeIDNodes.put(new Integer(2), node);
		nodes.add(node);

		node = new Node(3, new Position(4, 3));
		nodeIDNodes.put(new Integer(3), node);
		nodes.add(node);

		node = new Node(4, new Position(5, 1));
		nodeIDNodes.put(new Integer(4), node);
		nodes.add(node);

		node = new Node(5, new Position(1, 3));
		nodeIDNodes.put(new Integer(5), node);
		nodes.add(node);

		node = new Node(6, new Position(3, 2));
		nodeIDNodes.put(new Integer(6), node);
		nodes.add(node);

		node = new Node(7, new Position(6, 3));
		nodeIDNodes.put(new Integer(7), node);
		nodes.add(node);

		node = new Node(8, new Position(2, 1));
		nodeIDNodes.put(new Integer(8), node);
		nodes.add(node);

		n.setNodes(nodes);
		n.setNodeIDNodes(nodeIDNodes);
		return n;
	}

	public static Network intelLabNetwork() {
		return Network.createNetworkFromFile(new File(
				Network.INTELLAB_MOC_LOC_FILEPATH));
	}

	public static Network anrgNetwork1() {
		return Network.createNetworkFromANRGFile(new File(
				Network.ANRG_NODE_LOC_FILEPATH1));
	}

	public static Network anrgNetwork2() {
		return Network.createNetworkFromANRGFile(new File(
				Network.ANRG_NODE_LOC_FILEPATH2));
	}

	public static Network createNetworkFromANRGFile(File file) {
		try {
			FileReader fr;
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			Vector nodes = new Vector();
			Rect rect;
			String[] lines = new String[3];
			int nodeNum;

			for (int i = 0; i < lines.length; i++) {
				lines[i] = br.readLine();
			}

			for (int i = 0; i < 3; i++) {
				if (lines[i] == null || lines[i].equals(""))
					return null;
			}

			nodeNum = Integer.parseInt(lines[0]);
			if (nodeNum <= 0)
				return null;
			String[] xss = lines[1].split("\\s+");
			String[] yss = lines[2].split("\\s+");
			if (xss.length != nodeNum || yss.length != nodeNum)
				return null;

			double[] xs = new double[nodeNum];
			double[] ys = new double[nodeNum];
			Node node;
			double maxx = 0;
			double maxy = 0;
			for (int i = 0; i < nodeNum; i++) {
				xs[i] = Double.parseDouble(xss[i]);
				ys[i] = Double.parseDouble(yss[i]);
				node = new Node(i, xs[i], ys[i]);
				nodes.add(node);
				maxx = Math.max(maxx, xs[i]);
				maxy = Math.max(maxy, ys[i]);
			}
			rect = new Rect(0, maxx, 0, maxy);
			Network network = new Network(rect);
			network.setNodes(nodes);
			return network;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public static Network createNetworkFromFile(File file) {
		int moteID;
		double x, y;
		Node node;
		Vector nodes = new Vector();
		Hashtable nodeIDNodes = new Hashtable();
		Rect r = null;
		int maxNodeID = Integer.MIN_VALUE;

		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			// process file header ->width height
			double w, h;
			if (line != null) {
				String[] rectInfo = line.split("\\s");
				if (rectInfo.length < 2) {
					System.out.println("file header error");
					return null;
				} else {
					w = Double.parseDouble(rectInfo[0]);
					h = Double.parseDouble(rectInfo[1]);
					r = new Rect(w, h);
				}
			} else {
				System.out.println("file has no header");
				return null;
			}

			line = br.readLine();
			while (line != null) {
				String[] nodeInfo = line.split("\\s+");
				if (nodeInfo.length < 3) {
					System.out.println("file format error");
					return null;
				}
				moteID = Integer.parseInt(nodeInfo[0]) - 1;
				if(moteID>maxNodeID) maxNodeID = moteID;
				x = w - Double.parseDouble(nodeInfo[1]);
				y = h - Double.parseDouble(nodeInfo[2]);
				node = new Node(moteID, x, y);
				nodes.add(node);
				nodeIDNodes.put(new Integer(node.getId()), node);
				line = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Network network = new Network(r);
		network.setNodes(nodes);
		network.setNodeIDNodes(nodeIDNodes);
		return network;
	}

	public Vector getNodes() {
		return nodes;
	}
	
	protected void cleanNodeNeighbors() {
		Node node1;
		
		for(int i=0;i<this.nodes.size();i++) {
			node1 = (Node)this.nodes.elementAt(i);
			node1.clearNeighborIDS();
		}
	}
	
	public void setNodeRadioRange(Node node, double radioRange) {
		node.setRadioRange(radioRange);
		this.initNeighbors();
	}
	
	public void initNeighbors() {	
		Node node1,node2;

		this.cleanNodeNeighbors();
		for(int i=0;i<nodes.size();i++) {
			node1 = (Node)nodes.elementAt(i);
			if(node1.getId()>this.maxNodeID) this.maxNodeID = node1.getId();
			for(int j=0;j<nodes.size();j++) {
				node2 = (Node)nodes.elementAt(j);
				if(i!=j&&this.getLinkPRR(node1, node2)>0) {
					node1.addNeighbor(node2.getId());
				}
			}
		}
		
	}

	public int getMaxNodeID() {
		return maxNodeID;
	}

	public void setMaxNodeID(int maxNodeID) {
		this.maxNodeID = maxNodeID;
	}

	public void setNodes(Vector nodes) {
		this.nodes = nodes;
		this.initNeighbors();
	}

	public Node getNode(int id) {
		return (Node) this.nodeIDNodes.get(id);
	}

	public Node getCentreNode() {
		return this.getNearestNodeToPos(this.getRect().getCentre());
	}

	public Node getARandomNode() {
		Random random = new Random();
		int index = (int) (random.nextDouble() * nodes.size());
		return (Node) nodes.elementAt(index);
	}

	public Node getFirstNode() {
		return (Node) nodes.elementAt(0);
	}

	public Node getLBNode() {
		return this.getNearestNodeToPos(this.getRect().getLB());
	}

	public Node getTopNodeInRing(Ring ring) {
		Position topPosition = new Position(ring.getCircleCentre().getX(), ring
				.getCircleCentre().getY()
				+ (ring.getLowRadius() + ring.getHighRadius()) / 2);
		return this.getNearestNodeToPosInShape(topPosition, ring);
	}

	public Node get2LRTNode() {
		Position pos = new Position(this.getRect().getCentre().getX(), this
				.getRect().getMaxY());
		return this.getNearestNodeToPos(pos);
	}
	
	public Node getNodeAboveRect(Rect rect, int index) {
		if(index==0) {
			return this.get2LRTNode();
		}
		Rect aboveRect = new Rect(rect.getMinX(), rect.getMaxX(), rect.getMaxY(), this.getHeigth());
		Vector nodes = this.getNodesInRect(aboveRect);
		if(!nodes.contains(this.get2LRTNode()))
			nodes.add(this.get2LRTNode());
		return (Node)nodes.elementAt(index%this.getNodeNumAboveRect(rect));
	}

	public int getNodeNumAboveRect(Rect rect) {
		Rect aboveRect = new Rect(rect.getMinX(), rect.getMaxX(), rect.getMaxY(), this.getHeigth());
		Vector nodes = this.getNodesInRect(aboveRect);
		if(!nodes.contains(this.get2LRTNode()))
			nodes.add(this.get2LRTNode());
		return nodes.size();
	}

	public Node get2LRTNode(Rect rect) {
		Position pos = new Position(this.getRect().getCentre().getX(), this
				.getRect().getMaxY());
		return this.getNearestNodeToPosInRect(pos, rect);
	}

	public Node get2LRBNode() {
		Position pos = new Position(this.getRect().getCentre().getX(), this
				.getRect().getMinY());
		return this.getNearestNodeToPos(pos);
	}

	public Node get2LRBNode(Rect rect) {
		Position pos = new Position(this.getRect().getCentre().getX(), this
				.getRect().getMinY());
		return this.getNearestNodeToPosInRect(pos, rect);
	}

	public Node getL2BTNode() {
		Position pos = new Position(this.getRect().getMinX(), this.getRect()
				.getCentre().getY());
		return this.getNearestNodeToPos(pos);
	}

	public Node getL2BTNode(Rect rect) {
		Position pos = new Position(this.getRect().getMinX(), this.getRect()
				.getCentre().getY());
		return this.getNearestNodeToPosInRect(pos, rect);
	}

	public Node getR2BTNode() {
		Position pos = new Position(this.getRect().getMaxX(), this.getRect()
				.getCentre().getY());
		return this.getNearestNodeToPos(pos);
	}

	public Node getR2BTNode(Rect rect) {
		Position pos = new Position(this.getRect().getMaxX(), this.getRect()
				.getCentre().getY());
		return this.getNearestNodeToPosInRect(pos, rect);
	}

	public Node getLTNode() {
		return this.getNearestNodeToPos(this.getRect().getLT());
	}

	public Node getNearestNodeToPos(Position pos) {
		return this.getNearestNodeToPosInRect(pos, this.getRect());
	}

	public Node getNearestNodeToPosInRect(Position pos, Rect rectangle) {
		if (this.nodes.isEmpty())
			return null;

		Node minDisNode = null;
		Node other;
		double minDis = Double.MAX_VALUE;
		double dis;

		for (Iterator it = this.nodes.iterator(); it.hasNext();) {
			other = (Node) it.next();
			if (!other.getPos().in(rectangle))
				continue;
			dis = other.getPos().distance(pos);
			if (dis < minDis) {
				minDisNode = other;
				minDis = dis;
			}
		}
		return minDisNode;
	}

	public Query createQuery(double queryRegionRate) {
		double queryRegionArea = this.getRect().area() * queryRegionRate;
		double queryRegionSize = Math.sqrt(queryRegionArea);
		Rect queryRect = new Rect((this.getWidth() - queryRegionSize) / 2,
				(this.getWidth() + queryRegionSize) / 2,
				(this.getHeigth() - queryRegionSize) / 2,
				(this.getHeigth() + queryRegionSize) / 2);
		Node orig = this.get2LRTNode();
		Query query = new Query(orig, queryRect);
		return query;
	}

	public Node getNearestNodeToPosInShape(Position pos, Shape shape) {
		if (this.nodes.isEmpty())
			return null;

		Node minDisNode = null;
		Node other;
		double minDis = Double.MAX_VALUE;
		double dis;

		for (Iterator it = this.nodes.iterator(); it.hasNext();) {
			other = (Node) it.next();
			if (!shape.contains(other.getPos()))
				continue;
			dis = other.getPos().distance(pos);
			if (dis < minDis) {
				minDisNode = other;
				minDis = dis;
			}
		}
		return minDisNode;
	}

	public Vector getNodesInRect(Rect rect) {
		Vector v = new Vector();
		Node node;

		for (Iterator it = this.nodes.iterator(); it.hasNext();) {
			node = (Node) it.next();
			if (node.getPos().in(rect)) {
				v.add(node);
			}
		}
		return v;
	}

	public double getWidth() {
		return this.getRect().getWidth();
	}

	public double getHeigth() {
		return this.getRect().getHeight();
	}

	public Rect getRect() {
		return this.rect;
	}

	public void setNodeFailureModel(NodeFailureModel nfm) {
		this.failNodes = nfm.getFailNodes(this.getNodes());
		Node node;

		for (int i = 0; i < failNodes.size(); i++) {
			node = (Node) failNodes.elementAt(i);
			node.setAlive(false);
		}
	}

	public Vector getFailNodes() {
		return failNodes;
	}

	public Circle[] getHoles() {
		return holes;
	}

	public void setHoles(Circle[] holes) {
		this.holes = holes;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void saveOld(File file) {
		XMLEncoder e;
		try {
			e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(
					file)));
			e.writeObject(this);
			e.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}
	
	public void save(File file) {
		 ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(file));
			 oos.writeObject(this);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static Network read(File file) throws Exception {
        FileInputStream fis;
		fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        return (Network) ois.readObject();
	}

	public void setRect(Rect rect) {
		this.rect = rect;
	}

	public void setFailNodes(Vector failNodes) {
		this.failNodes = failNodes;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Network{");
		sb.append("width{");
		sb.append(this.getWidth());
		sb.append("},");
		sb.append("height{");
		sb.append(this.getHeigth());
		sb.append("}");
		sb.append(",");
		sb.append(Util.toString(this.nodes));
		sb.append("\n}");
		return sb.toString();
	}
}
