package burlap.assignment4.easy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import burlap.assignment4.util.BasicRewardFunction;
import burlap.assignment4.util.BasicTerminalFunction;
import burlap.behavior.policy.Policy;
import burlap.behavior.singleagent.EpisodeAnalysis;
import burlap.behavior.singleagent.auxiliary.EpisodeSequenceVisualizer;
import burlap.behavior.singleagent.learning.tdmethods.QLearning;
import burlap.behavior.singleagent.planning.stochastic.policyiteration.PolicyIteration;
import burlap.behavior.singleagent.planning.stochastic.valueiteration.ValueIteration;
import burlap.behavior.valuefunction.ValueFunctionInitialization;
import burlap.domain.singleagent.gridworld.GridWorldVisualizer;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.singleagent.environment.SimulatedEnvironment;
import burlap.oomdp.singleagent.explorer.VisualExplorer;
import burlap.oomdp.statehashing.SimpleHashableStateFactory;
import burlap.oomdp.visualizer.Visualizer;
import burlap.tutorials.cpl.QLTutorial;

public class EasyGridWorldLauncher {

	private static boolean visualizeInitialGridWorld = false;
	private static boolean visualizeValueIteration = false;
	private static boolean visualizePolicyIteration = false;
	private static boolean visualizeQLearning = true;
	

	public static void main(String[] args) {

		EasyGridWorld gen = new EasyGridWorld();
		Domain domain = gen.generateDomain();

		State initialState = EasyGridWorld.getExampleState(domain);

		RewardFunction rf = new BasicRewardFunction(10, 10);
		TerminalFunction tf = new BasicTerminalFunction(10, 10);

		SimulatedEnvironment env = new SimulatedEnvironment(domain, rf, tf,
				initialState);

		if (visualizeInitialGridWorld) {
			visualizeInitialGridWorld(domain, gen, env);
		}else if(visualizeValueIteration){
			runValueIteration(gen,domain,initialState, rf, tf);
		}else if(visualizePolicyIteration){
			runPolicyIteration(gen,domain,initialState, rf, tf);
		}else if(visualizeQLearning){
			runQLearning(gen,domain,initialState, rf, tf, env);

		}
	}

	private static void visualizeInitialGridWorld(Domain domain,
			EasyGridWorld gen, SimulatedEnvironment env) {
		Visualizer v = gen.getVisualizer();
		VisualExplorer exp = new VisualExplorer(domain, env, v);

		exp.addKeyAction("w", EasyGridWorld.ACTIONNORTH);
		exp.addKeyAction("s", EasyGridWorld.ACTIONSOUTH);
		exp.addKeyAction("d", EasyGridWorld.ACTIONEAST);
		exp.addKeyAction("a", EasyGridWorld.ACTIONWEST);

		exp.initGUI();

	}

	private static void runValueIteration(EasyGridWorld gen, Domain domain,
			State initialState, RewardFunction rf, TerminalFunction tf) {

		ValueIteration vi = new ValueIteration(
				domain,
				rf,
				tf,
				0.99,
				new SimpleHashableStateFactory(),
				.5, 30);

		// run planning from our initial state
		Policy p = vi.planFromState(initialState);

		// evaluate the policy with one roll out visualize the trajectory
		EpisodeAnalysis ea = p.evaluateBehavior(initialState, rf, tf);

		Visualizer v = gen.getVisualizer();
		new EpisodeSequenceVisualizer(v, domain, Arrays.asList(ea));
	}
	
	private static void runPolicyIteration(EasyGridWorld gen, Domain domain,
			State initialState, RewardFunction rf, TerminalFunction tf) {

		PolicyIteration pi = new PolicyIteration(
				domain,
				rf,
				tf,
				0.99,
				new SimpleHashableStateFactory(),
				.5, 30, 30);

		// run planning from our initial state
		Policy p = pi.planFromState(initialState);

		// evaluate the policy with one roll out visualize the trajectory
		EpisodeAnalysis ea = p.evaluateBehavior(initialState, rf, tf);

		Visualizer v = gen.getVisualizer();
		new EpisodeSequenceVisualizer(v, domain, Arrays.asList(ea));
	}
	
	private static void runQLearning(EasyGridWorld gen, Domain domain,
			State initialState, RewardFunction rf, TerminalFunction tf,SimulatedEnvironment env) {

		QLearning agent = new QLearning(
				domain,
				0.99,
				new SimpleHashableStateFactory(),
				0.1, 0.1);

		// run Q-learning and store results in a list
		List<EpisodeAnalysis> episodes = new ArrayList<EpisodeAnalysis>(1000);
		for (int i = 0; i < 1000; i++) {
			episodes.add(agent.runLearningEpisode(env));
			env.resetEnvironment();
		}

		Visualizer v = gen.getVisualizer();
		new EpisodeSequenceVisualizer(v, domain, episodes);
	}

}
