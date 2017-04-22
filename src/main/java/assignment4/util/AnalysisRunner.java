package assignment4.util;

import assignment4.BasicGridWorld;
import burlap.behavior.policy.Policy;
import burlap.behavior.singleagent.EpisodeAnalysis;
import burlap.behavior.singleagent.auxiliary.StateReachability;
import burlap.behavior.singleagent.auxiliary.valuefunctionvis.ValueFunctionVisualizerGUI;
import burlap.behavior.singleagent.learning.tdmethods.QLearning;
import burlap.behavior.singleagent.planning.stochastic.policyiteration.PolicyIteration;
import burlap.behavior.singleagent.planning.stochastic.valueiteration.ValueIteration;
import burlap.behavior.valuefunction.ValueFunction;
import burlap.domain.singleagent.gridworld.GridWorldDomain;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.singleagent.SADomain;
import burlap.oomdp.singleagent.environment.SimulatedEnvironment;
import burlap.oomdp.statehashing.HashableStateFactory;
import burlap.oomdp.statehashing.SimpleHashableStateFactory;

import java.util.List;

public class AnalysisRunner {

	final SimpleHashableStateFactory hashingFactory = new SimpleHashableStateFactory();

	private int MAX_ITERATIONS;
	private int NUM_INTERVALS;
	private int MAX_STEPS;

	public AnalysisRunner(int MAX_ITERATIONS, int NUM_INTERVALS, int MAX_STEPS){
		this.MAX_ITERATIONS = MAX_ITERATIONS;
		this.NUM_INTERVALS = NUM_INTERVALS;
		this.MAX_STEPS = MAX_STEPS;
		
		int increment = MAX_ITERATIONS/NUM_INTERVALS;
		for(int numIterations = increment;numIterations<=MAX_ITERATIONS;numIterations+=increment ){
			AnalysisAggregator.addNumberOfIterations(numIterations);
		}
	}

	public void runValueIteration(BasicGridWorld gen, Domain domain, State initialState, RewardFunction rf, TerminalFunction tf, boolean showPolicyMap) {
		double[] gammas = new double[] { 0.99 };
		for (double gamma : gammas) {
			runValueIteration(gen, domain, initialState, rf, tf, gamma, showPolicyMap);
		}
	}

	private void runValueIteration(BasicGridWorld gen, Domain domain, State initialState, RewardFunction rf, TerminalFunction tf, double gamma, boolean showPolicyMap) {
		System.out.println("//Value Iteration Analysis//");
		ValueIteration vi = null;
		Policy p = null;
		EpisodeAnalysis ea = null;
		int increment = MAX_ITERATIONS/NUM_INTERVALS;
		for(int numIterations = increment;numIterations<=MAX_ITERATIONS;numIterations+=increment ){
			long startTime = System.currentTimeMillis();
			//Added a very high delta number in order to guarantee that value iteration occurs the max number of iterations for comparison with the other algorithms.
			vi = new ValueIteration(domain, rf, tf, gamma, hashingFactory, -1, numIterations);

			// run planning from our initial state
			p = vi.planFromState(initialState);
			AnalysisAggregator.addMillisecondsToFinishValueIteration("VI " + gamma, (double) numIterations, (double) (System.currentTimeMillis()-startTime));

			// evaluate the policy with one roll out visualize the trajectory
			ea = p.evaluateBehavior(initialState, rf, tf, MAX_STEPS);
			AnalysisAggregator.addValueIterationReward("VI " + gamma, (double) numIterations, calcRewardInEpisode(ea));
			AnalysisAggregator.addStepsToFinishValueIteration("VI " + gamma, (double) numIterations, (double) ea.numTimeSteps());
		}
		
//		Visualizer v = gen.getVisualizer();
//		new EpisodeSequenceVisualizer(v, domain, Arrays.asList(ea));
		AnalysisAggregator.printValueIterationResults();
		MapPrinter.printPolicyMap(vi.getAllStates(), p, gen.getMap());
		System.out.println("\n\n");
		if(showPolicyMap){
			simpleValueFunctionVis((ValueFunction)vi, p, initialState, domain, hashingFactory, "Value Iteration");
		}
	}

	public void runPolicyIteration(BasicGridWorld gen, Domain domain, State initialState, RewardFunction rf, TerminalFunction tf, boolean showPolicyMap) {
		double[] gammas = new double[] { 0.99 };
		for (double gamma : gammas) {
			runPolicyIteration(gen, domain, initialState, rf, tf, gamma, showPolicyMap);
		}
	}

