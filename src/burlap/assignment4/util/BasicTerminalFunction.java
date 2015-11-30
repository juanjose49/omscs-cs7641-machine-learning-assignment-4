package burlap.assignment4.util;

import burlap.assignment4.BasicGridWorld;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;

public class BasicTerminalFunction implements TerminalFunction {

	int goalX;
	int goalY;
    int goalX2;
    int goalY2;
    boolean secondary;

	public BasicTerminalFunction(int goalX, int goalY) {
		this.goalX = goalX;
		this.goalY = goalY;

        secondary = false;
	}

    public BasicTerminalFunction(int goalX, int goalY, int goalX2, int goalY2) {
        this.goalX = goalX;
        this.goalY = goalY;

        this.secondary = true;
        this.goalX2 = goalX2;
        this.goalY2 = goalY2;
    }

	@Override
	public boolean isTerminal(State s) {

		// get location of agent in next state
		ObjectInstance agent = s.getFirstObjectOfClass(BasicGridWorld.CLASSAGENT);
		int ax = agent.getIntValForAttribute(BasicGridWorld.ATTX);
		int ay = agent.getIntValForAttribute(BasicGridWorld.ATTY);

		// are they at goal location?
		if (ax == this.goalX && ay == this.goalY) {
			return true;
		}

        if (secondary && ax == this.goalX2 && ay == this.goalY2) {
            return true;
        }

		return false;
	}

}
