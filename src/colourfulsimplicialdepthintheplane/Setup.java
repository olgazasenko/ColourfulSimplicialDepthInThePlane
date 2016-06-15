/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colourfulsimplicialdepthintheplane;

import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Olga
 */
public class Setup {

    Scanner in = null;

    protected ArrayList<Point2D.Double[]> colourSets;
    protected ArrayList<Double[]> thetas; // polar angles with respect to (0, 0)
    protected ArrayList<Double[]> antipodes;
    int[] sizes; // individual sizes of the arrays
    int n; // total num of points
    int k; // total num of colours
    int[] lastInd0;

    Double[] A; // sorted array of all antipodes
    long depth; // monochrome depth with respect to all n points
    long sum1 = 0; // number of triangles with all three vertices of the same color that contain (0, 0)
    long sum2 = 0; // number of triangles with two or more vertices of the same color

        Setup(boolean random) {

        Random rand = new Random();
        if (random) {
            k = rand.nextInt((8 - 3) + 1) + 3; // k >= 3
        } else {
            try {
                in = new Scanner(new FileReader("E:\\Documents\\NetBeansProjects\\"
                        + "ColourfulSimplicialDepthNaive\\src\\testing.txt"));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Setup.class.getName()).log(Level.SEVERE, null, ex);
            }
            k = in.nextInt(); // total number of colours
            if (k < 3) {
                System.out.println("Not enough colours! CSD = 0.");
                System.exit(0);
            }
        }
        // initialize all the arrays
        colourSets = new ArrayList<>(k);
        thetas = new ArrayList<>(k);
        antipodes = new ArrayList<>(k);
        sizes = new int[k];
        lastInd0 = new int[k];
        
        if (random) {
            randomData(rand);
        } else {
            initialize();
        }
    }

private final void randomData(Random rand) {

        // bounds on the number of points of each color
        int max = 200, min = 170;
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("E:\\Documents\\NetBeansProjects\\"
                    + "ColourfulSimplicialDepthNaive\\src\\testing.txt", "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(Setup.class.getName()).log(Level.SEVERE, null, ex);
        }
        writer.println(String.valueOf(k));
        int minCoord = -300, maxCoord = 300;
        int ni; // number of points of color i
        
        for (int i = 0; i < k; i++) {
            ni = rand.nextInt((max - min) + 1) + min;
            writer.println(String.valueOf(ni)); // write to file
            sizes[i] = ni;
            n += ni;

            colourSets.add(new Point2D.Double[ni]);
            thetas.add(new Double[ni]);
            antipodes.add(new Double[ni]);

            for (int j = 0; j < ni; j++) {
                double x = rand.nextInt((maxCoord - minCoord) + 1) + minCoord;
                double y = rand.nextInt((maxCoord - minCoord) + 1) + minCoord;
                writer.println(String.valueOf(x).concat(" ").concat(String.valueOf(y)));
                colourSets.get(i)[j] = new Point2D.Double(x, y);
                double theta = Math.toDegrees(Math.atan2(y, x));

                // all the angles are in [0, 360)
                if (theta < 0) {
                    thetas.get(i)[j] = theta + 360;
                } else {
                    thetas.get(i)[j] = theta;
                }
                antipodes.get(i)[j] = (theta + 180) % 360;
            }
            sortCol(i);
        }
        writer.close();
    }

    // reads from file
    private final void initialize() {

        int i = 0;

        while (in.hasNext()) {
            int ni = in.nextInt(); // n_i
            sizes[i] = ni;
            n += ni;

            colourSets.add(new Point2D.Double[ni]);
            thetas.add(new Double[ni]);
            antipodes.add(new Double[ni]);

            for (int j = 0; j < ni; j++) {
                double x = in.nextDouble();
                double y = in.nextDouble();
                colourSets.get(i)[j] = new Point2D.Double(x, y);
                double theta = Math.toDegrees(Math.atan2(y, x)); 

                if (theta < 0) {
                    thetas.get(i)[j] = theta + 360.0;
                } else {
                    thetas.get(i)[j] = theta;
                }
                antipodes.get(i)[j] = (theta + 180.0) % 360.0; 
            }
            sortCol(i);
            i++;
        }
    }

    /*
    * sort both arrays of angles and antipodes of color i
    */
    private void sortCol(int i) {
        Arrays.sort(thetas.get(i));
        Arrays.sort(antipodes.get(i));
    }

    // comute sum1 and depth
    protected void firstPart() {
        for (int i = 0; i < k; i++) {
            sum1 += RousseeuwAndRuts.computeDepth(thetas.get(i));
            lastInd0[i] = RousseeuwAndRuts.NU - 1;
        }
        A = MergeKSortedArrays.KSortedArray.mergeSort(antipodes);
        depth = RousseeuwAndRuts.computeDepth(A);
        Paint paint = new Paint(colourSets);
        paint.paint();
    }

    // there are k iteratoins in total, one for each color
    protected void runIterationK(int iteration) {

        IterationK itK = new IterationK(A, thetas.get(iteration));
        itK.mergeTwoSortedArrays();
        itK.fillCounts();
        itK.findLast(lastInd0[iteration]); // the last index for theta[0] of colour i
        sum2 += itK.getFormulaValue();
    }

    protected long colourfulSimplicialDepth() {
        for (int i = 0; i < k; i++) {
            runIterationK(i);
        }
        System.out.println("D = " + depth);
        System.out.println("sum2 = " + sum2);
        System.out.println("sum1 = " + sum1);
        return (depth - (sum2 - 2 * sum1));
    }
}
