package burlap.assignment4.util;

import java.util.List;

import burlap.assignment4.BasicGridWorld;
import burlap.behavior.policy.Policy;
import burlap.behavior.singleagent.EpisodeAnalysis;
import burlap.behavior.singleagent.learning.tdmethods.QLearning;
import burlap.behavior.singleagent.planning.stochastic.policyiteration.PolicyIteration;
import burlap.behavior.singleagent.planning.stochastic.valueiteration.ValueIteration;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.singleagent.environment.SimulatedEnvironment;
import burlap.oomdp.statehashing.SimpleHashableStateFactory;

public class AnalysisRunner {

	private int MAX_ITERATIONS;
	private int NUM_INTERVALS;

	public AnalysisRunner(int MAX_ITERATIONS, int NUM_INTERVALS){
		this.MAX_ITERATIONS = MAX_ITERATIONS;
		this.NUM_INTERVALS = NUM_INTERVALS;
		
	}
	public void runValueIteration(BasicGridWorld gen, Domain domain,
			State initialState, RewardFunction rf, TerminalFunction tf) {
		System.out.println("//Value Iteration Analysis//");
		ValueIteration vi = null;
		Policy p = null;
		EpisodeAnalysis ea = null;
		int increment = MAX_ITERATIONS/NUM_INTERVALS;
		for(int numIterations = increment;numIterations<=MAX_ITERATIONS;numIterations+=increment ){
			long startTime = System.nanoTime();
			vi = new ValueIteration(
					domain,
					rf,
					tf,
					0.99,
					new SimpleHashableStateFactory(),
					0, numIterations); //Added a very high delta number in order to guarantee that value iteration occurs the max number of iterations
										   //for comparison with the other algorithms.
	
			// run planning from our initial state
			p = vi.planFromState(initialState);
			AnalysisAggregator.addMillisecondsToFinishValueIteration((int) (System.nanoTime()-startTime)/1000000);

			// evaluate the policy with one roll out visualize the trajectory
			ea = p.evaluateBehavior(initialState, rf, tf);
			AnalysisAggregator.addNumberOfIterations(numIterations);
			AnalysisAggregator.addStepsToFinishValueIteration(ea.numTimeSteps());
		}
		
//		Visualizer v = gen.getVisualizer();
//		new EpisodeSequenceVisualizer(v, domain, Arrays.asList(ea));
		AnalysisAggregator.printValueIterationResults();
		MapPrinter.printPolicyMap(vi.getAllStates(), p, gen.getMap());
		System.out.println("\n\n");
	}
	
	

	public void runPolicyIteration(BasicGridWorld gen, Domain domain,
			State initialState, RewardFunction rf, TerminalFunction tf) {
		System.out.println("//Policy Iteration Analysis//");
		PolicyIteration pi = null;
		Policy p = null;
		EpisodeAnalysis ea = null;
		int increment = MAX_ITERATIONS/NUM_INTERVALS;
		for(int numIterations = increment;numIterations<=MAX_ITERATIONS;numIterations+=increment ){
			long startTime = System.nanoTime();
			 pi = new PolicyIteration(
					domain,
					rf,
					tf,
					0.99,
					new SimpleHashableStateFactory(),
					0, 1, numIterations);
	
			// run planning from our initial state
			p = pi.planFromState(initialState);
			AnalysisAggregator.addMillisecondsToFinishPolicyIteration((int) (System.nanoTime()-startTime)/1000000);

			// evaluate the policy with one roll out visualize the trajectory
			ea = p.evaluateBehavior(initialState, rf, tf);
			AnalysisAggregator.addStepsToFinishPolicyIteration(ea.numTimeSteps());
		}

//		Visualizer v = gen.getVisualizer();
//		new EpisodeSequenceVisualizer(v, domain, Arrays.asList(ea));
		AnalysisAggregator.printPolicyIterationResults();

		MapPrinter.printPolicyMap(pi.getAllStates(), p, gen.getMap());
		System.out.println("\n\n");

	}
	
	public void runQLearning(BasicGridWorld gen, Domain domain,
			State initialState, RewardFunction rf, TerminalFunction tf,SimulatedEnvironment env) {
		System.out.println("//Q Learning Analysis//");

		QLearning agent = null;
		Policy p = null;
		EpisodeAnalysis ea = null;
		int increment = MAX_ITERATIONS/NUM_INTERVALS;
		for(int numIterations = increment;numIterations<=MAX_ITERATIONS;numIterations+=increment ){
			long startTime = System.nanoTime();

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
			AnalysisAggregator.addMillisecondsToFinishQLearning((int) (System.nanoTime()-startTime)/1000000);
			AnalysisAggregator.addStepsToFinishQLearning(ea.numTimeSteps());

		}
		AnalysisAggregator.printQLearningResults();
		MapPrinter.printPolicyMap(getAllStates(domain,rf,tf,initialState), p, gen.getMap());
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
	
	private static List<State> getAllStates(Domain domain,
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
