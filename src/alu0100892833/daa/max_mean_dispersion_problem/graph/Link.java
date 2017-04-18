package alu0100892833.daa.max_mean_dispersion_problem.graph;

/**
 * This class allows to represent links between nodes in a graph.
 * It saves the origin and destination nodes and the affinity of the connection between both.
 * This links are reversible: a link from a node A to a node B exists also from B to A.
 * @author Óscar Darias Plasencia
 * @since 18/04/2017
 */
class Link {
    private Node from, to;
    private int affinity;

    Link(Node from, Node to, int affinity) {
        this.to = to;
        this.from = from;
        this.affinity = affinity;
    }

    public Node getFrom() {
        return from;
    }

    public void setFrom(Node from) {
        this.from = from;
    }

    public Node getTo() {
        return to;
    }

    public void setTo(Node to) {
        this.to = to;
    }

    public int getAffinity() {
        return affinity;
    }

    public void setAffinity(int affinity) {
        this.affinity = affinity;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Link))
            return false;
        Link other = (Link) o;
        return (((from == other.from) && (to == other.to)) || ((from == other.to) && (to == other.from)));
    }
}