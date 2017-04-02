/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colourfulsimplicialdepthintheplane;

/**
 *
 * @author Olga
 */
public class ColourfulSimplicialDepthInThePlane {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();
        Setup setup = new Setup();
        setup.firstPart();
        System.out.println("CSD(0, 0) = " + setup.colourfulSimplicialDepth() + ".");
        long endTime = System.currentTimeMillis();
        System.out.println("Total time = " + (endTime - startTime));
    }
}
