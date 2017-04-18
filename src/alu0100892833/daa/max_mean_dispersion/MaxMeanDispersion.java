package alu0100892833.daa.max_mean_dispersion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import alu0100892833.daa.graph.Graph;

/**
 * @author Óscar Darias Plasencia
 * @since 18/04/2017
 */
public class MaxMeanDispersion {

    private Graph problemGraph;


    public MaxMeanDispersion(String filename) {
        BufferedReader bReader = null;
        try {
            bReader = new BufferedReader(new FileReader(filename));
            int nNodes = Integer.parseInt(bReader.readLine());

            for (int i = 1; i < nNodes; i++)
                for (int j = i + 1; j <= nNodes; j++) {
                    int value = Integer.parseInt(bReader.readLine());
                    problemGraph.addLink(i, j, value);
                }
        } catch (IOException e) {
            System.err.println("ERROR WHILE OPENING INPUT FILE.");
            e.printStackTrace();
        } finally {
            if (bReader != null)
                try {
                    bReader.close();
                } catch (IOException e) {
                    System.err.println("ERROR WHILE CLOSING INPUT FILE.");
                    e.printStackTrace();
                }
        }
    }
}
