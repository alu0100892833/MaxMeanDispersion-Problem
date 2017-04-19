package alu0100892833.daa.max_mean_dispersion_problem.solver;

import alu0100892833.daa.max_mean_dispersion_problem.graph.*;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


/**
 * This class instantiates objects that are able to solve de Max-Mean-Dispersion problem.
 * It is necessary to specify a text file with a particular syntax describing the problem.
 * @author Óscar Darias Plasencia
 * @since 18/04/2017
 */
public class MaxMeanDispersion {

    private Graph problemGraph;
    private ArrayList<Integer> solution;


    /**
     * Constructor that builds a graph from the content of a text file.
     * This Graph object will be the one used for solving the problem.
     * @param filename Name of the file that specifies the graph.
     */
    public MaxMeanDispersion(String filename) {
        solution = new ArrayList<>();
        BufferedReader bReader = null;
        try {
            bReader = new BufferedReader(new FileReader(filename));
            problemGraph = new Graph(Integer.parseInt(bReader.readLine()));

            for (int i = 1; i < problemGraph.size(); i++)
                for (int j = i + 1; j <= problemGraph.size(); j++) {
                    int value = Integer.parseInt(bReader.readLine());
                    problemGraph.addLink(i, j, value);
                }
        } catch (IOException e) {
            System.err.println("ERROR WHILE OPENING INPUT FILE.");
            e.printStackTrace();
        } finally {
            if (bReader != null)
                try {
                    bReader.close();
                } catch (IOException e) {
                    System.err.println("ERROR WHILE CLOSING INPUT FILE.");
                    e.printStackTrace();
                }
        }
    }

    /**
     * This method allows to reset the solution, emptying the solution ArrayList.
     */
    public void reset() {
        solution.clear();
    }



    /************************************
     *********** GETTERS ************
     ****************************/

    public ArrayList<Integer> getSolution() {
        return solution;
    }

    public Graph getProblemGraph() {
        return problemGraph;
    }

    /********************************************/



    /**
     * This method allows to add a new Node (its identifier) to the solution.
     * @param identifier
     */
    public void addToSolution(int identifier) {
        if (!getSolution().contains(identifier))
            getSolution().add(identifier);
    }

    public void removeFromSolution(int identifier) {
        if (getSolution().contains(identifier))
            getSolution().remove(getSolution().indexOf(identifier));
    }

    /**
     * This method calculates the average dispersion of the current solution.
     * It is calculated dividing the summation of all solution connection´s affinity by the number of nodes in the solution.
     * @return Average dispersion (Double).
     */
    public double averageDispersion() throws InvalidArgumentException {
        double numerator = 0.0;
        for (int from = 0; from < getSolution().size() - 1; from++)
            for (int to = from + 1; to < getSolution().size(); to++) {
                numerator += problemGraph.getAffinity(getSolution().get(from), getSolution().get(to));
            }
        return numerator / getSolution().size();
    }

    /**
     * This method allows to add a new Node identifier to the solution, just if doing it improves that solution.
     * @param identifier
     * @return a boolean value that specifies if the value was added (true) or not (false)
     * @throws InvalidArgumentException
     */
    public boolean addIfImproves(int identifier) throws InvalidArgumentException {
        double currentDispersion = averageDispersion();
        addToSolution(identifier);
        double newDispersion = averageDispersion();
        if (currentDispersion > newDispersion) {
            removeFromSolution(identifier);
            return false;
        }
        return true;
    }

    /**
     * This method solves the problem using a Greedy Constructive Algorithm. When it finishes, prints the solution on the terminal.
     * @throws InvalidArgumentException
     */
    public void greedyConstructiveAlgorithm() throws InvalidArgumentException {
        reset();

        // ADD THE LINK WITH THE HIGHEST AFFINITY
        Link highestAffinityLink = getProblemGraph().getHighestAffinityLink();
        addToSolution(highestAffinityLink.getFrom().getIdentifier());
        addToSolution(highestAffinityLink.getTo().getIdentifier());

        // LOOK FOR THE NEXT NODE THAT IMPROVES THE SOLUTION
        // ONCE THE SOLUTION IS NOT CHANGED, THE ALGORITHM HAS FINISHED
        boolean changed = true;
        while (changed) {
            Node nextBetterNode = getProblemGraph().getBetterNextNode(getSolution());
            changed = addIfImproves(nextBetterNode.getIdentifier());
        }

        // PRINT THE SOLUTION
        System.out.println("===================================================================");
        System.out.println("GREEDY CONSTRUCTIVE ALGORITHM: " + getSolution());
        System.out.println("AVERAGE DISPERSION: " + averageDispersion());
        System.out.println("===================================================================");
    }


    /**
     * This method solves the problem using a GRASP algorithm. It can also be used to generate an initial solution for more complex algorithms.
     * @param rclSize The size of the Restricted Candidate List.
     * @throws InvalidArgumentException
     */
    public void graspAlgorithm(int rclSize) throws InvalidArgumentException {
        reset();
        Random randSelector = new Random();

        boolean changed = true;
        while (changed) {
            ArrayList<Integer> rcl = getProblemGraph().generateRCL(rclSize, new ArrayList<>(getSolution()));
            if (rcl == null)
                break;
            changed = addIfImproves(rcl.get(randSelector.nextInt(rcl.size())));
        }

        // PRINT THE SOLUTION
        System.out.println("===================================================================");
        System.out.println("GRASP ALGORITHM USING A RCL OF " + rclSize + " ELEMENTS: \n" + getSolution());
        System.out.println("AVERAGE DISPERSION: " + averageDispersion());
        System.out.println("===================================================================");
    }


}
















//END