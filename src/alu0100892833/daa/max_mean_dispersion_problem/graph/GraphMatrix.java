package alu0100892833.daa.max_mean_dispersion_problem.graph;

import alu0100892833.daa.Matrix.Matrix;
import alu0100892833.daa.Matrix.Position;

import java.util.ArrayList;

/**
 * This class allows to represent Graphs using an internal Matrix object.
 * This makes it much simpler to access each connection´s affinity between nodes.
 * @author Óscar Darias Plasencia
 * @since 19-4-2017
 */
public class GraphMatrix {

    private Matrix data;
    int rows, cols;


    /**
     * Constructor specifying the number of nodes that the graph will have.
     * @param nNodes
     */
    public GraphMatrix(int nNodes) {
        this.rows = nNodes;
        this.cols = nNodes;
        data = new Matrix(nNodes, nNodes);
    }

    /**
     * Specifies the size of the Graph.
     * @return Number of nodes of the Graph.
     */
    public int getSize() {
        return data.getM();
    }

    /**
     * This method adds a new Link between two Node objects of the Graph.
     * It calls the addLink method from the Node class.
     * @param from The one Node of the Link.
     * @param to The other Node of the Link.
     * @param affinity The affinity that the Link will have set.
     */
    public void addLink(int from, int to, int affinity) {
        data.set(from - 1, to - 1, affinity);
        data.set(to - 1, from - 1, affinity);
    }

    /**
     * Obtain the affinity of the Link between the two given Node objects.
     * @param firstNode
     * @param secondNode
     * @return
     */
    public double getAffinity(int firstNode, int secondNode) {
        return data.get(firstNode - 1, secondNode - 1);
    }

    /**
     * Obtain the position of the matrix where the highest value is set. In other words, returns the link between two nodes with the maximum affinity of the entire graph.
     * @return
     */
    public Position getHighestAffinityLink() {
        Position highestAffinityPos = new Position(-1, -1);
        double highestAffinity = Double.NEGATIVE_INFINITY;
        for (int i = 1; i <= getSize(); i++)
            for (int j = i + 1; j <= getSize(); j++) {
                if (getAffinity(i, j) > highestAffinity) {
                    highestAffinity = getAffinity(i, j);
                    highestAffinityPos.set(i, j);
                }
            }
        return highestAffinityPos;
    }

    /**
     * Obtain the position of the matrix where the lowest value is set. In other words, returns the link between two nodes with the minimum affinity of the entire graph.
     * @return
     */
    public Position getLowestAffinityLink() {
        Position lowestAffinityPos = new Position(-1, -1);
        double lowestAffinity = Double.POSITIVE_INFINITY;
        for (int i = 1; i < getSize(); i++)
            for (int j = i + 1; j <= getSize(); j++) {
                if (getAffinity(i, j) < lowestAffinity) {
                    lowestAffinity = getAffinity(i, j);
                    lowestAffinityPos.set(i, j);
                }
            }
        return lowestAffinityPos;
    }

    /**
     * Obtain the best node that could possibly be added to the solution, ignoring the nodes specified in the excluding ArrayList.
     * @param excluding This ArrayList should be composed by all nodes that are already a part of the current solution.
     * @return
     */
    public int getBetterNextNode(ArrayList<Integer> excluding) {
        int betterNode = -1;
        double affinity = Double.NEGATIVE_INFINITY;
        if (excluding.size() == 0)
            return getHighestAffinityLink().getX();
        if (excluding.size() == 1)
            return getHighestAffinityLink().getY();
        for (int from = 0; from < excluding.size(); from++)
            for (int to = 1; to <= getSize(); to++)
                if ((getAffinity(excluding.get(from), to) > affinity) && (!excluding.contains(to))) {
                    betterNode = to;
                    affinity = getAffinity(excluding.get(from), to);
                }
        return betterNode;
    }

    /**
     * Obtain the best node that could possibly be removed from the solution, ignoring the nodes specified in the excluding ArrayList.
     * @param excluding This ArrayList should be composed by all nodes that have already been eliminated from the solution.
     * @return
     */
    public int getWorstNextNode(ArrayList<Integer> excluding) {
        int worstNode = -1;
        double affinity = Double.POSITIVE_INFINITY;

        for (int from = 0; from < excluding.size(); from++)
            for (int to = 1; to <= getSize(); to++)
                if ((getAffinity(excluding.get(from), to) < affinity) && (!excluding.contains(to))) {
                    worstNode = to;
                    affinity = getAffinity(excluding.get(from), to);
                }
        return worstNode;
    }

    @Override
    public String toString() {
        return data.toString();
    }


}


















//END