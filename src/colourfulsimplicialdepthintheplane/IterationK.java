/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colourfulsimplicialdepthintheplane;

import java.util.Arrays;

/**
 *
 * @author Olga
 */
public class IterationK {

    private final Double[] arr;
    private Double[] res;
    private int[] w;
    private final Double[] thetaI;
    private final int totalLen;
    private final int ni;
    private int[] pointer;
    private int[] counts;
    private int[] lastInd;
    private int[] sumPrefix;
    private int[] tSumPrefix;

    IterationK(Double[] A, Double[] thetaI) {
        this.arr = A;
        this.thetaI = thetaI;
        this.totalLen = A.length + thetaI.length;
        this.ni = thetaI.length;
    }

    /*
    source:
    http://stackoverflow.com/questions/5958169/how-to-merge-two-sorted-arrays-into-a-sorted-array
     */
    protected void mergeTwoSortedArrays() {

        res = new Double[totalLen];
        w = new int[totalLen];
        pointer = new int[ni];
        int i = 0, j = 0, k = 0;

        while (i < arr.length && j < ni) {
            if (arr[i] < thetaI[j]) {
                res[k] = arr[i++];
                w[k] = -1;
            } else {
                res[k] = thetaI[j]; // if equal theta comes first
                w[k] = 1;
                pointer[j] = k;
                j++;
            }

            k++;
        }

        while (i < arr.length) {
            res[k] = arr[i];
            w[k] = -1;
            i++;
            k++;
        }

        while (j < ni) {
            res[k] = thetaI[j];
            w[k] = 1;
            pointer[j] = k;
            j++;
            k++;
        }
        System.out.println("thetaI: " + Arrays.toString(thetaI));
        System.out.println("B: " + Arrays.toString(res));
        System.out.println("w: " + Arrays.toString(w));
        System.out.println("pointer: " + Arrays.toString(pointer));
    }

    protected void fillCounts() {

        counts = new int[ni];
        for (int j = 1; j <= ni; j++) {

            if (ni == 1) {
                counts[0] = 0;
                break;
            }
            int jMod = j;
            if (j == ni) {
                jMod = 0;
            }
            double angle = thetaI[jMod] - thetaI[j - 1];
            if (angle < 0) {
                angle += 360;
            }
            if (angle >= 180 || (angle == 0 && jMod != j + 1)) {
                counts[jMod] = 0;
            } else {
                counts[jMod] = pointer[jMod] - pointer[j - 1] - 1;
                if (pointer[jMod] < pointer[j - 1]) {
                    counts[jMod] += totalLen;
                }
            }
            if (pointer[jMod] == pointer[j - 1]) {
                System.out.println("THIS SHOULD NOT HAPPEN!");
            }
        }
        System.out.println("Counts: " + Arrays.toString(counts));
    }

    /* take first index as an argument
       all subsequent ones just linear scan
       also fill the prefix sum arrays
     */
    protected void findLast(int lastInd0) {

        lastInd = new int[ni];
        sumPrefix = new int[ni];
        tSumPrefix = new int[ni];

        lastInd[0] = lastInd0;
        sumPrefix[0] = counts[0];
        tSumPrefix[0] = sumPrefix[0];

        int j = lastInd0; // could be the same
        double angle;
        for (int i = 1; i < ni; i++) {
            angle = thetaI[j] - thetaI[i];
            if (angle < 0) {
                angle += 360;
            }

            while (angle < 180) {
                j++;
                j %= ni;
                angle = thetaI[j] - thetaI[i];
                if (angle < 0) {
                    angle += 360;
                }
                if (angle == 0 && i == j) {
                    break;
                }
            }
            lastInd[i] = (j - 1);
            if (lastInd[i] < 0) {
                lastInd[i] += ni;
            }

            sumPrefix[i] = sumPrefix[i - 1] + counts[i];
            tSumPrefix[i] = tSumPrefix[i - 1] + sumPrefix[i];
        }
        System.out.println("LastInd: " + Arrays.toString(lastInd));
        System.out.println("S: " + Arrays.toString(sumPrefix));
        System.out.println("T: " + Arrays.toString(tSumPrefix));
    }

    protected long getFormulaValue() {
        int depthI = 0;
        int temp;
        for (int j = 0; j < ni - 1; j++) {
            if (j == lastInd[j]) {
                continue; // !!!
            }
            temp = lastInd[j] - j;
            if (temp < 0) {
                temp += ni;
            }
            depthI += tSumPrefix[lastInd[j]] - tSumPrefix[j] - temp * sumPrefix[j]; // OMG
            if (lastInd[j] < (j + 1)) {
                temp = lastInd[j] + 1; // NO MODULO HERE
                depthI += tSumPrefix[ni - 1]
                        + sumPrefix[ni - 1] * temp;
            }
        }
        double angle = thetaI[0] - thetaI[ni - 1];
        if (angle < 0) {
            angle += 360;
        }
        if (angle < 180) { // NECCESSARY????
            depthI += tSumPrefix[lastInd[ni - 1]]; // special case, when j = n_i - 1, 
            // and j + 1 = 0
        }
        System.out.println("D^i = " + depthI);
        return (depthI);
    }

  /*  private long degenerateTriangles() {
        int thetas, antipodes, j;
        long degeneracies = 0;
        for (int i = 1; i < totalLen; i++) {
            thetas = 0;
            antipodes = 0;
            if (Math.abs(res[i] - res[i - 1]) < EPSILON) {
                if (w[i - 1] == 1) {
                        thetas++;
                    } else {
                        antipodes++;
                    }
                j = i - 1;
                while (Math.abs(res[j + 1] - res[j]) < EPSILON && (j + 1) < totalLen) {
                    if (w[j + 1] == 1) {
                        thetas++;
                    } else {
                        antipodes++;
                    }
                    j++;
                }
                degeneracies += choose(thetas, 2) * antipodes;
                i = j;
            } 
        }
        return degeneracies;
    }*/

    /*
    source:
    http://stackoverflow.com/questions/15301885/calculate-value-of-n-choose-k
    
     */
   /* private static long choose(int n, int k) {
        if (k == 0) {
            return 1;
        }
        return (n * choose(n - 1, k - 1)) / k;
    }*/
}
