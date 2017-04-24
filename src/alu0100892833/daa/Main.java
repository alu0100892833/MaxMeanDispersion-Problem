package alu0100892833.daa;

import alu0100892833.daa.max_mean_dispersion_problem.solver.MaxMeanDispersion;
import com.sun.javaws.exceptions.InvalidArgumentException;

public class Main {

    public static void main(String[] args) {
        try {
            MaxMeanDispersion problemSolver = new MaxMeanDispersion(args[0]);

            final int EXECUTIONS = 5;
            final int MINIMUM_RCL = 2;
            final int MAXIMUM_RCL = 5;
            final int MINIMUM_N_BOOTS = 2;
            final int MAXIMUM_N_BOOTS = 32;

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
            for (int rcl = MINIMUM_RCL; rcl <= MAXIMUM_RCL; rcl++) {
                System.out.println("===================================================================");
                System.out.println("===================================================================");
                System.out.println();
                System.out.println("============= GRASP WITH " + rcl + " CANDIDATES ===================");
                for (int i = 1; i <= EXECUTIONS; i++) {
                    System.out.println("# EXECUTION " + i);
                    problemSolver.graspAlgorithm(rcl, true);
                }
            }



            System.out.println("===================================================================");
            System.out.println("===================================================================");
            for (int boots = MINIMUM_N_BOOTS; boots <= MAXIMUM_N_BOOTS; boots += boots) {
                System.out.println("===================================================================");
                System.out.println("===================================================================");
                System.out.println();
                System.out.println("============= MULTI - BOOT WITH " + boots + " BOOTS ===============");
                for (int i = 1; i <= EXECUTIONS; i++) {
                    System.out.println("# EXECUTION " + i);
                    problemSolver.multiBootAlgorithm(boots);
                }
            }


            System.out.println("===================================================================");
            System.out.println("===================================================================");
            System.out.println("===================================================================");
            System.out.println("===================================================================");
            System.out.println();
            System.out.println("========================== BASIC VNS ==============================");
            for (int i = 1; i <= EXECUTIONS; i++) {
                System.out.println("# EXECUTION " + i);
                problemSolver.basicVNS();
            }


        } catch(IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
