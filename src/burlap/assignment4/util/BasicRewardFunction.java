package burlap.assignment4.util;

import burlap.assignment4.easy.EasyGridWorld;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.GroundedAction;
import burlap.oomdp.singleagent.RewardFunction;

public class BasicRewardFunction implements RewardFunction {

	int goalX;
	int goalY;

	public BasicRewardFunction(int goalX, int goalY) {
		this.goalX = goalX;
		this.goalY = goalY;
	}

	@Override
	public double reward(State s, GroundedAction a, State sprime) {

		// get location of agent in next state
		ObjectInstance agent = sprime.getFirstObjectOfClass(EasyGridWorld.CLASSAGENT);
		int ax = agent.getIntValForAttribute(EasyGridWorld.ATTX);
		int ay = agent.getIntValForAttribute(EasyGridWorld.ATTY);

		// are they at goal location?
		if (ax == this.goalX && ay == this.goalY) {
			return 100.;
		}

		return -1;
	}

}
