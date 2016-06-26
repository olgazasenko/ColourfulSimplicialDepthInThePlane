This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
    
# ColourfulSimplicialDepthInThePlane

This project is an implementation of the algorithm developed as part of the paper 
in progress by O. Zasenko and T. Stephen. It extends the notion of simplicial depth, first introduced
by Liu, to a colourful case. More specifically, each data point in a set is assigned its own colour.
We form colourful triangles from these points (all three vertices have different colours), and count
in how many of this triangles our point of interest is contained. For convenience, we chose
to compute the depth of <strong>x = (0, 0)</strong>.

Our algorithm builds up on those introduced by Rousseeuw and Ruts in "Bivariate Location Depth",
and Gil, Steiger, and Wigderson in "Geometric Medians". The overall running time is <strong>O(n log n + kn)</strong>,
where <strong>k</strong> is the number of colours, and <strong>n</strong> is the number of data points. 
The space required is <strong>O(n)</strong>.
Points can either be read from file, or generated randomly. But we do require them to be in general position.
The minimal number of colours is 3, in order to have at least one colourful triangle.

Our approach is as follows. 
<ol>
<li>Compute <strong>depth</strong> - the number of monochrome triangles that contain <strong>x</strong> 
using the Rousseeuw and Ruts Algorithm.</li>
<li>Compute <strong>sum1</strong> - the number of triangles with all three vertices of the same colour that 
contain <strong>x</strong>.</li>
<li>Compute <strong>sum2</strong> - the number of triangles with two vertices of the same colour that 
contain <strong>x</strong>.</li>
<li>Conclude that the colourful simplicial depth of <strong>x</strong> is equal to
<strong>depth - sum1 - sum2</strong>.</li>
</ol>

# RousseeuwAndRutsAlgorithm

The paper "Bivariate Location Depth" by Rousseeuw and Ruts describes how to compute
the simplicial depth in <strong>O(n log n)</strong> time. 
Suppose we have a set <strong>P</strong> of <strong>n</strong> data points in <strong>R^2</strong>. 
And we want to find the simplicial depth of some point <strong>x</strong> in <strong>R^2</strong>.
To do this, we have to count in how many simplices, formed by points from <strong>P</strong>,
<strong>x</strong> is contained. Since we are in two-dimensional space, we count
triangular containments. We also consider closed triangles.

The algorithm starts by computing the polar angles that data points from <strong>P</strong>
form with <strong>x</strong>. Then these angles are sorted and further computations are performed.

This implementation takes an already sorted array of polar angles, and returns
the simplicial depth in <strong>O(n)</strong> time. Memory required is also <strong>O(n)</strong>.

The image "test3.png" demonstrates an example.
