package burlap.assignment4.util;

import burlap.assignment4.easy.EasyGridWorld;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.PropositionalFunction;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;

public class AtLocation extends PropositionalFunction {

	public AtLocation(Domain domain) {
		super(EasyGridWorld.PFAT, domain, new String[] { EasyGridWorld.CLASSAGENT, EasyGridWorld.CLASSLOCATION });
	}

	@Override
	public boolean isTrue(State s, String... params) {
		ObjectInstance agent = s.getObject(params[0]);
		ObjectInstance location = s.getObject(params[1]);

		int ax = agent.getIntValForAttribute(EasyGridWorld.ATTX);
		int ay = agent.getIntValForAttribute(EasyGridWorld.ATTY);

		int lx = location.getIntValForAttribute(EasyGridWorld.ATTX);
		int ly = location.getIntValForAttribute(EasyGridWorld.ATTY);

		return ax == lx && ay == ly;
	}

}
