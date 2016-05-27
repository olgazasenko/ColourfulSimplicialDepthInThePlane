# RousseeuwAndRutsAlgorithm

The paper "Bivariate Location Depth" by Rousseeuw and Ruts describes how to compute
the simplicial depth in O(n log n) time. 
Suppose we have a set P of n data points in R^2. And we want to find the simplicial depth 
of some point x in R^2. To do this, we have to count in how many simplices, formed 
by points from P, x is contained. Since we are in two-dimensional space, we count
triangular containments. We also consider closed triangles.

The algorithm starts by computing the polar angles that data points from P
form with x. Then these angles are sorted and further computations are performed.

This implementation takes an already sorted array of polar angles, and returns
the simplicial depth in O(n) time. Memory required is also O(n).

The image "test3.png" demonstrates an example.
