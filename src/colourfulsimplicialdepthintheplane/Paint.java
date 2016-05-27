/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colourfulsimplicialdepthintheplane;

import java.applet.Applet;
import java.awt.*;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import org.math.plot.Plot2DPanel;

/**
 *
 * @author Olga
 */
public  class Paint extends Applet{
    Graphics2D g2;
    float[] gamma;
    int[] w;
    Plot2DPanel plot;
    
    
    Paint(float[] gamma, int[] w){
        this.gamma = gamma;
        this.w = w;
        JFrame frame = new JFrame("a plot panel");
        frame.setSize(700, 700);
        plot = new Plot2DPanel();
        plot.setFixedBounds(0, -5, 15);
        plot.setFixedBounds(1, -5, 30);
        frame.setContentPane(plot);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public void paint(){
         paintPoints();
         
    }
    
    private void paintPoints(){
        int n = gamma.length;
        double[][] alpha = new double[n/2][2];
        double[][] beta = new double[n/2][2];
        int j = 0;
        int k = 0;
        for (int i = 0; i < n; i++){
            if (w[i] == 1){
                alpha[j][0] = Math.cos(Math.toRadians(gamma[i]));
                alpha[j][1] = Math.sin(Math.toRadians(gamma[i]));
                j++;
            }
            else {
                beta[k][0] = Math.cos(Math.toRadians(gamma[i]));
                beta[k][1] = Math.sin(Math.toRadians(gamma[i]));
                k++;
            }
        }
        plot.addScatterPlot("plot", Color.BLUE, alpha);
        plot.addScatterPlot("plot", Color.GREEN, beta);
    }
}
