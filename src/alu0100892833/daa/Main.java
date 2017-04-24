package alu0100892833.daa;

import alu0100892833.daa.max_mean_dispersion_problem.solver.MaxMeanDispersion;
import com.sun.javaws.exceptions.InvalidArgumentException;

public class Main {

    public static void main(String[] args) {
        try {
            MaxMeanDispersion problemSolver = new MaxMeanDispersion(args[0]);

            System.out.println("===================================================================");
            System.out.println("===================================================================");
            System.out.println("===================================================================");
            System.out.println("===================================================================");
            System.out.println();
            System.out.println("====================== GREEDY CONSTRUCTIVE ========================");
            System.out.println("===================================================================");
            problemSolver.greedyConstructiveAlgorithm();

            System.out.println("===================================================================");
            System.out.println("===================================================================");
            System.out.println("===================================================================");
            System.out.println("===================================================================");
            System.out.println();
            System.out.println("===================== GREEDY DESTRUCTIVE ==========================");
            problemSolver.greedyDestructiveAlgorithm();

            System.out.println("===================================================================");
            System.out.println("===================================================================");
            for (int rcl = 2; rcl <= 16; rcl += rcl) {
                System.out.println("===================================================================");
                System.out.println("===================================================================");
                System.out.println();
                System.out.println("============= GRASP WITH " + rcl + " CANDIDATES ===================");
                for (int i = 1; i <= 5; i++) {
                    System.out.println("# EXECUTION " + i);
                    problemSolver.graspAlgorithm(rcl, true);
                }
            }

            System.out.println("===================================================================");
            System.out.println("===================================================================");
            for (int boots = 2; boots <= 32; boots += boots) {
                System.out.println("===================================================================");
                System.out.println("===================================================================");
                System.out.println();
                System.out.println("============= MULTI - BOOT WITH " + boots + " BOOTS ===============");
                for (int i = 1; i <= 5; i++) {
                    System.out.println("# EXECUTION " + i);
                    problemSolver.multiBootAlgorithm(boots);
                }
            }

        } catch(IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
