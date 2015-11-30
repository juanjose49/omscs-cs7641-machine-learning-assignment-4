package burlap.assignment4.util;

import burlap.behavior.policy.EpsilonGreedy;
import burlap.behavior.singleagent.auxiliary.performance.LearningAlgorithmExperimenter;
import burlap.behavior.singleagent.auxiliary.performance.PerformanceMetric;
import burlap.behavior.singleagent.auxiliary.performance.TrialMode;
import burlap.behavior.singleagent.learning.LearningAgent;
import burlap.behavior.singleagent.learning.LearningAgentFactory;
import burlap.behavior.singleagent.learning.tdmethods.QLearning;
import burlap.behavior.singleagent.planning.stochastic.policyiteration.PolicyIteration;
import burlap.behavior.singleagent.planning.stochastic.valueiteration.ValueIteration;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.singleagent.environment.SimulatedEnvironment;
import burlap.oomdp.statehashing.SimpleHashableStateFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by James on 11/29/15.
 */
public class AnalysisPlotter {

    final SimpleHashableStateFactory hashingFactory = new SimpleHashableStateFactory();

    private int MAX_ITERATIONS;
    private int NUM_INTERVALS;

    private SimulatedEnvironment env;
    private Domain domain;
    private State initialState;
    private RewardFunction rf;
    private TerminalFunction tf;
    private List<LearningAgentFactory> laFactories;

    public AnalysisPlotter(SimulatedEnvironment env, Domain domain,
                           State initialState, RewardFunction rf, TerminalFunction tf,
                           int MAX_ITERATIONS, int NUM_INTERVALS) {
        this.env = env;
        this.domain = domain;
        this.initialState = initialState;
        this.rf = rf;
        this.tf = tf;
        this.MAX_ITERATIONS = MAX_ITERATIONS;
        this.NUM_INTERVALS = NUM_INTERVALS;

        this.laFactories = new ArrayList<LearningAgentFactory>();
    }

    public void addLearningAgent(String name, String agentName) {
        final String myName = name;
        final String myAgent = agentName;

        LearningAgentFactory learningFactory = new LearningAgentFactory() {
            @Override
            public String getAgentName() {
                return myName;
            }

            @Override
            public LearningAgent generateAgent() {
                LearningAgent agent = new QLearning(domain, .99, hashingFactory, .99, .99);

                if (myAgent.equals("value")) {
                    // agent = new ValueIteration(domain, rf, tf, .99, hashingFactory, -1, MAX_ITERATIONS);
                } else if (myAgent.equals("policy")) {
                    //agent = new PolicyIteration(domain, rf, tf, .99, hashingFactory, -1, 1, MAX_ITERATIONS);
                } else if (myAgent.equals("qepsilon")) {
                    agent = new QLearning(domain, .99, hashingFactory, 0.3, 0.1, "epsilon");
                } else if (myAgent.equals("qboltz")) {
                    agent = new QLearning(domain, .99, hashingFactory, 0.3, 0.1, "boltzmann");
                } else {
                }

                return agent;
            }
        };

        laFactories.add(learningFactory);
    }

    public void runExperiment() {
        for (LearningAgentFactory laf : laFactories) {
            LearningAlgorithmExperimenter exp = new LearningAlgorithmExperimenter(env, 10, 100, laf);

            exp.setUpPlottingConfiguration(500, 250, 2, 1000, TrialMode.MOSTRECENTANDAVERAGE,
                    PerformanceMetric.CUMULATIVESTEPSPEREPISODE, PerformanceMetric.AVERAGEEPISODEREWARD);

            exp.startExperiment();
        }
    }


}
