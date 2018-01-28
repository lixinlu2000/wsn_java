package NHSensor.NHSensorSim.algorithm;

import java.util.Vector;

import NHSensor.NHSensorSim.core.GStarAlgParam;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.NodeAttachment;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.events.AlgorithmEvent;
import NHSensor.NHSensorSim.events.GreedyForwardToPointEvent;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.query.Query;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Rect;

public class GStarAlg extends AbstractTreeAlg {
	public final static String NAME = "GStar";

	private int xSectionCount, ySectionCount;
	boolean xRest = false;
	boolean yRest = false;
	private NodeAttachment[][] sectionSubRoots;
	private Rect[][] sectionRects;
	private NodeAttachment root;

	public GStarAlg(Query query) {
		super(query);
	}

	public GStarAlg(Query query, Network network, Simulator simulator,
			Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
	}

	public GStarAlg(Query query, Network network, Simulator simulator,
			Param param, Statistics statistics) {
		super(query, network, simulator, param, GStarAlg.NAME, statistics);
	}

	public GStarAlg() {
	}

	public void initAttachment() {
		super.initAttachment();
	}

	public void initOthers() {
		this.initSections();
		this.initRoot();
	}

	public void initSections() {
		GStarAlgParam gsap = (GStarAlgParam) this.getAlgParam();

		Rect queryRect = this.getSpatialQuery().getRect();
		double sw = gsap.getSectionWidth();
		double sh = gsap.getSectionHeight();
		Position subRootPos = gsap.getSubRoot();
		this.xSectionCount = (int) (queryRect.getWidth() / sw);
		this.ySectionCount = (int) (queryRect.getHeight() / sh);
		// divide > 0
		double x1, x2, y1, y2;

		if (this.xSectionCount * sw < queryRect.getWidth()) {
			this.xRest = true;
			this.xSectionCount++;
		}
		if (this.ySectionCount * sh < queryRect.getHeight()) {
			this.yRest = true;
			this.ySectionCount++;
		}
		this.sectionRects = new Rect[this.xSectionCount][this.ySectionCount];
		this.sectionSubRoots = new NodeAttachment[this.xSectionCount][this.ySectionCount];

		for (int i = 0; i < this.xSectionCount; i++) {
			for (int j = 0; j < this.ySectionCount; j++) {
				x1 = i * sw;
				x2 = (i + 1) * sw;
				y1 = j * sh;
				y2 = (j + 1) * sh;

				if (i == this.xSectionCount - 1 && this.xRest)
					x2 = queryRect.getWidth();
				if (j == this.ySectionCount - 1 && this.yRest)
					y2 = queryRect.getHeight();
				x1 += this.getSpatialQuery().getRect().getMinX();
				x2 += this.getSpatialQuery().getRect().getMinX();
				y1 += this.getSpatialQuery().getRect().getMinY();
				y2 += this.getSpatialQuery().getRect().getMinY();

				sectionRects[i][j] = new Rect(x1, x2, y1, y2);
				Position lb = sectionRects[i][j].getLB();
				Node subroot = this.getNetwork().getNearestNodeToPosInRect(
						lb.getAddedPosition(subRootPos), sectionRects[i][j]);
				if (subroot != null)
					sectionSubRoots[i][j] = (NodeAttachment) (subroot
							.getAttachment(this.getName()));
				else
					sectionSubRoots[i][j] = null;
			}
		}
	}

	public void initRoot() {
		NodeAttachment na;
		Position source = this.getSpatialQuery().getOrig().getPos();
		double minDist = Double.MAX_VALUE;
		double dist;
		NodeAttachment tmpRoot = null;

		for (int i = 0; i < this.getXSectionCount(); i++) {
			for (int j = 0; j < this.getYSectionCount(); j++) {
				na = this.sectionSubRoots[i][j];
				if (na == null)
					continue;
				dist = Position.distance(na.getNode().getPos(), source);
				if (dist < minDist) {
					tmpRoot = na;
					minDist = dist;
				}
			}
		}
		this.root = tmpRoot;
	}

