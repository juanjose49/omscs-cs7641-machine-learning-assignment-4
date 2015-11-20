package burlap.assignment4.easy;

import burlap.assignment4.util.AgentPainter;
import burlap.assignment4.util.AtLocation;
import burlap.assignment4.util.LocationPainter;
import burlap.assignment4.util.Movement;
import burlap.assignment4.util.WallPainter;
import burlap.oomdp.auxiliary.DomainGenerator;
import burlap.oomdp.core.Attribute;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.ObjectClass;
import burlap.oomdp.core.objects.MutableObjectInstance;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.MutableState;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.SADomain;
import burlap.oomdp.visualizer.StateRenderLayer;
import burlap.oomdp.visualizer.Visualizer;

public class EasyGridWorld implements DomainGenerator {

	public static final String ATTX = "x";
	public static final String ATTY = "y";

	public static final String CLASSAGENT = "agent";
	public static final String CLASSLOCATION = "location";

	public static final String ACTIONNORTH = "north";
	public static final String ACTIONSOUTH = "south";
	public static final String ACTIONEAST = "east";
	public static final String ACTIONWEST = "west";

	public static final String PFAT = "at";

	// ordered so first dimension is x
	protected int[][] map = new int[][] { 
			{ 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
			{ 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1 },
			{ 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 }, };


	@Override
	public Domain generateDomain() {

		SADomain domain = new SADomain();

		Attribute xatt = new Attribute(domain, ATTX,
				Attribute.AttributeType.INT);
		xatt.setLims(0, 10);

		Attribute yatt = new Attribute(domain, ATTY,
				Attribute.AttributeType.INT);
		yatt.setLims(0, 10);

		ObjectClass agentClass = new ObjectClass(domain, CLASSAGENT);
		agentClass.addAttribute(xatt);
		agentClass.addAttribute(yatt);

		ObjectClass locationClass = new ObjectClass(domain, CLASSLOCATION);
		locationClass.addAttribute(xatt);
		locationClass.addAttribute(yatt);

		new Movement(ACTIONNORTH, domain, 0, map);
		new Movement(ACTIONSOUTH, domain, 1, map);
		new Movement(ACTIONEAST, domain, 2, map);
		new Movement(ACTIONWEST, domain, 3, map);

		new AtLocation(domain);

		return domain;
	}

	public static State getExampleState(Domain domain) {
		State s = new MutableState();
		ObjectInstance agent = new MutableObjectInstance(
				domain.getObjectClass(CLASSAGENT), "agent0");
		agent.setValue(ATTX, 0);
		agent.setValue(ATTY, 0);

		ObjectInstance location = new MutableObjectInstance(
				domain.getObjectClass(CLASSLOCATION), "location0");
		location.setValue(ATTX, 10);
		location.setValue(ATTY, 10);

		s.addObject(agent);
		s.addObject(location);

		return s;
	}

	public StateRenderLayer getStateRenderLayer() {
		StateRenderLayer rl = new StateRenderLayer();
		rl.addStaticPainter(new WallPainter(map));
		rl.addObjectClassPainter(CLASSLOCATION, new LocationPainter(map));
		rl.addObjectClassPainter(CLASSAGENT, new AgentPainter(map));

		return rl;
	}

	public Visualizer getVisualizer() {
		return new Visualizer(this.getStateRenderLayer());
	}

	public int[][] getMap() {
		return map;
	}

	public void setMap(int[][] map) {
		this.map = map;
	}



}
