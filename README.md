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
