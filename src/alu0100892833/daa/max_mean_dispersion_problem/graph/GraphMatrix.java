package alu0100892833.daa.max_mean_dispersion_problem.graph;

import alu0100892833.daa.Matrix.Matrix;

/**
 * Created by oscardp96 on 19/04/2017.
 */
public class GraphMatrix {

    /*********************************
     ********** CONSTANTS **********
     ***************************/


    /*********************************
     ********** ATTRIBUTES **********
     ***************************/
    private Matrix data;
    int rows, cols;



    public GraphMatrix(int nNodes) {
        this.rows = nNodes;
        this.rows = nNodes;
        data = new Matrix(nNodes * nNodes);
    }







    /**
     * Specifies the size of the Graph.
     * @return Number of nodes of the Graph.
     */
    public int size() {
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
        data.set(from, to, affinity);
        data.set(to, from, affinity);
    }

    /**
     * Obtain the affinity of the Link between the two given Node objects.
     * @param firstNode
     * @param secondNode
     * @return
     */
    public double getAffinity(int firstNode, int secondNode) {
        return data.get(firstNode, secondNode);
    }


}


















//END