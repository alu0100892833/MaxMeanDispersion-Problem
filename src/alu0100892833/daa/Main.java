package alu0100892833.daa;

import alu0100892833.daa.max_mean_dispersion_problem.solver.MaxMeanDispersion;

public class Main {

    public static void main(String[] args) {
        try {
            MaxMeanDispersion problemSolver = new MaxMeanDispersion(args[0]);
        } catch(IndexOutOfBoundsException e) {
            System.err.println("YOU NEED TO SPECIFY THE PROBLEM´S FILE NAME.");
            e.printStackTrace();
        }
    }
}
