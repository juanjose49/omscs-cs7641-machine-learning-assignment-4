package burlap.assignment4.util;

import burlap.assignment4.BasicGridWorld;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.GroundedAction;
import burlap.oomdp.singleagent.RewardFunction;

public class BasicRewardFunction implements RewardFunction {

	int goalX;
	int goalY;
	int goalX2;
    int goalY2;
    boolean secondary;

	public BasicRewardFunction(int goalX, int goalY) {
		this.goalX = goalX;
		this.goalY = goalY;

        this.secondary = false;
	}

    public BasicRewardFunction(int goalX, int goalY, int goalX2, int goalY2) {
        this.goalX = goalX;
        this.goalY = goalY;

        this.secondary = true;
        this.goalX2 = goalX2;
        this.goalY2 = goalY2;
    }

	@Override
	public double reward(State s, GroundedAction a, State sprime) {

		// get location of agent in next state
		ObjectInstance agent = sprime.getFirstObjectOfClass(BasicGridWorld.CLASSAGENT);
		int ax = agent.getIntValForAttribute(BasicGridWorld.ATTX);
		int ay = agent.getIntValForAttribute(BasicGridWorld.ATTY);

		// are they at goal location?
		if (ax == this.goalX && ay == this.goalY) {
			return 100.;
		}

        // if secondary is set, check if at secondary goal
        if (secondary && ax == this.goalX2 && ay == this.goalY2) {
            return 50.;
        }

		return -1;
	}

}
