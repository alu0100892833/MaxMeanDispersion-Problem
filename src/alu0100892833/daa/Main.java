package alu0100892833.daa;

import alu0100892833.daa.max_mean_dispersion_problem.solver.MaxMeanDispersion;
import com.sun.javaws.exceptions.InvalidArgumentException;

public class Main {
    private static final int SMALL_RCL = 2;
    private static final int BIG_RCL = 3;

    public static void main(String[] args) {
        try {
            MaxMeanDispersion problemSolver = new MaxMeanDispersion(args[0]);
            problemSolver.greedyConstructiveAlgorithm();
            problemSolver.greedyDestructiveAlgorithm();
            problemSolver.graspAlgorithm(BIG_RCL);
            problemSolver.graspAlgorithm(SMALL_RCL);
            problemSolver.multiBootAlgorithm(4);
        } catch(IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
