/*
 * This file is part of ColourfulSimplicialDepthInThePlane project.
    ColourfulSimplicialDepthInThePlane is free software: you can redistribute 
    it and/or modify it under the terms of the GNU General Public License 
    as published by the Free Software Foundation, either version 3 of the License, 
    or (at your option) any later version.
    
    ColourfulSimplicialDepthInThePlane is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    
    You should have received a copy of the GNU General Public License
    along with ColourfulSimplicialDepthInThePlane. 
    If not, see <http://www.gnu.org/licenses/>.
 */
package colourfulsimplicialdepthintheplane;

import java.applet.Applet;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import org.math.plot.Plot2DPanel;

/**
 *
 * @author Olga
 */
public class Paint extends Applet {

    Graphics2D g2;
    ArrayList<Point2D.Double[]> colourSets;
    Plot2DPanel plot;

    Paint(ArrayList<Point2D.Double[]> colourSets) {
        this.colourSets = colourSets;
        JFrame frame = new JFrame("a plot panel");
        frame.setSize(700, 700);
        plot = new Plot2DPanel();
        frame.setContentPane(plot);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void paint() {
        paintPoints();

    }

    private void paintPoints() {

        int k = colourSets.size();
        Color[] colors = {Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA,
            Color.ORANGE, Color.PINK, Color.RED, Color.YELLOW};
        for (int i = 0; i < k; i++) {
            int ni = colourSets.get(i).length;
            double[][] points = new double[ni][2];
            for (int j = 0; j < ni; j++) {
                points[j][0] = colourSets.get(i)[j].x;
                points[j][1] = colourSets.get(i)[j].y;
            }
            plot.addScatterPlot("plot", colors[i], points);
        }
        double[][] points = {{0}, {0}};
        plot.addScatterPlot("plot", Color.BLACK, points);
    }
}
