package main.java.exercise;

import main.java.framework.Report;
import main.java.framework.Timer;
import main.java.framework.Verifier;

public class VerifierImplementation extends Verifier<InstanceImplementation, StudentSolutionImplementation, ResultImplementation> {

    @Override
    public ResultImplementation solveProblemUsingStudentSolution(InstanceImplementation instance, StudentSolutionImplementation studentSolution) {
        if (instance.getGroupName().equals("Indexsuche")) {
            Timer timer = new Timer();
            timer.start();
            int max = studentSolution.findIndex(instance.getNumbers(), instance.getElement());
            timer.stop();
            return new ResultImplementation(instance.getGroupName(), timer.getDuration(), instance.getNumbers().length, max, null, false, null);
        } else if (instance.getGroupName().equals("Gale Shapley")) {
            StableMatchingSolution solution = new StableMatchingSolution(instance.getStableMatchingInstance(), studentSolution);
            Timer timer = new Timer();
            timer.start();
            studentSolution.performGaleShapley(instance.getStableMatchingInstance(), solution);
            timer.stop();
            return new ResultImplementation(instance.getGroupName(), timer.getDuration(), instance.getStableMatchingInstance().getN(), -1, solution, false, null);
        } else if (instance.getGroupName().equals("Stable Matching Positiv") || instance.getGroupName().equals("Stable Matching Negativ")) {
            StableMatchingSolution solution = new StableMatchingSolution(instance.getStableMatchingInstance(), studentSolution);
            Timer timer = new Timer();
            timer.start();
            boolean solutionStable = studentSolution.isStableMatching(instance.getStableMatchingInstance(), instance.getSolutionStableCandit());
            timer.stop();
            return new ResultImplementation(instance.getGroupName(), timer.getDuration(), instance.getStableMatchingInstance().getN(), -1, null, solutionStable, null);
        } else {
            Timer timer = new Timer();
            timer.start();
            StableMatchingSolution solution = BruteForce.performBruteForce(instance.getStableMatchingInstance(), studentSolution);
            timer.stop();
            return new ResultImplementation(instance.getGroupName(), timer.getDuration(), instance.getStableMatchingInstance().getN(), -1, null, false, solution);
        }
    }

    @Override
    public Report verifyResult(InstanceImplementation instance, ResultImplementation result) {
        if (instance.getGroupName().equals("Indexsuche")) {
            if (instance.getSolutionIndex() == result.getSolutionIndex()) {
                return new Report(true, "");
            } else {
                return new Report(false, "Error in instance " + instance.getNumber() + ": Index value expected to be " + instance.getSolutionIndex() + " but was " + result.getSolutionIndex() + ".");
            }
        } else if (instance.getGroupName().equals("Gale Shapley")) {
            if (instance.getSolutionGaleShapley().equals(result.getSolutionGaleShapley())) {
                return new Report(true, "");
            } else {
                return new Report(false, "Error in instance " + instance.getNumber() + ": Stable Matching (Gale Shapley) expected to be\n" + instance.getSolutionGaleShapley().toString() + " but was\n" + result.getSolutionGaleShapley().toString() + ".");
            }
        } if (instance.getGroupName().equals("Stable Matching Positiv") || instance.getGroupName().equals("Stable Matching Negativ")) {
            if (instance.getSolutionStable() == result.getSolutionStable()) {
                return new Report(true, "");
            } else {
                return new Report(false, "Error in instance " + instance.getNumber() + ": Test for Stable Matching expected to be " + instance.getSolutionStable() + " but was " + result.getSolutionStable() + ".");
            }
        } else {
            if (instance.getSolutionBruteForce().equals(result.getSolutionBruteForce())) {
                return new Report(true, "");
            } else {
                return new Report(false, "Error in instance " + instance.getNumber() + ": Stable Matching (Brute Force) expected to be\n" + instance.getSolutionBruteForce().toString() + " but was\n" + result.getSolutionBruteForce().toString() + ".");
            }
        }
    }
}
