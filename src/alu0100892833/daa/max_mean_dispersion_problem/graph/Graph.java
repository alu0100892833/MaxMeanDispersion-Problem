package alu0100892833.daa.max_mean_dispersion_problem.graph;

import com.sun.javaws.exceptions.InvalidArgumentException;
import org.omg.CORBA.DynAnyPackage.Invalid;

import java.util.ArrayList;

/**
 * This class allows to represent Graphs adapted to work for the Max-Mean-Dispersion Problem solver.
 * It saves an ArrayList of Node objects.
 * @author Óscar Darias Plasencia
 * @since 18/04/2017
 * @deprecated
 */
public class Graph {

    private final double COST_TO_SELF = 0.0;

    private ArrayList<Node> nodes;

    /**
     * This constructor initializes a Graph object with the given number of nodes, unconnected.
     * @param nNodes Number of nodes.
     */
    public Graph(int nNodes) {
        nodes = new ArrayList<>();
        for (int i = 1; i <= nNodes; i++) {
            nodes.add(new Node(i));
        }
    }

    /**
     * This method allows to obtain a Node object using its identifier.
     * @param identifier
     * @return
     */
    private Node getNode(int identifier) {
        if (nodes.contains(new Node(identifier)))
            for (Node node : nodes)
                if (node.getIdentifier() == identifier)
                    return node;
        return null;
    }

    /**
     * Specifies the size of the Graph.
     * @return Number of nodes of the Graph.
     */
    public int size() {
        return nodes.size();
    }


    @Deprecated
    public void addNode(int identifier) {
        Node newNode = new Node(identifier);
        if (!nodes.contains(newNode))
            nodes.add(newNode);
    }

    /**
     * This method adds a new Link between two Node objects of the Graph.
     * It calls the addLink method from the Node class.
     * @param from The one Node of the Link.
     * @param to The other Node of the Link.
     * @param affinity The affinity that the Link will have set.
     */
    public void addLink(int from, int to, int affinity) {
        try {
            getNode(from).addLink(getNode(to), affinity);
        } catch (NullPointerException e) {
            System.err.println("ERROR WHILE ADDING A LINK TO NODE ---> " + from);
            e.printStackTrace();
        }
    }

    /**
     * Obtain the affinity of the Link between the two given Node objects.
     * @param firstNode
     * @param secondNode
     * @return
     * @throws InvalidArgumentException
     */
    public double getAffinity(int firstNode, int secondNode) throws InvalidArgumentException {
        if (firstNode == secondNode)
            return COST_TO_SELF;
        return getNode(firstNode).getAffinityToNode(secondNode);
    }

    /**
     * Between all the Links of all Nodes of the Graph, obtain the one that has the highest affinity value.
     * @return
     * @throws InvalidArgumentException
     */
    public Link getHighestAffinityLink() throws InvalidArgumentException {
        Link highestAffinityLink = new Link(null, null, (int) Double.NEGATIVE_INFINITY);
        for (Node object : nodes) {
            for (Link connection : object.getLinks()) {
                if (connection.getAffinity() > highestAffinityLink.getAffinity())
                    highestAffinityLink = connection;
            }
        }
        return highestAffinityLink;
    }



    /**
     * This method allows to obtain the best Node object possible to be added to the solution.
     * It looks for the best candidates from all over the Graph, ignoring those specified in the excluding ArrayList.
     * @param excluding ArrayList of non-selectable Nodes, specified by their identifiers. This ArrayList should be composed by the Nodes that are already part of the solution.
     * @return
     * @throws InvalidArgumentException
     */
    public Node getBetterNextNode(ArrayList<Integer> excluding) throws InvalidArgumentException {
        int betterNode = -1;
        double affinity = Double.NEGATIVE_INFINITY;
        if (excluding.size() == 0)
            return getHighestAffinityLink().getFrom();
        if (excluding.size() == 1)
            return getHighestAffinityLink().getTo();
        for (Integer origin : excluding)
            for (Link connection : getNode(origin).getLinks())
                if ((connection.getAffinity() > affinity) && (!excluding.contains(connection.getTo().getIdentifier()))) {
                    betterNode = connection.getTo().getIdentifier();
                    affinity = connection.getAffinity();
                }
        if (betterNode == -1)
            return null;
        return getNode(betterNode);
    }


    /*public Node getWorstNextNode(ArrayList<Integer> excluding) throws InvalidArgumentException) {

    }*/



}











//END