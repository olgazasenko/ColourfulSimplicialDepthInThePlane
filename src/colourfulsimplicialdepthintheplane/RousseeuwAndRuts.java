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
 *
 * computes the monochrome simplicial depth alpha is a sorted array of polar
 * angles alpha[i] in [0, 2*pi)
 */
public class RousseeuwAndRuts {

    private final float[] alpha;
    private final int len;
    float[] alpha1;
    float[] beta;
    float[] gamma; // common sorted array
    int[] w; // 
    int NU; // number of alphas in the semicircle of alpha[0] inclusive

    RousseeuwAndRuts(float[] alpha) {

        this.alpha = alpha;
        this.len = alpha.length;
        fillAlphaBeta();
    }

    private void fillAlphaBeta() {
        /* let alpha1[0] = 0, alpha1[i] = alpha[i] - alpha[0]
           let beta[i] = (alpha1[i] + pi) mod 2*pi
           sort beta
         */
        alpha1 = new float[len];
        beta = new float[len];
        NU = 0;
        boolean flag = false; // for looping around beta
        int startB = 0;

        for (int i = 0; i < len; i++) {
            alpha1[i] = alpha[i] - alpha[0];
            if (alpha1[i] < 180) {
                NU++;
            } else if (!flag) {
                // we just looped for the first time
                startB = i;
                flag = true;
            }
        }
        int j = startB, indB = 0;
        while (indB != len) {
            if (alpha1[j] < 180) {
                beta[indB] = alpha1[j] + 180;
            } else {
                beta[indB] = alpha1[j] - 180;
            }
            j++;
            indB++;
            j %= len;
        }
        System.out.println("alpha: " + Arrays.toString(alpha));
        System.out.println("alpha1: " + Arrays.toString(alpha1));
        System.out.println("beta: " + Arrays.toString(beta));
        System.out.println("NU: " + NU);
    }

    /**
     *
     * @return the monochrome simplicial depth
     */
    protected int computeDepth() {

        if (len == 0) {
            System.out.println("Alpha array is empty!");
            return 0;
        } else if (len < 3) {
            System.out.println("There is less than 3 points in alpha!");
            return 0;
        }
        if (alpha1[len - 1] < 180){
            System.out.println("The center lies outisde of the data cloud.");
            return 0;
        }

        int start = merge(); //merge alpha and beta into gamma sorted
        // start is the index of the entry right after 180
        System.out.println("Sorted:");
        System.out.println("gamma: " + Arrays.toString(gamma));
        System.out.println("w: " + Arrays.toString(w));

        int NF = NU;
        int[] hi = new int[len];
        hi[0] = NU - 1;

        int i = start, t = 1;
        while (i != start - 1) { // completed full circle
            if (w[i] == 1) {
                NF++;
            } else {
                hi[t] = NF - (t + 1); // h(i) = F(i) - i
                t++;
            }
            i++;
            i %= 2 * len;
        }
        System.out.println("h(i): " + Arrays.toString(hi));

        int sum = 0;
        for (i = 0; i < len; i++) {
            sum += choose(hi[i], 2);
        }
        int total = choose(len, 3);
        System.out.println("Simplicial Depth: " + (total - sum));

        Paint paint = new Paint(gamma, w);
        paint.paint();
        return (total - sum);
    }

    /*
    source:
    http://stackoverflow.com/questions/15301885/calculate-value-of-n-choose-k
    
     */
    private int choose(int n, int k) {
        if (k == 0) {
            return 1;
        }
        return (n * choose(n - 1, k - 1)) / k;
    }

    /*
    source:
    http://stackoverflow.com/questions/5958169/how-to-merge-two-sorted-arrays-into-a-sorted-array
     */
    private int merge() {
        /*
           w[i] = 1, if gamma[i] = alpha1[j]
           w[i] = -1, if gamma[i] = beta[j]
           merge also finds first element greater than 180
           or alpha[start] = 180
         */
        w = new int[2 * len];
        gamma = new float[2 * len];
        int i = 0, j = 0, k = 0, start = NU;
        boolean flag = false;

        while (i < len && j < len) {
            if (alpha1[i] < beta[j]) {
                gamma[k] = alpha1[i++];
                w[k] = 1;
            } else {
                gamma[k] = beta[j++];   // priority given to beta      
                w[k] = -1;
            }
            if ((gamma[k] == 180 && w[k] == 1 && !flag) || (gamma[k] > 180 && !flag)) {
                start = k;
                flag = true;
            }
            k++;
        }

        while (i < len) {
            gamma[k] = alpha1[i++];
            w[k] = 1;
            if (gamma[k] >= 180 && !flag) {
                start = k;
                flag = true;
            }
            k++;
        }

        while (j < len) {
            gamma[k] = beta[j++];
            w[k] = -1;
            if (gamma[k] > 180 && !flag) {
                start = k;
                flag = true;
            }
            k++;
        }
        return start;
    }
}
