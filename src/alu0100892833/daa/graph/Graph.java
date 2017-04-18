package alu0100892833.daa.graph;

import java.util.HashSet;

/**
 * @author Óscar Darias Plasencia
 * @since 18/04/2017
 */
public class Graph {

    private HashSet<Node> nodes;

    public Graph() {
        nodes = new HashSet<>();
    }

    public Node getNode(int identifier) {
        if (nodes.contains(new Node(identifier)))
            for (Node node : nodes)
                if (node.getIdentifier() == identifier)
                    return node;
        return null;
    }
}
