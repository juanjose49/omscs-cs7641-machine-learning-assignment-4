package burlap.assignment4.util;

import burlap.assignment4.easy.EasyGridWorld;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;

public class BasicTerminalFunction implements TerminalFunction {

	int goalX;
	int goalY;

	public BasicTerminalFunction(int goalX, int goalY) {
		this.goalX = goalX;
		this.goalY = goalY;
	}

	@Override
	public boolean isTerminal(State s) {

		// get location of agent in next state
		ObjectInstance agent = s.getFirstObjectOfClass(EasyGridWorld.CLASSAGENT);
		int ax = agent.getIntValForAttribute(EasyGridWorld.ATTX);
		int ay = agent.getIntValForAttribute(EasyGridWorld.ATTY);

		// are they at goal location?
		if (ax == this.goalX && ay == this.goalY) {
			return true;
		}

		return false;
	}

}
