package alu0100892833.daa.graph;

/**
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