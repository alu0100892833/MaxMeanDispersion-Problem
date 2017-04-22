package alu0100892833.daa.max_mean_dispersion_problem.solver;

import alu0100892833.daa.Matrix.Position;
import alu0100892833.daa.max_mean_dispersion_problem.graph.*;

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

    private GraphMatrix problemGraph;
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
            int size = Integer.parseInt(bReader.readLine());
            problemGraph = new GraphMatrix(size);

            for (int i = 1; i < problemGraph.getSize(); i++)
                for (int j = i + 1; j <= problemGraph.getSize(); j++) {
                    double value = Double.parseDouble(bReader.readLine());
                    problemGraph.addLink(i, j, value);
                }
            System.out.println(problemGraph);
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

    public GraphMatrix getProblemGraph() {
        return problemGraph;
    }

    /********************************************/



    /**
     * This method allows to add a new Node (its identifier) to the solution.
     * @param identifier
     */
    private void addToSolution(int identifier) {
        if (!getSolution().contains(identifier))
            getSolution().add(identifier);
    }

    /**
     * This method allows to remove a node from the solution.
     * @param identifier
     */
    private void removeFromSolution(int identifier) {
        if (getSolution().contains(identifier))
            getSolution().remove(getSolution().indexOf(identifier));
    }

    /**
     * This method calculates the average dispersion of the current solution.
     * It is calculated dividing the summation of all solution connection´s affinity by the number of nodes in the solution.
     * @return Average dispersion (Double).
     */
    private double averageDispersion() {
        double numerator = 0.0;
        for (int from = 0; from < getSolution().size() - 1; from++)
            for (int to = from + 1; to < getSolution().size(); to++) {
                numerator += problemGraph.getAffinity(getSolution().get(from), getSolution().get(to));
            }
        if (getSolution().size() == 0)
            return 0.0;
        return numerator / getSolution().size();
    }

    /**
     * This method allows to add a new Node identifier to the solution, just if doing it improves that solution.
     * @param identifier
     * @return a boolean value that specifies if the value was added (true) or not (false)
     */
    private boolean addIfImproves(int identifier) {
        if (checkIfImproves(identifier, true)) {
            addToSolution(identifier);
            return true;
        } else
            return false;
    }

    /**
     * This method allows to check if adding or removing a Node to or from the solution makes it better.
     * @param identifier
     * @param adding boolean value that indicates the function if the Node is candidate to be added or removed.
     * @return True or false, if it improves the solution or not.
     */
    private boolean checkIfImproves(int identifier, boolean adding) {
        double currentDispersion = averageDispersion();
        if (adding)
            addToSolution(identifier);
        else
            removeFromSolution(identifier);
        double newDispersion = averageDispersion();
        if (adding)
            removeFromSolution(identifier);
        else
            addToSolution(identifier);

        return (currentDispersion > newDispersion);
    }

    /**
     * This method solves the problem using a Greedy Constructive Algorithm. When it finishes, prints the solution on the terminal.
     */
    public void greedyConstructiveAlgorithm() {
        reset();

        // ADD THE LINK WITH THE HIGHEST AFFINITY
        Position highestAffinityLink = getProblemGraph().getHighestAffinityLink();
        addToSolution(highestAffinityLink.getX());
        addToSolution(highestAffinityLink.getY());

        // LOOK FOR THE NEXT NODE THAT IMPROVES THE SOLUTION
        // ONCE THE SOLUTION IS NOT CHANGED, THE ALGORITHM HAS FINISHED
        boolean changed = true;
        while (changed) {
            int nextBetterNode = getProblemGraph().getBetterNextNode(getSolution());
            changed = addIfImproves(nextBetterNode);
            if (getSolution().size() == getProblemGraph().getSize())
                changed = false;
        }

        // PRINT THE SOLUTION
        System.out.println("===================================================================");
        System.out.println("GREEDY CONSTRUCTIVE ALGORITHM: " + getSolution());
        System.out.println("AVERAGE DISPERSION: " + averageDispersion());
        System.out.println("===================================================================");
    }

    /**
     * This method solves the problem using a Greedy Destructive Algorithm.
     */
    public void greedyDestructiveAlgorithm() {
        reset();
        boolean changed = true;
        ArrayList<Integer> discardedCandidates = new ArrayList<>();

        // CREATE AN INITIAL SOLUTION WITH ALL NODES OF THE GRAPH
        for (int i = 1; i <= getProblemGraph().getSize(); i++)
            addToSolution(i);

        // REMOVE THE LINK WITH THE LOWEST AFFINITY
        Position lowestAffinityLink = getProblemGraph().getLowestAffinityLink();
        removeFromSolution(lowestAffinityLink.getX());
        removeFromSolution(lowestAffinityLink.getY());
        discardedCandidates.add(lowestAffinityLink.getX());
        discardedCandidates.add(lowestAffinityLink.getY());

        // LOOK FOR THE NEXT NODE TO ELIMINATE, SO THE SOLUTION CAN BE IMPROVED
        // ONCE THE SOLUTION IS NOT CHANGED, THE ALGORITHM HAS FINISHED
        while (changed) {
            int nextWorstNode = getProblemGraph().getWorstNextNode(discardedCandidates);
            changed = checkIfImproves(nextWorstNode, false);
            if (changed) {
                removeFromSolution(nextWorstNode);
                discardedCandidates.add(nextWorstNode);
            }
            if (discardedCandidates.size() == getProblemGraph().getSize())
                changed = false;
        }

        // PRINT THE SOLUTION
        System.out.println("===================================================================");
        System.out.println("GREEDY DESTRUCTIVE ALGORITHM: " + getSolution());
        System.out.println("AVERAGE DISPERSION: " + averageDispersion());
        System.out.println("===================================================================");
    }


    /**
     * This method solves the problem using a GRASP algorithm. It can also be used to generate an initial solution for more complex algorithms.
     * @param rclSize The size of the Restricted Candidate List.
     */
    public void graspAlgorithm(int rclSize) {
        reset();
        Random randSelector = new Random();

        boolean changed = true;
        while (changed) {
            ArrayList<Integer> rcl = generateRCL(rclSize, new ArrayList<>(getSolution()));
            if (rcl == null)
                break;
            int selectedCandidate = rcl.get(randSelector.nextInt(rcl.size()));
            changed = addIfImproves(selectedCandidate);
        }

        // PRINT THE SOLUTION
        System.out.println("===================================================================");
        System.out.println("GRASP ALGORITHM USING A RCL OF " + rclSize + " ELEMENTS: \n" + getSolution());
        System.out.println("AVERAGE DISPERSION: " + averageDispersion());
        System.out.println("===================================================================");
    }

    /**
     * This method generates a Restricted List of Candidates for the GRASP algorithm. It just selects as many elements as indicated by the parameter, using the getBetterNextNode method.
     * @param length Number of elements that will compose the RCL.
     * @param excluding ArrayList of non-selectable Nodes, specified by their identifiers. This ArrayList should be composed by the Nodes that are already part of the solution.
     * @return
     */
    private ArrayList<Integer> generateRCL(int length, ArrayList<Integer> excluding) {
        ArrayList<Integer> rcl = new ArrayList<>();
        while (rcl.size() < length) {
            int newCandidate = getProblemGraph().getBetterNextNode(excluding);
            if (newCandidate == -1)
                return null;
            if (checkIfImproves(newCandidate, true))
                rcl.add(newCandidate);
            excluding.add(newCandidate);
        }
        return rcl;
    }
}
















//END