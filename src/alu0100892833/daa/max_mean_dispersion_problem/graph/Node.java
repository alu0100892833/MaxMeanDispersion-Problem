package alu0100892833.daa.max_mean_dispersion_problem.graph;

import java.util.HashSet;

/**
 * This class allows to represent nodes of a graph. Each one has an identifier, an integer that goes from 1 to infinity (this value depends on the number of nodes of the graph).
 * Each node maintains a HashSet of Link objects, as if they were their links to other nodes.
 * Every Link added from a Node "this" to other Node is automatically added in reverse.
 * @author Óscar Darias Plasencia
 * @since 18/04/2017
 */
class Node {
    private int identifier;
    private HashSet<Link> links;

    Node(int identifier) {
        this.identifier = identifier;
        links = new HashSet<>();
    }

    public int getIdentifier() {
        return this.identifier;
    }

    HashSet<Link> getLinks() {
        return this.links;
    }

    void addLink(Node to, int affinity) {
        links.add(new Link(this, to, affinity));
        to.addLink(this, affinity);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Node))
            return false;
        Node other = (Node) o;
        return (getIdentifier() == other.getIdentifier());
    }

    @Override
    public String toString() {
        return Integer.toString(getIdentifier());
    }
}