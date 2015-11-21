package burlap.assignment4.util;

import java.util.ArrayList;
import java.util.List;

public final class AnalysisAggregator {
	private static List<Integer> numIterations = new ArrayList<Integer>();
	private static List<Integer> stepsToFinishValueIteration = new ArrayList<Integer>();
	private static List<Integer> stepsToFinishPolicyIteration = new ArrayList<Integer>();
	private static List<Integer> stepsToFinishQLearning = new ArrayList<Integer>();
	
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
	public static void printNumIterations(){
		System.out.print("Iterations,");	
		printList(numIterations);
	}
	private static void printList(List<Integer> valueList){
		for(int value : valueList){
			System.out.print(String.valueOf(value));
			if(value != valueList.get(valueList.size()-1)){
				System.out.print(",");
			}
		}
		System.out.println();
	}
	public static void printAggregateAnalysis(){
		System.out.println("//Aggregate Analysis//");
		System.out.println("This is your aggregate analysis:");
		printNumIterations();
		printValueIterationResults();
		printPolicyIterationResults();
		printQLearningResults();
	}
}
