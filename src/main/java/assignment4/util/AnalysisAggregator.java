package assignment4.util;

import java.util.*;

public final class AnalysisAggregator {
	private static List<Integer> numIterations = new ArrayList<Integer>();
	private static List<Integer> stepsToFinishValueIteration = new ArrayList<Integer>();
	private static List<Integer> stepsToFinishPolicyIteration = new ArrayList<Integer>();
	private static List<Integer> stepsToFinishQLearning = new ArrayList<Integer>();
	
	private static List<Integer> millisecondsToFinishValueIteration = new ArrayList<Integer>();
	private static List<Integer> millisecondsToFinishPolicyIteration = new ArrayList<Integer>();
	private static List<Integer> millisecondsToFinishQLearning = new ArrayList<Integer>();

	private static List<Double> rewardsForValueIteration = new ArrayList<Double>();
	private static List<Double> rewardsForPolicyIteration = new ArrayList<Double>();
	private static List<Double> rewardsForQLearning = new ArrayList<Double>();
	
	public static void addNumberOfIterations(Integer numIterations1){
		numIterations.add(numIterations1);
	}

	public static void addStepsToFinishValueIteration(Integer stepsToFinishValueIteration1){
		stepsToFinishValueIteration.add(stepsToFinishValueIteration1);
	}

	public static void addStepsToFinishPolicyIteration(Integer stepsToFinishPolicyIteration1){
		stepsToFinishPolicyIteration.add(stepsToFinishPolicyIteration1);
	}

	public static void addStepsToFinishQLearning(Integer stepsToFinishQLearning1){
		stepsToFinishQLearning.add(stepsToFinishQLearning1);
	}

	public static void printValueIterationResults(){
		System.out.print("Value Iteration,");	
		printList(stepsToFinishValueIteration);
	}

	public static void printPolicyIterationResults(){
		System.out.print("Policy Iteration,");	
		printList(stepsToFinishPolicyIteration);
	}

	public static void printQLearningResults(){
		System.out.print("Q Learning,");	
		printList(stepsToFinishQLearning);
	}
	
	public static void addMillisecondsToFinishValueIteration(Integer millisecondsToFinishValueIteration1){
		millisecondsToFinishValueIteration.add(millisecondsToFinishValueIteration1);
	}

	public static void addMillisecondsToFinishPolicyIteration(Integer millisecondsToFinishPolicyIteration1){
		millisecondsToFinishPolicyIteration.add(millisecondsToFinishPolicyIteration1);
	}

	public static void addMillisecondsToFinishQLearning(Integer millisecondsToFinishQLearning1){
		millisecondsToFinishQLearning.add(millisecondsToFinishQLearning1);
	}

	public static void addValueIterationReward(double reward) {
		rewardsForValueIteration.add(reward);
	}

	public static void addPolicyIterationReward(double reward) {
		rewardsForPolicyIteration.add(reward);
	}

	public static void addQLearningReward(double reward) {
		rewardsForQLearning.add(reward);
	}

	public static void printValueIterationTimeResults(){
		System.out.print("Value Iteration,");	
		printList(millisecondsToFinishValueIteration);
	}

	public static void printPolicyIterationTimeResults(){
		System.out.print("Policy Iteration,");
		printList(millisecondsToFinishPolicyIteration);
	}

	public static void printQLearningTimeResults(){
		System.out.print("Q Learning,");	
		printList(millisecondsToFinishQLearning);
	}

	public static void printValueIterationRewards(){
		System.out.print("Value Iteration Rewards,");
		printDoubleList(rewardsForValueIteration);
	}

	public static void printPolicyIterationRewards(){
		System.out.print("Policy Iteration Rewards,");
		printDoubleList(rewardsForPolicyIteration);
	}

	public static void printQLearningRewards(){
		System.out.print("Q Learning Rewards,");
		printDoubleList(rewardsForQLearning);
	}

	public static void printNumIterations(){
		System.out.print("Iterations,");	
		printList(numIterations);
	}

	private static void printList(List<Integer> valueList){
		int counter = 0;
		for(int value : valueList){
			System.out.print(String.valueOf(value));
			if(counter != valueList.size()-1){
				System.out.print(",");
			}
			counter++;
		}
		System.out.println();
	}

