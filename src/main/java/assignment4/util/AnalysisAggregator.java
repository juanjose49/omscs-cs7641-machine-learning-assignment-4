package assignment4.util;

import java.util.*;

public final class AnalysisAggregator {
	private static List<Integer> numIterations = new ArrayList<Integer>();
	private static Map<String, Map<Double, Double>> stepsToFinishValueIteration = new LinkedHashMap<>();
	private static Map<String, Map<Double, Double>> stepsToFinishPolicyIteration = new LinkedHashMap<>();
	private static Map<String, Map<Double, Double>> stepsToFinishQLearning = new LinkedHashMap<>();
	
	private static Map<String, Map<Double, Double>> millisecondsToFinishValueIteration = new LinkedHashMap<>();
	private static Map<String, Map<Double, Double>> millisecondsToFinishPolicyIteration = new LinkedHashMap<>();
	private static Map<String, Map<Double, Double>> millisecondsToFinishQLearning = new LinkedHashMap<>();

	private static Map<String, Map<Double, Double>> rewardsForValueIteration = new LinkedHashMap<>();
	private static Map<String, Map<Double, Double>> rewardsForPolicyIteration = new LinkedHashMap<>();
	private static Map<String, Map<Double, Double>> rewardsForQLearning = new LinkedHashMap<>();
	
	public static void addNumberOfIterations(Integer numIterations1){
		numIterations.add(numIterations1);
	}

	public static void addStepsToFinishValueIteration(String key, Double iteration, Double steps) {
		addToMap(stepsToFinishValueIteration, key, iteration, steps);
	}

	public static void addStepsToFinishPolicyIteration(String key, Double iteration, Double steps) {
		addToMap(stepsToFinishPolicyIteration, key, iteration, steps);
	}

	public static void addStepsToFinishQLearning(String key, Double iteration, Double steps) {
		addToMap(stepsToFinishQLearning, key, iteration, steps);
	}

	public static void addMillisecondsToFinishValueIteration(String key, Double iteration, Double milliseconds) {
		addToMap(millisecondsToFinishValueIteration, key, iteration, milliseconds);
	}

	public static void addMillisecondsToFinishPolicyIteration(String key, Double iteration, Double milliseconds) {
		addToMap(millisecondsToFinishPolicyIteration, key, iteration, milliseconds);
	}

	public static void addMillisecondsToFinishQLearning(String key, Double iteration, Double milliseconds) {
		addToMap(millisecondsToFinishQLearning, key, iteration, milliseconds);
	}

	public static void addValueIterationReward(String key, Double iteration, Double reward) {
		addToMap(rewardsForValueIteration, key, iteration, reward);
	}

	public static void addPolicyIterationReward(String key, Double iteration, Double reward) {
		addToMap(rewardsForPolicyIteration, key, iteration, reward);
	}

	public static void addQLearningReward(String key, Double iteration, Double reward) {
		addToMap(rewardsForQLearning, key, iteration, reward);
	}

	public static void printNumIterations() {
		System.out.print("Iterations,");
		printList(numIterations);
	}

	public static void printValueIterationResults() {
		System.out.print("Value Iteration,");
		printResultsMap(stepsToFinishValueIteration);
	}

	public static void printPolicyIterationResults() {
		System.out.print("Policy Iteration,");
		printResultsMap(stepsToFinishPolicyIteration);
	}

	public static void printQLearningResults() {
		System.out.print("Q Learning,");
		printResultsMap(stepsToFinishQLearning);
	}

	public static void printValueIterationTimeResults() {
		System.out.print("Value Iteration,");	
		printResultsMap(millisecondsToFinishValueIteration);
	}

	public static void printPolicyIterationTimeResults() {
		System.out.print("Policy Iteration,");
		printResultsMap(millisecondsToFinishPolicyIteration);
	}

	public static void printQLearningTimeResults() {
		System.out.print("Q Learning,");	
		printResultsMap(millisecondsToFinishQLearning);
	}

	public static void printValueIterationRewards() {
		System.out.print("Value Iteration Rewards,");
		printResultsMap(rewardsForValueIteration);
	}

	public static void printPolicyIterationRewards() {
		System.out.print("Policy Iteration Rewards,");
		printResultsMap(rewardsForPolicyIteration);
	}

