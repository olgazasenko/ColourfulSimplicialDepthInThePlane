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
        boolean random = true; // change to false to read from file
        Setup setup = new Setup(random);
        setup.firstPart();
        System.out.println("CSD(0, 0) = " + setup.colourfulSimplicialDepth());
        long endTime = System.currentTimeMillis();
        System.out.println("Total time = " + (endTime - startTime));
    }
}
