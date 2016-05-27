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
        // TODO code application logic here
        float[] alpha = {30, 50, 120, 170, 210, 270};
        RousseeuwAndRuts rousseeuwRuts = new RousseeuwAndRuts(alpha);
        int monochromeDepth = rousseeuwRuts.computeDepth();
        System.out.println(monochromeDepth);

    }

    
}
