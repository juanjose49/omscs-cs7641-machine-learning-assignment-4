package burlap.assignment4.easy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import burlap.assignment4.util.AnalysisAggregator;
import burlap.assignment4.util.BasicRewardFunction;
import burlap.assignment4.util.BasicTerminalFunction;
import burlap.assignment4.util.MapPrinter;
import burlap.behavior.policy.Policy;
import burlap.behavior.singleagent.EpisodeAnalysis;
import burlap.behavior.singleagent.auxiliary.EpisodeSequenceVisualizer;
import burlap.behavior.singleagent.learning.tdmethods.QLearning;
import burlap.behavior.singleagent.planning.stochastic.policyiteration.PolicyIteration;
import burlap.behavior.singleagent.planning.stochastic.valueiteration.ValueIteration;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.singleagent.environment.SimulatedEnvironment;
import burlap.oomdp.singleagent.explorer.VisualExplorer;
import burlap.oomdp.statehashing.SimpleHashableStateFactory;
import burlap.oomdp.visualizer.Visualizer;

public class EasyGridWorldLauncher {

	private static boolean visualizeInitialGridWorld = false;
	private static boolean runValueIteration = true;
	private static boolean runPolicyIteration = true;
	private static boolean runQLearning = true;
	
	private static Integer MAX_ITERATIONS = 15;
	private static Integer NUM_INTERVALS = 15;

	

	public static void main(String[] args) {

		EasyGridWorld gen = new EasyGridWorld();
		Domain domain = gen.generateDomain();

		State initialState = EasyGridWorld.getExampleState(domain);

		RewardFunction rf = new BasicRewardFunction(10, 10);
		TerminalFunction tf = new BasicTerminalFunction(10, 10);

		SimulatedEnvironment env = new SimulatedEnvironment(domain, rf, tf,
				initialState);
		//Print the map that is being analyzed
		MapPrinter.printMap(gen.getMap());
		
		if (visualizeInitialGridWorld) {
			visualizeInitialGridWorld(domain, gen, env);
		}
		if(runValueIteration){
			runValueIteration(gen,domain,initialState, rf, tf);
		}
		if(runPolicyIteration){
			runPolicyIteration(gen,domain,initialState, rf, tf);
		}
		if(runQLearning){
			runQLearning(gen,domain,initialState, rf, tf, env);
		}
		AnalysisAggregator.printAggregateAnalysis();
	}


	private static void runValueIteration(EasyGridWorld gen, Domain domain,
			State initialState, RewardFunction rf, TerminalFunction tf) {
		System.out.println("//Value Iteration Analysis//");
		ValueIteration vi = null;
		Policy p = null;
		EpisodeAnalysis ea = null;
		int increment = MAX_ITERATIONS/NUM_INTERVALS;
		for(int numIterations = increment;numIterations<=MAX_ITERATIONS;numIterations+=increment ){
			vi = new ValueIteration(
					domain,
					rf,
					tf,
					0.99,
					new SimpleHashableStateFactory(),
					.99, numIterations); //Added a very high delta number in order to guarantee that value iteration occurs the max number of iterations
										   //for comparison with the other algorithms.
	
			// run planning from our initial state
			p = vi.planFromState(initialState);
	
			// evaluate the policy with one roll out visualize the trajectory
			ea = p.evaluateBehavior(initialState, rf, tf);
			AnalysisAggregator.addNumberOfIterations(numIterations);
			AnalysisAggregator.addStepsToFinishValueIteration(ea.numTimeSteps());
		}
		
//		Visualizer v = gen.getVisualizer();
//		new EpisodeSequenceVisualizer(v, domain, Arrays.asList(ea));
		AnalysisAggregator.printValueIterationResults();
		MapPrinter.printPolicyMap(vi.getAllStates(), p, gen);
		System.out.println("\n\n");
	}
	
	

	private static void runPolicyIteration(EasyGridWorld gen, Domain domain,
			State initialState, RewardFunction rf, TerminalFunction tf) {
		System.out.println("//Policy Iteration Analysis//");
		PolicyIteration pi = null;
		Policy p = null;
		EpisodeAnalysis ea = null;
		int increment = MAX_ITERATIONS/NUM_INTERVALS;
		for(int numIterations = increment;numIterations<=MAX_ITERATIONS;numIterations+=increment ){
			 pi = new PolicyIteration(
					domain,
					rf,
					tf,
					0.99,
					new SimpleHashableStateFactory(),
					.99, 1, numIterations);
	
			// run planning from our initial state
			p = pi.planFromState(initialState);
	
			// evaluate the policy with one roll out visualize the trajectory
			ea = p.evaluateBehavior(initialState, rf, tf);
			AnalysisAggregator.addStepsToFinishPolicyIteration(ea.numTimeSteps());
		}

//		Visualizer v = gen.getVisualizer();
//		new EpisodeSequenceVisualizer(v, domain, Arrays.asList(ea));
		AnalysisAggregator.printPolicyIterationResults();

		MapPrinter.printPolicyMap(pi.getAllStates(), p, gen);
		System.out.println("\n\n");

	}
	
	private static void runQLearning(EasyGridWorld gen, Domain domain,
			State initialState, RewardFunction rf, TerminalFunction tf,SimulatedEnvironment env) {
		System.out.println("//Q Learning Analysis//");

		QLearning agent = null;
		Policy p = null;
		EpisodeAnalysis ea = null;
		int increment = MAX_ITERATIONS/NUM_INTERVALS;
		for(int numIterations = increment;numIterations<=MAX_ITERATIONS;numIterations+=increment ){
			agent = new QLearning(
				domain,
				0.99,
				new SimpleHashableStateFactory(),
				0.99, 0.99);
			
			for (int i = 0; i < numIterations; i++) {
				ea = agent.runLearningEpisode(env);
				env.resetEnvironment();
			}
			agent.initializeForPlanning(rf, tf, 1);
			p = agent.planFromState(initialState);
			AnalysisAggregator.addStepsToFinishQLearning(ea.numTimeSteps());

		}
		AnalysisAggregator.printQLearningResults();
		MapPrinter.printPolicyMap(getAllStates(domain,rf,tf,initialState), p, gen);
		System.out.println("\n\n");

		
//		agent = new QLearning(
//				domain,
//				0.99,
//				new SimpleHashableStateFactory(),
//				0.99, 0.99);
//		agent.initializeForPlanning(rf, tf, 1);
//		p = agent.planFromState(initialState);
//		MapPrinter.printPolicyMap(getAllStates(domain,rf,tf,initialState), p, gen);

//		// run Q-learning and store results in a list
//		List<EpisodeAnalysis> episodes = new ArrayList<EpisodeAnalysis>(1000);
//		for (int i = 0; i < 1000; i++) {
//			episodes.add(agent.runLearningEpisode(env));
//			env.resetEnvironment();
//		}
//		Visualizer v = gen.getVisualizer();
//		new EpisodeSequenceVisualizer(v, domain, episodes);
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
	
	public static List<State> getAllStates(Domain domain,
			 RewardFunction rf, TerminalFunction tf,State initialState){
		ValueIteration vi = new ValueIteration(
				domain,
				rf,
				tf,
				0.99,
				new SimpleHashableStateFactory(),
				.5, 100);
		Policy p = vi.planFromState(initialState);

		return vi.getAllStates();
	}
}
