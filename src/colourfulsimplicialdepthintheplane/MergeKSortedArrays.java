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

import java.util.*;

/**
 *
 * source:
 * http://www.programcreek.com/2014/05/merge-k-sorted-arrays-in-java/
 */
public class MergeKSortedArrays {

    protected static class ArrayContainer implements Comparable<ArrayContainer> {

        protected Double[] arr;
        protected int index;

        protected ArrayContainer(Double[] arr, int index) {
            this.arr = arr;
            this.index = index;
        }

        @Override
        public int compareTo(ArrayContainer o) {
            return Double.compare(this.arr[this.index], o.arr[o.index]);
        }
    }

    protected static class KSortedArray {
        
        protected static PriorityQueue<ArrayContainer> queue;

        /**
         *
         * @param arr
         * @return
         */
        protected static Double[] mergeSort(ArrayList<Double[]> arr) {
            //PriorityQueue is heap in Java 
            queue = new PriorityQueue<>();
            int total = 0;

            //add arrays to heap
            for (Double[] arr1 : arr) {
                queue.add(new ArrayContainer(arr1, 0));
                total += arr1.length;
            }

            int m = 0;
            Double result[] = new Double[total];

            //while heap is not empty
            while (!queue.isEmpty()) {
                ArrayContainer ac = queue.poll();
                result[m++] = ac.arr[ac.index];

                if (ac.index < ac.arr.length - 1) {
                    queue.add(new ArrayContainer(ac.arr, ac.index + 1));
                }
            }

            return result;
        }

    }
}
