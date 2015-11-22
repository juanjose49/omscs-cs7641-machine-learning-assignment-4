package burlap.assignment4.util;

import burlap.assignment4.BasicGridWorld;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.PropositionalFunction;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;

public class AtLocation extends PropositionalFunction {

	public AtLocation(Domain domain) {
		super(BasicGridWorld.PFAT, domain, new String[] { BasicGridWorld.CLASSAGENT, BasicGridWorld.CLASSLOCATION });
	}

	@Override
	public boolean isTrue(State s, String... params) {
		ObjectInstance agent = s.getObject(params[0]);
		ObjectInstance location = s.getObject(params[1]);

		int ax = agent.getIntValForAttribute(BasicGridWorld.ATTX);
		int ay = agent.getIntValForAttribute(BasicGridWorld.ATTY);

		int lx = location.getIntValForAttribute(BasicGridWorld.ATTX);
		int ly = location.getIntValForAttribute(BasicGridWorld.ATTY);

		return ax == lx && ay == ly;
	}

}