	private static void printDoubleList(List<Double> valueList){
		int counter = 0;
		for(double value : valueList){
			System.out.print(String.valueOf(value));
			if(counter != valueList.size()-1){
				System.out.print(",");
			}
			counter++;
		}
		System.out.println();
	}

	public static void printAggregateAnalysis(){
		System.out.println("//Aggregate Analysis//\n");
		System.out.println("The data below shows the number of steps/actions the agent required to reach \n"
				+ "the terminal state given the number of iterations the algorithm was run.");
		printNumIterations();
		printValueIterationResults();
		printPolicyIterationResults();
		printQLearningResults();
		System.out.println();
		System.out.println("The data below shows the number of milliseconds the algorithm required to generate \n"
				+ "the optimal policy given the number of iterations the algorithm was run.");
		printNumIterations();
		printValueIterationTimeResults();
		printPolicyIterationTimeResults();
		printQLearningTimeResults();

		System.out.println("\nThe data below shows the total reward gained for \n"
				+ "the optimal policy given the number of iterations the algorithm was run.");
		printNumIterations();
		printValueIterationRewards();
		printPolicyIterationRewards();
		printQLearningRewards();
	}

	public static void plotGraphs(){
		plotResults(numIterations, intToDouble(stepsToFinishValueIteration), intToDouble(stepsToFinishPolicyIteration), intToDouble(stepsToFinishQLearning), "Results", "iterations", "steps");
		plotResults(numIterations, intToDouble(millisecondsToFinishValueIteration), intToDouble(millisecondsToFinishPolicyIteration), intToDouble(millisecondsToFinishQLearning), "Time", "iterations", "time (ms)");
		plotResults(numIterations, rewardsForValueIteration, rewardsForPolicyIteration, rewardsForQLearning, "Rewards", "iterations", "rewards");
	}

	private static List<Double> intToDouble(List<Integer> input) {
		List<Double> output = new ArrayList<>();
		for (Integer inputValue : input) {
			output.add(new Double(inputValue));
		}
		return output;
	}

	private static void plotResults(List<Integer> numIterations, List<Double> valueIteration, List<Double> policyIteration, List<Double> qLearning, String plotType, String xAxis, String yAxis) {
		Map<String, Map<Double, Double>> resultCollection = new HashMap<>();
		double lowestY = 0;
		double highestY = 0;
		Map<Double, Double> valueIterationResults = new LinkedHashMap<>();
		for (int i = 0; i < valueIteration.size(); i++) {
			valueIterationResults.put(new Double(numIterations.get(i)), valueIteration.get(i));
			lowestY = lowestY > valueIteration.get(i) ? valueIteration.get(i) : lowestY;
			highestY = highestY < valueIteration.get(i) ? valueIteration.get(i) : highestY;
		}
		resultCollection.put("Value Iteration " + plotType, valueIterationResults);
		Map<Double, Double> policyIterationResults = new LinkedHashMap<>();
		for (int i = 0; i < policyIteration.size(); i++) {
			policyIterationResults.put(new Double(numIterations.get(i)), policyIteration.get(i));
			lowestY = lowestY > policyIteration.get(i) ? policyIteration.get(i) : lowestY;
			highestY = highestY < policyIteration.get(i) ? policyIteration.get(i) : highestY;
		}
		resultCollection.put("Policy Iteration " + plotType, policyIterationResults);
		Map<Double, Double> qLearningResults = new LinkedHashMap<>();
		for (int i = 0; i < qLearning.size(); i++) {
			qLearningResults.put(new Double(numIterations.get(i)), qLearning.get(i));
			lowestY = lowestY > qLearning.get(i) ? qLearning.get(i) : lowestY;
			highestY = highestY < qLearning.get(i) ? qLearning.get(i) : highestY;
		}
		resultCollection.put("Q-Learning " + plotType, qLearningResults);

		GraphUtils.plotGraph(resultCollection, xAxis, yAxis, 0, numIterations.get(numIterations.size() - 1), lowestY, highestY);
	}
}
