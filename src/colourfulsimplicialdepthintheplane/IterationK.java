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

    private final Double[] arr; // array of sorted antipodes
    private Double[] res; // result of merging arr and thetaI
    private final Double[] thetaI; // sorted array pf polar angles of color i
    private final int totalLen;
    private final int ni;
    private int[] pointer; // indicates the position of thetaI[j] in res
    private int[] counts; // the number of points in res in the interval between thetaI[j] and thetaI[j + 1]
    private int[] lastInd;
    private int[] sumPrefix; // prefix sum array of counts
    private int[] tSumPrefix; // prefix sum array of sumPrefix

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
        pointer = new int[ni];
        int i = 0, j = 0, k = 0;

        while (i < arr.length && j < ni) {
            if (arr[i] < thetaI[j]) {
                res[k] = arr[i++];
            } else {
                res[k] = thetaI[j]; // if equal theta comes first
                pointer[j] = k; // remember the position
                j++;
            }

            k++;
        }

        while (i < arr.length) {
            res[k++] = arr[i++];
        }

        while (j < ni) {
            res[k] = thetaI[j];
            pointer[j] = k;
            j++;
            k++;
        }
    }

    protected void fillCounts() {

        counts = new int[ni];
        for (int j = 1; j <= ni; j++) {

            if (ni == 1) {
                counts[0] = 0;
                break;
            }
            int jMod = j, jMinus = j - 1;
            if (j == ni) {
                jMod = 0;
            }
            double angle = thetaI[jMod] - thetaI[jMinus];
            if (angle < 0) {
                angle += 360;
            }
            // the latter condition is for small arrays
            if (angle >= 180 || (angle == 0 && jMod != j + 1)) {
                counts[jMod] = 0;
            } else {
                counts[jMod] = pointer[jMod] - pointer[jMinus] - 1;
                if (pointer[jMod] < pointer[jMinus]) {
                    counts[jMod] += totalLen;
                }
            }
        }
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

            int iMinus = i - 1;
            sumPrefix[i] = sumPrefix[iMinus] + counts[i];
            tSumPrefix[i] = tSumPrefix[iMinus] + sumPrefix[i];
        }
    }

    protected long getFormulaValue() {
        long depthI = 0;
        int temp;
        int niMinus1 = ni - 1;
        
        for (int j = 0; j < niMinus1; j++) {
            if (j == lastInd[j]) {
                continue; // !!!
            }
            temp = lastInd[j] - j;
            if (temp < 0) {
                temp += ni;
            }
            depthI += tSumPrefix[lastInd[j]] - tSumPrefix[j] - temp * sumPrefix[j]; 
            if (lastInd[j] < (j + 1)) {
                temp = lastInd[j] + 1; // NO MODULO HERE
                depthI += tSumPrefix[ni - 1]
                        + sumPrefix[ni - 1] * temp;
            }
        }
        double angle = thetaI[0] - thetaI[niMinus1];
        if (angle < 0) {
            angle += 360;
        }
        if (angle < 180) { 
            depthI += tSumPrefix[lastInd[niMinus1]]; // special case, when j = n_i - 1, 
            // and j + 1 = 0
        }
        return (depthI);
    }

}