	private void runPolicyIteration(BasicGridWorld gen, Domain domain, State initialState, RewardFunction rf, TerminalFunction tf, double gamma, boolean showPolicyMap) {
		System.out.println("//Policy Iteration Analysis//");
		PolicyIteration pi = null;
		Policy p = null;
		EpisodeAnalysis ea = null;
		int increment = MAX_ITERATIONS/NUM_INTERVALS;
		for(int numIterations = increment;numIterations<=MAX_ITERATIONS;numIterations+=increment ){
			long startTime = System.currentTimeMillis();
			pi = new PolicyIteration(domain, rf, tf, gamma, hashingFactory, -1, 1, numIterations);
	
			// run planning from our initial state
			p = pi.planFromState(initialState);
			AnalysisAggregator.addMillisecondsToFinishPolicyIteration("PI " + gamma, (double) numIterations, (double) (System.currentTimeMillis()-startTime));

			// evaluate the policy with one roll out visualize the trajectory
			ea = p.evaluateBehavior(initialState, rf, tf, MAX_STEPS);
			AnalysisAggregator.addPolicyIterationReward("PI " + gamma, (double) numIterations, calcRewardInEpisode(ea));
			AnalysisAggregator.addStepsToFinishPolicyIteration("PI " + gamma, (double) numIterations, (double) ea.numTimeSteps());
		}

//		Visualizer v = gen.getVisualizer();
//		new EpisodeSequenceVisualizer(v, domain, Arrays.asList(ea));
		AnalysisAggregator.printPolicyIterationResults();

		MapPrinter.printPolicyMap(getAllStates(domain,rf,tf,initialState), p, gen.getMap());
		System.out.println("\n\n");

		//visualize the value function and policy.
		if(showPolicyMap){
			simpleValueFunctionVis(pi, p, initialState, domain, hashingFactory, "Policy Iteration");
		}
	}

	public void simpleValueFunctionVis(ValueFunction valueFunction, Policy p, 
			State initialState, Domain domain, HashableStateFactory hashingFactory, String title){

		List<State> allStates = StateReachability.getReachableStates(initialState,
				(SADomain)domain, hashingFactory);
		ValueFunctionVisualizerGUI gui = GridWorldDomain.getGridWorldValueFunctionVisualization(
				allStates, valueFunction, p);
		gui.setTitle(title);
		gui.initGUI();
	}

	public void runQLearning(BasicGridWorld gen, Domain domain, State initialState, RewardFunction rf, TerminalFunction tf, SimulatedEnvironment env, boolean showPolicyMap) {
		double[] gammas = new double[] { 0.99 };
		double[] lrs = new double[] { 0.99 };
		for (double gamma : gammas) {
			for (double lr : lrs) {
				runQLearning(gen, domain, initialState, rf, tf, env, gamma, lr, showPolicyMap);
			}
		}
	}

	private void runQLearning(BasicGridWorld gen, Domain domain, State initialState, RewardFunction rf, TerminalFunction tf, SimulatedEnvironment env, double gamma, double learningRate, boolean showPolicyMap) {
		System.out.println("//Q Learning Analysis//");

		QLearning agent = null;
		Policy p = null;
		EpisodeAnalysis ea = null;
		int increment = MAX_ITERATIONS/NUM_INTERVALS;
		for(int numIterations = increment;numIterations<=MAX_ITERATIONS;numIterations+=increment ){
			long startTime = System.currentTimeMillis();

			agent = new QLearning(domain, gamma, hashingFactory, 0.99, learningRate, MAX_STEPS);
			
			for (int i = 0; i < numIterations; i++) {
				ea = agent.runLearningEpisode(env);
				env.resetEnvironment();
			}
			agent.initializeForPlanning(rf, tf, 1);
			p = agent.planFromState(initialState);
			AnalysisAggregator.addQLearningReward("QL " + gamma + " " + learningRate, (double) numIterations, calcRewardInEpisode(ea));
			AnalysisAggregator.addMillisecondsToFinishQLearning("QL " + gamma + " " + learningRate, (double) numIterations, (double) (System.currentTimeMillis()-startTime));
			AnalysisAggregator.addStepsToFinishQLearning("QL " + gamma + " " + learningRate, (double) numIterations, (double) ea.numTimeSteps());
		}
		AnalysisAggregator.printQLearningResults();
		MapPrinter.printPolicyMap(getAllStates(domain,rf,tf,initialState), p, gen.getMap());
		System.out.println("\n\n");

		//visualize the value function and policy.
		if(showPolicyMap){
			simpleValueFunctionVis((ValueFunction)agent, p, initialState, domain, hashingFactory, "Q-Learning");
		}
	}
	
	private static List<State> getAllStates(Domain domain, RewardFunction rf, TerminalFunction tf, State initialState) {
		ValueIteration vi = new ValueIteration(domain, rf, tf, 0.99, new SimpleHashableStateFactory(), .5, 100);
		vi.planFromState(initialState);

		return vi.getAllStates();
	}
	
	public double calcRewardInEpisode(EpisodeAnalysis ea) {
		double myRewards = 0;

		//sum all rewards
		for (int i = 0; i<ea.rewardSequence.size(); i++) {
			myRewards += ea.rewardSequence.get(i);
		}
		return myRewards;
	}
}
