package alu0100892833.daa.graph;

import java.util.HashSet;

/**
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
}