package alu0100892833.daa.max_mean_dispersion_problem.graph;

import alu0100892833.daa.Matrix.Matrix;

/**
 * Created by oscardp96 on 19/04/2017.
 */
public class GraphMatrix {

    /*********************************
     ********** CONSTANTS **********
     ***************************/
    private final double COST_TO_SELF = 0.0;


    /*********************************
     ********** ATTRIBUTES **********
     ***************************/
    private Matrix data;


    /*********************************
     ********** CONSTRUCTOR **********
     ***************************/

    public GraphMatrix(int nNodes) {
        data = new Matrix(nNodes * nNodes);
    }

}
