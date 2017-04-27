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

    private static final int N_NEIGHBORHOOD_STRUCTS = 3;
    private static final int FIRST_NEIGHBORHOOD = 1;
    private static final int STARTING_N_OPERATIONS = 0;

    private GraphMatrix problemGraph;
    private ArrayList<Integer> solution;
    private int nOperations;


    /**
     * Constructor that builds a graph from the content of a text file.
     * This Graph object will be the one used for solving the problem.
     * @param filename Name of the file that specifies the graph.
     */
    public MaxMeanDispersion(String filename) {
        nOperations = 0;
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
            //System.out.println(problemGraph);
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
        setnOperations(STARTING_N_OPERATIONS);
    }

    /**
     * Increments the number of executed operations by one.
     * It should be called each time an operation is executed.
     */
    private void operation() {
        nOperations++;
    }



    /************************************
     *********** GETTERS ************
     ****************************/

    public ArrayList<Integer> getSolution() {
        return solution;
    }

    public void setSolution(ArrayList<Integer> solution) {
        this.solution = solution;
    }

    public GraphMatrix getProblemGraph() {
        return problemGraph;
    }

    public int getnOperations() {
        return nOperations;
    }

    public void setnOperations(int nOperations) {
        this.nOperations = nOperations;
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
     * This method calculates the average dispersion of the given solution.
     * @param solution ArrayList that represents the solution.
     * @return Average dispersion.
     */
    private double averageDispersion(ArrayList<Integer> solution) {
        double numerator = 0.0;
        if (solution.size() == 0)
            return numerator;
        for (int from = 0; from < solution.size() - 1; from++)
            for (int to = from + 1; to < solution.size(); to++) {
                numerator += problemGraph.getAffinity(solution.get(from), solution.get(to));
            }
        return numerator / getSolution().size();
    }

    /**
     * This method returns the non-selected nodes in a given solution.
     * @param solution
     * @return
     */
    private ArrayList<Integer> getExcludedNodes(ArrayList<Integer> solution) {
        ArrayList<Integer> excluded = new ArrayList<>();
        for (int i = 1; i <= getProblemGraph().getSize(); i++)
            if (!solution.contains(i))
                excluded.add(i);
        return excluded;
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
        if ((identifier < 0) || (identifier > getProblemGraph().getSize()))
            return false;
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

        return (currentDispersion <= newDispersion);
    }

    /**
     * This method creates a fast initial solution for the grasp and greedy algorithms.
     * It stores this solution as the solution problem.
     */
    private void fastInitialSolution() {
        ArrayList<Integer> solution = new ArrayList<>();
        // ADD THE LINK WITH THE HIGHEST AFFINITY
        Position highestAffinityLink = getProblemGraph().getHighestAffinityLink();
        solution.add(highestAffinityLink.getX());
        solution.add(highestAffinityLink.getY());
        setSolution(solution);
    }

    /**
     * This method solves the problem using a Greedy Constructive Algorithm. When it finishes, prints the solution on the terminal.
     */
    public void greedyConstructiveAlgorithm() {
        reset();

        // ADD THE LINK WITH THE HIGHEST AFFINITY
        fastInitialSolution();
        operation();

        // LOOK FOR THE NEXT NODE THAT IMPROVES THE SOLUTION
        // ONCE THE SOLUTION IS NOT CHANGED, THE ALGORITHM HAS FINISHED
        boolean changed = true;
        while (changed) {
            int nextBetterNode = getProblemGraph().getBetterNextNode(getSolution());
            operation();
            changed = addIfImproves(nextBetterNode);
            operation();
            if (getSolution().size() == getProblemGraph().getSize())
                changed = false;
        }

        // PRINT THE SOLUTION
        System.out.println("===================================================================");
        System.out.println("SOLUTION: " + getSolution());
        System.out.println("AVERAGE DISPERSION: " + averageDispersion());
        System.out.println("OPERATIONS EXECUTED: " + getnOperations());
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
        operation();

        // LOOK FOR THE NEXT NODE TO ELIMINATE, SO THE SOLUTION CAN BE IMPROVED
        // ONCE THE SOLUTION IS NOT CHANGED, THE ALGORITHM HAS FINISHED
        while (changed) {
            int nextWorstNode = getProblemGraph().getWorstNextNode(discardedCandidates);
            operation();
            changed = checkIfImproves(nextWorstNode, false);
            if (changed) {
                removeFromSolution(nextWorstNode);
                discardedCandidates.add(nextWorstNode);
            }
            if (discardedCandidates.size() == getProblemGraph().getSize())
                changed = false;
            operation();
        }

        // PRINT THE SOLUTION
        System.out.println("===================================================================");
        System.out.println("SOLUTION: " + getSolution());
        System.out.println("AVERAGE DISPERSION: " + averageDispersion());
        System.out.println("OPERATIONS EXECUTED: " + getnOperations());
        System.out.println("===================================================================");
    }


    /**
     * This method solves the problem using a GRASP algorithm. It can also be used to generate an initial solution for more complex algorithms.
     * @param rclSize The size of the Restricted Candidate List.
     * @param improve Indicates if the algorithm should improved each solution with a local search.
     * @param print Indicates if the found solution should be printed on screen.
     */
    public void graspAlgorithm(int rclSize, boolean improve, boolean print) {
        reset();
        Random randSelector = new Random();
        fastInitialSolution();
        operation();

        boolean changed = true;
        while (changed) {
            ArrayList<Integer> rcl = generateRCL(rclSize, new ArrayList<>(getSolution()));
            operation();
            if (rcl.isEmpty())
                break;
            int selectedCandidate = rcl.get(randSelector.nextInt(rcl.size()));
            operation();
            changed = addIfImproves(selectedCandidate);
            operation();
            if ((changed) && (improve)) {
                setSolution(localSearch(getSolution()));
                operation();
            }
        }

        // PRINT THE SOLUTION
        if (print) {
            System.out.println("===================================================================");
            System.out.println("SOLUTION: " + getSolution());
            System.out.println("AVERAGE DISPERSION: " + averageDispersion());
            System.out.println("OPERATIONS EXECUTED: " + getnOperations());
            System.out.println("===================================================================");
        }
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
                return rcl;
            if (checkIfImproves(newCandidate, true))
                rcl.add(newCandidate);
            excluding.add(newCandidate);
        }
        return rcl;
    }

    /**
     * This method solves the problem using a multi-boot algorithm. It generates the specified number of initial solutions, and then performs the local search for each one of them.
     * Lastly, the best solution of all is selected and printed.
     * @param nBoots
     */
    public void multiBootAlgorithm(int nBoots) {
        reset();
        ArrayList<ArrayList<Integer>> bestSolutions = new ArrayList<>();
        for (int i = 0; i < nBoots; i++) {
            graspAlgorithm(2, false, false);
            operation();
            bestSolutions.add(localSearch(getSolution()));
            operation();
        }
        ArrayList<Integer> bestSolution = getSolution();
        for (int i = 0; i < bestSolutions.size(); i++) {
            if (averageDispersion(bestSolutions.get(i)) > averageDispersion(bestSolution)) {
                bestSolution = bestSolutions.get(i);
                operation();
            }
        }

        // PRINT THE SOLUTION
        System.out.println("===================================================================");
        System.out.println("SOLUTION: " + getSolution());
        System.out.println("AVERAGE DISPERSION: " + averageDispersion());
        System.out.println("OPERATIONS EXECUTED: " + getnOperations());
        System.out.println("===================================================================");
    }

    /**
     * This algorithm performs a local search in the neighborhood of the initially given solution.
     * @param solution Initial solution. Starting from this one, the local search will look for a better one.
     * @return ArrayList with the best solution found in the neighborhood.
     */
    private ArrayList<Integer> localSearch(ArrayList<Integer> solution) {
        // FIRST, WE CREATE AN ArrayList WITH ALL NON-SELECTED NODES
        ArrayList<Integer> excluded = getExcludedNodes(solution);

        // START EXPLORING ITS NEIGHBORHOOD
        // HAVING A SOLUTION WITH N ELEMENTS AND OTHER M EXCLUDED NODES
        // WE DEFINE THE NEIGHBORHOOD OF THIS SOLUTION AS ALL SOLUTIONS RESULTING OF ELIMINATING A MEMBER OF THE SOLUTION AND INTRODUCING AN EXCLUDED NODE
        // WE CHECK ALL THIS CASES AND SAVE THE CASE WHERE WE FIND THE HIGHEST DISPERSION
        ArrayList<Integer> bestSolution = new ArrayList<>(solution);
        double dispersion = averageDispersion(bestSolution);
        for (int i = 0; i < solution.size(); i++) {
            for (int j = 0; j < excluded.size(); j++) {
                int saveEliminatedNode = solution.get(i);
                solution.remove(i);
                solution.add(0, excluded.get(j));
                if (averageDispersion(solution) > dispersion) {
                    bestSolution.clear();
                    bestSolution = new ArrayList<>(solution);
                    dispersion = averageDispersion(bestSolution);
                }
                // RESTORE THE ORIGINAL ONE SO WE CAN CONTINUE EXPLORING ITS NEIGHBORHOOD
                solution.remove(0);
                solution.add(saveEliminatedNode);
            }
        }

        // FINALLY, DELIVER THE BEST SOLUTION FOUND
        return bestSolution;
    }


    /**
     * This method solves the problem using a Basic Variable Neighborhood Search algorithm.
     * Having N_NEIGHBORHOOD_STRUCTS neighborhood structures, it shakes and improves with the local search for each one of them until it finds a better one.
     */
    public void basicVNS() {
        reset();

        // GET AN INITIAL SOLUTION PRODUCED BY A GRASP
        final int RCL_SIZE = 3;
        graspAlgorithm(RCL_SIZE, true, false);
        operation();

        // UNTIL THERE ARE NO MORE USEFUL NEIGHBORHOOD STRUCTURES
        int neighborhoodStruct = FIRST_NEIGHBORHOOD;
        while (neighborhoodStruct <= N_NEIGHBORHOOD_STRUCTS) {
            // SHAKE TO GET A RANDOM SOLUTION ON THE GIVEN NEIGHBORHOOD
            ArrayList<Integer> alternativeSolution = shake(getSolution(), neighborhoodStruct);
            operation();
            // PERFORM A LOCAL SEARCH TO IMPROVE IT
            alternativeSolution = localSearch(alternativeSolution);
            operation();
            // IF THE NEW SOLUTION IS BETTER, SET IT AS THE ACTIVE ONE AND GO BACK TO THE FIRST NEIGHBORHOOD
            if (averageDispersion() < averageDispersion(alternativeSolution)) {
                setSolution(new ArrayList<>(alternativeSolution));
                operation();
                neighborhoodStruct = FIRST_NEIGHBORHOOD;
            } else {             // TRY WITH THE NEXT NEIGHBORHOOD
                neighborhoodStruct++;
            }
        }

        // PRINT THE SOLUTION
        System.out.println("===================================================================");
        System.out.println("SOLUTION: " + getSolution());
        System.out.println("AVERAGE DISPERSION: " + averageDispersion());
        System.out.println("OPERATIONS EXECUTED: " + getnOperations());
        System.out.println("===================================================================");
    }

    /**
     * This method produces a random solution in the specified neighborhood of the given solution.
     * @param solution Given solution, from which the new one will be produced.
     * @param kNeighborHoodStruct The used neighborhood structure.
     * @return The generated solution.
     */
    private ArrayList<Integer> shake(ArrayList<Integer> solution, int kNeighborHoodStruct) {
        ArrayList<Integer> newSolution = new ArrayList<>(solution);
        ArrayList<Integer> excluded = getExcludedNodes(newSolution);
        Random randomNode = new Random();

        // IF THERE ARE ANY NODES OUTSIDE OF THE SOLUTION
        if (!excluded.isEmpty()) {
            // CHANGE AS MANY RANDOM NODES AS THE NEIGHBORHOOD STRUCTURE
            for (int i = 0; i < kNeighborHoodStruct; i++) {
                int removedNodeIndex = randomNode.nextInt(newSolution.size());
                int addedNodeIndex = randomNode.nextInt(excluded.size());

                excluded.add(newSolution.get(removedNodeIndex));
                newSolution.add(excluded.get(addedNodeIndex));
                excluded.remove(addedNodeIndex);
                newSolution.remove(removedNodeIndex);
            }
        }
        return newSolution;
    }
}
















//END