	public static void printQLearningRewards() {
		System.out.print("Q Learning Rewards,");
		printResultsMap(rewardsForQLearning);
	}

	private static void printList(List<Integer> valueList) {
		int counter = 0;
		for (int value : valueList) {
			System.out.print(String.valueOf(value));
			if (counter != valueList.size()-1) {
				System.out.print(",");
			}
			counter++;
		}
		System.out.println();
	}

	private static void printResultsMap(Map<String, Map<Double, Double>> resultsMap){
		for(String resultKey : resultsMap.keySet()){
			System.out.println(resultKey + ":");
			Map<Double, Double> valuesMap = resultsMap.get(resultKey);
			int counter = 0;
			for (Double valueKey : valuesMap.keySet()) {
				System.out.print(valuesMap.get(valueKey));
				if(counter != valuesMap.size()-1){
					System.out.print(",");
				}
				counter++;
			}
		}
		System.out.println();
	}

	private static void addToMap(Map<String, Map<Double, Double>> map, String key, Double iteration, Double value) {
		if (!map.containsKey(key)) {
			map.put(key, new LinkedHashMap<>());
		}
		map.get(key).put(iteration, value);
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
		plotResults(numIterations, stepsToFinishValueIteration, stepsToFinishPolicyIteration, stepsToFinishQLearning, "Results", "iterations", "steps");
		plotResults(numIterations, millisecondsToFinishValueIteration, millisecondsToFinishPolicyIteration, millisecondsToFinishQLearning, "Time", "iterations", "time (ms)");
		plotResults(numIterations, rewardsForValueIteration, rewardsForPolicyIteration, rewardsForQLearning, "Rewards", "iterations", "rewards");
	}

	private static void plotResults(List<Integer> numIterations, Map<String, Map<Double, Double>> valueIteration,
									Map<String, Map<Double, Double>> policyIteration, Map<String, Map<Double, Double>> qLearning,
									String plotType, String xAxis, String yAxis) {
		Map<String, Map<Double, Double>> resultCollection = new HashMap<>();
		MutableDouble lowestY = new MutableDouble(0);
		MutableDouble highestY = new MutableDouble(0);

		addToResultCollection(valueIteration, resultCollection, lowestY, highestY, plotType);
		addToResultCollection(policyIteration, resultCollection, lowestY, highestY, plotType);
		addToResultCollection(qLearning, resultCollection, lowestY, highestY, plotType);

		GraphUtils.plotGraph(resultCollection, xAxis, yAxis, 0, numIterations.get(numIterations.size() - 1), lowestY.getValue(), highestY.getValue());
	}

	private static void addToResultCollection(Map<String, Map<Double, Double>> valueIteration, Map<String, Map<Double, Double>> resultCollection,
											  MutableDouble lowestY, MutableDouble highestY, String plotType) {
		for (String valueIterationKey : valueIteration.keySet()) {
			Map<Double, Double> valueIterationValueMap = valueIteration.get(valueIterationKey);
			Map<Double, Double> valueIterationResults = new LinkedHashMap<>();
			for (Double valueIterationValueKey : valueIterationValueMap.keySet()) {
				Double valueIterationValueResult = valueIterationValueMap.get(valueIterationValueKey);
				valueIterationResults.put(valueIterationValueKey, valueIterationValueResult);

				if (lowestY.isGreaterThan(valueIterationValueResult)) {
					lowestY.setValue(valueIterationValueResult);
				}
				if (highestY.isLessThan(valueIterationValueResult)) {
					highestY.setValue(valueIterationValueResult);
				}
			}
			resultCollection.put(valueIterationKey + " " + plotType, valueIterationResults);
		}
	}

	static class MutableDouble {
		private double value;

		public MutableDouble(double value) {
			this.value = value;
		}

		public double getValue() {
			return this.value;
		}

		public void setValue(double value) {
			this.value = value;
		}

		public boolean isLessThan (double value) {
			return this.value < value;
		}

		public boolean isGreaterThan (double value) {
			return this.value > value;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			MutableDouble that = (MutableDouble) o;

			return Double.compare(that.value, value) == 0;
		}

		@Override
		public int hashCode() {
			long temp = Double.doubleToLongBits(value);
			return (int) (temp ^ (temp >>> 32));
		}
	}
}