	public boolean greedyForward() throws SensornetBaseException {
		NodeAttachment root = this.getRoot();
		Position dest = root.getNode().getPos();

		Message message = new Message(this.getParam().getQUERY_MESSAGE_SIZE(),
				this.getQuery());
		GreedyForwardToPointEvent gftpe = new GreedyForwardToPointEvent(root,
				dest, message, this);
		this.getSimulator().handle(gftpe);

		if (!gftpe.isSuccess()) {
			if (!gftpe.getLastNode().getNode().getPos().in(
					this.getSpatialQuery().getRect())) {
				System.out.println(this.getName() + " cannot greedy");
				return false;
			} else {

			}
		}
		return true;
	}

	public void sectionsGreedyTAG() throws SensornetBaseException {
		Node sender;
		Rect sectionRect;
		GreedyTAGAlg gta;
		AlgorithmEvent algEvent;
		Query query;
		GStarAlgParam gsap = (GStarAlgParam) this.getAlgParam();

		// subroots broadcast
		for (int i = 0; i < this.getXSectionCount(); i++) {
			for (int j = 0; j < this.getYSectionCount(); j++) {
				if (this.sectionSubRoots[i][j] == null)
					continue;

				sender = this.sectionSubRoots[i][j].getNode();
				sectionRect = this.sectionRects[i][j];
				query = new Query(sender, sectionRect);
				gta = new GreedyTAGAlg(query);
				gta.setRadioRange(gsap.getRadioRange());
				algEvent = new AlgorithmEvent(this, gta);
				this.getSimulator().handle(algEvent);
			}
		}
	}

	public void subRootGreedyTAG() {
		GStarAlgParam gsap = (GStarAlgParam) this.getAlgParam();
		// collect subroot result
		Network subRootsNetwork = new Network(this.getSpatialQuery().getRect());
		Vector subRoots = new Vector();
		for (int i = 0; i < this.getXSectionCount(); i++) {
			for (int j = 0; j < this.getYSectionCount(); j++) {
				if (this.sectionSubRoots[i][j] != null)
					subRoots.add(this.sectionSubRoots[i][j].getNode());
			}
		}
		subRootsNetwork.setNodes(subRoots);
		Param param = new Param();
		param.setRADIO_RANGE(gsap.getRadioRange());
		Query childQuery = new Query(root.getNode(), subRootsNetwork.getRect());

		// child tag
		GreedyTAGAlg subRootGta = new GreedyTAGAlg(childQuery, subRootsNetwork,
				this.getSimulator(), param, "childTAG", this.getStatistics());
		subRootGta.setJustCollect(true);
		subRootGta.init();
		subRootGta.run();
	}

	public boolean greedyBack() throws SensornetBaseException {
		Position pos = this.getSpatialQuery().getOrig().getPos();

		NodeAttachment src = this.getRoot();

		Message msg = new Message(this.getParam().getANSWER_SIZE(), null);
		GreedyForwardToPointEvent event = new GreedyForwardToPointEvent(src,
				pos, msg, this);
		this.getSimulator().handle(event);

		// set answers
		return event.isSuccess();

	}

	public boolean run() {
		try {
			if (!this.greedyForward()) {
				System.out.println("Cannot greedy forward");
				return false;
			}

			this.sectionsGreedyTAG();
			this.subRootGreedyTAG();

			if (!this.greedyBack()) {
				System.out.println("Cannnot greedy back");
				return false;
			}
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

	public Rect[][] getSectionRects() {
		return sectionRects;
	}

	public void setSectionRects(Rect[][] sectionRects) {
		this.sectionRects = sectionRects;
	}

	public NodeAttachment[][] getSectionSubRoots() {
		return sectionSubRoots;
	}

	public void setSectionSubRoots(NodeAttachment[][] sectionSubRoots) {
		this.sectionSubRoots = sectionSubRoots;
	}

	public boolean isXRest() {
		return xRest;
	}

	public void setXRest(boolean rest) {
		xRest = rest;
	}

	public int getXSectionCount() {
		return xSectionCount;
	}

	public void setXSectionCount(int sectionCount) {
		xSectionCount = sectionCount;
	}

	public boolean isYRest() {
		return yRest;
	}

	public void setYRest(boolean rest) {
		yRest = rest;
	}

	public int getYSectionCount() {
		return ySectionCount;
	}

	public void setYSectionCount(int sectionCount) {
		ySectionCount = sectionCount;
	}

	public NodeAttachment getRoot() {
		return root;
	}

}
