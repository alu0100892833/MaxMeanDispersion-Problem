package alu0100892833.daa.max_mean_dispersion_problem.graph;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.HashSet;

/**
 * This class allows to represent nodes of a graph. Each one has an identifier, an integer that goes from 1 to infinity (this value depends on the number of nodes of the graph).
 * Each node maintains a HashSet of Link objects, as if they were their links to other nodes.
 * Every Link added from a Node "this" to other Node is automatically added in reverse.
 * @author Óscar Darias Plasencia
 * @since 18/04/2017
 */
public class Node {
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

    /**
     * Specify a new Link for the Node.
     * @param to Destination Node, that will also be added this new Link if it has not already.
     * @param affinity The affinity value of this new Link.
     */
    void addLink(Node to, int affinity) {
        Link newLink = new Link(this, to, affinity);
        links.add(newLink);
        if (!to.containsLink(newLink))
            to.addLink(this, affinity);
    }

    /**
     * Obtain the affinity value of the link to the given Node, specified by its identifier. Uses the method {@link #getLinkTo(int)}.
     * @param destinationNode
     * @return
     * @throws InvalidArgumentException
     */
    double getAffinityToNode(int destinationNode) throws InvalidArgumentException {
        return getLinkTo(destinationNode).getAffinity();
    }

    /**
     * Determines if this Node has the given Link.
     * @param link
     * @return
     */
    boolean containsLink(Link link) {
        for (Link connection : getLinks())
            if (connection.equals(link))
                return true;
        return false;
    }

    /**
     * Obtain the Link from this Node to another one given by its identifier.
     * @param destinationNode
     * @return
     * @throws InvalidArgumentException
     */
    Link getLinkTo(int destinationNode) throws InvalidArgumentException {
        for (Link object : links) {
            if (object.getTo().getIdentifier() == destinationNode)
                return object;
        }
        throw new InvalidArgumentException(new String[]{"There is not a link between " + getIdentifier() + " and " + destinationNode});
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