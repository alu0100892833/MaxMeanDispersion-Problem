package alu0100892833.daa.graph;

import java.util.ArrayList;

/**
 * @author Óscar Darias Plasencia
 * @since 18/04/2017
 */
public class Graph {

    private ArrayList<Node> nodes;

    public Graph(int nNodes) {
        nodes = new ArrayList<>();
        for (int i = 1; i <= nNodes; i++) {
            nodes.add(new Node(i));
        }
    }

    private Node getNode(int identifier) {
        if (nodes.contains(new Node(identifier)))
            for (Node node : nodes)
                if (node.getIdentifier() == identifier)
                    return node;
        return null;
    }

    public void addNode(int identifier) {
        Node newNode = new Node(identifier);
        if (!nodes.contains(newNode))
            nodes.add(newNode);
    }

    public void addLink(int from, int to, int affinity) {
        try {
            getNode(from).addLink(getNode(to), affinity);
        } catch (NullPointerException e) {
            System.err.println("ERROR WHILE ADDING A LINK TO NODE ---> " + from);
            e.printStackTrace();
        }
    }
}
