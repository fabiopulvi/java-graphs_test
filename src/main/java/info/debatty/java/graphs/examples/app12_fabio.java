package info.debatty.java.graphs.examples;

import info.debatty.java.graphs.*;
import info.debatty.java.graphs.build.Brute;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;


/**
 * Created by fabio on 23/02/16.
 *
 * STRATEGY 3 with depth
 * Two graphs brute graphs are created
 * one with node 0
 * one without node 0
 * then node 0 is deleted from the first graphs
 *
 *DEPTH=1
 the wrong edges are: [0, 2, 1, 2, 3, 2, 0, 3, 1, 2, 3, 9, 2, 1, 4, 2, 3, 1, 1, 3, 3, 2, 1, 4, 3, 1, 3, 3, 3, 4, 4, 4, 1, 1, 1, 4, 4, 3, 3, 3, 2, 2, 6, 3, 5, 3, 0, 2, 2, 3, 1, 1, 2, 2, 4, 3, 3, 2, 1, 2, 2, 1, 1, 1, 4, 6, 3, 2, 4, 6, 2, 4, 2, 4, 3, 1, 2, 3, 3, 2, 3, 3, 3, 3, 4, 4, 2, 1, 1, 6, 1, 0, 2, 4, 6, 0, 2, 0, 2, 1]
 in the average the differences are 2.53
 the modified edges are 6.66
 the correct edges are 3997.47
 the Q is 0.36750000000000005

 the wrong edges are: [1, 1, 3, 5, 4, 1, 3, 3, 3, 0, 1, 2, 4, 2, 6, 3, 2, 4, 1, 1, 2, 3, 0, 3, 2, 3, 1, 2, 0, 3, 2, 3, 2, 1, 0, 0, 3, 3, 3, 10, 2, 3, 2, 0, 0, 0, 3, 4, 3, 2, 2, 3, 2, 4, 3, 2, 3, 1, 2, 1, 3, 1, 3, 0, 1, 3, 1, 3, 0, 1, 1, 5, 0, 2, 2, 6, 3, 1, 3, 0, 0, 0, 2, 3, 0, 2, 0, 4, 3, 3, 4, 0, 3, 4, 3, 2, 2, 1, 2, 0]
 in the average the differences are 2.15
 the modified edges are 5.69
 the correct edges are 3997.85
 the Q is 0.4625

 the wrong edges are: [1, 1, 4, 3, 1, 3, 2, 2, 1, 3, 2, 4, 1, 7, 1, 0, 2, 4, 3, 3, 4, 2, 2, 3, 3, 3, 0, 1, 2, 3, 2, 0, 4, 1, 2, 3, 2, 1, 3, 2, 1, 0, 2, 5, 3, 4, 1, 0, 3, 3, 3, 2, 0, 1, 1, 0, 3, 2, 1, 1, 4, 9, 2, 0, 1, 3, 1, 0, 2, 1, 0, 2, 4, 1, 2, 2, 3, 1, 1, 2, 4, 0, 2, 4, 1, 2, 1, 1, 1, 2, 0, 1, 3, 1, 1, 3, 1, 1, 2, 1]
 in the average the differences are 2.0
 the modified edges are 5.38
 the correct edges are 3998.0
 the Q is 0.5

 DEPTH=2

 the wrong edges are: [3, 2, 1, 1, 1, 2, 0, 3, 1, 1, 0, 0, 1, 2, 1, 7, 1, 2, 0, 0, 0, 2, 1, 0, 2, 1, 1, 0, 0, 2, 1, 0, 0, 1, 0, 1, 0, 0, 0, 2, 1, 0, 1, 4, 1, 0, 0, 0, 1, 0, 3, 4, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 4, 1, 1, 1, 3, 0, 2, 0, 2, 0, 0, 6, 2, 2, 0, 2, 0, 1, 1, 0, 1, 2, 2, 0, 0, 0, 3, 0, 3, 2, 1, 2, 2, 0, 1, 0, 1]
 in the average the differences are 1.09
 the modified edges are 4.58
 the correct edges are 3998.91
 the Q is 0.7275

 in the average the differences are 0.92
 the modified edges are 4.31
 the correct edges are 3999.08
 the Q is 0.77


 in the average the differences are 0.98
 the modified edges are 4.27
 the correct edges are 3999.02
 the Q is 0.755
 DEPTH=3

 in the average the differences are 0.4
 the modified edges are 4.67
 the correct edges are 3999.6
 the Q is 0.9

 in the average the differences are 0.42
 the modified edges are 4.21
 the correct edges are 3999.58
 the Q is 0.895

 in the average the differences are 0.5
 the modified edges are 4.39
 the correct edges are 3999.5
 the Q is 0.875

 DEPTH=4

 the wrong edges are: [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 4, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 3, 4, 0, 0, 0, 1, 1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
 in the average the differences are 0.34
 the modified edges are 4.44
 the correct edges are 3999.66
 the Q is 0.915

 in the average the differences are 0.38
 the modified edges are 4.43
 the correct edges are 3999.62
 the Q is 0.905

 the wrong edges are: [0, 0, 0, 4, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 2, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 4, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 5, 1, 0, 0, 0, 0, 0, 0]
 in the average the differences are 0.29
 the modified edges are 4.2
 the correct edges are 3999.71
 the Q is 0.9275
 1000!
 in the average the differences are 0.383
 the modified edges are 4.322
 the correct edges are 3999.617
 the Q is 0.90425
 the wrong edges are: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 4, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 7, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 12, 0, 0, 1, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 3, 0, 2, 0, 0, 1, 0, 0, 0, 0, 0, 2, 4, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 1, 1, 0, 2, 0, 0, 0, 0, 1, 0, 3, 0, 0, 1, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 4, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 2, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 1, 0, 2, 2, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 2, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 1, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 4, 0, 3, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 2, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 4, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 2, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 2, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 2, 4, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 4, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 3, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 3, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 2, 0, 2, 0, 2, 0, 0, 0, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 4, 2, 0, 0, 0, 0, 0, 0, 4, 1, 0, 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 2, 0, 0, 1, 2, 0, 0, 1, 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 7, 0, 0, 4, 0, 0, 0, 0, 2, 4, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 3, 1, 0, 0, 0, 4, 0, 0, 1, 2, 0, 2, 3, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 2, 7, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 4, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 4, 1, 1, 0, 0, 0, 3, 0, 0, 0, 0, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 2, 0, 0, 4, 1, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 2, 0, 0, 0, 0, 1, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 4, 1, 3, 0, 0, 1, 0, 1, 4, 0, 0, 0, 0, 1, 0, 3, 0, 0, 0, 0, 0, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 7, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 2, 0, 0, 2, 0, 7, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 4, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 4, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 4, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 4, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 9, 1, 2, 2, 0, 0, 0, 0, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 2, 0, 0, 2, 1, 0, 0, 0, 6, 8, 0, 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 6, 0, 5, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 1, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 2, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 9, 0, 0, 2, 0, 0, 0, 1, 2, 0, 0, 2, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 1, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 0, 4, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 4, 1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 2, 3, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 1, 0, 0, 0, 12, 0, 0, 1, 0, 2, 2, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0]
 in the average the differences are 0.3815
 the modified edges are 4.314
 the correct edges are 3999.6185
 the Q is 0.904625
 in the average the differences are 0.37666666666666665
 the modified edges are 4.282333333333334
 the correct edges are 3999.6233333333334
 the Q is 0.9058333333333334
 DEPTH=5

 in the average the differences are 0.37
 the modified edges are 4.64
 the correct edges are 3999.63
 the Q is 0.9075

 the wrong edges are: [0, 0, 4, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 9, 1, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 3, 0, 1, 0, 1, 0, 2, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 1, 4, 3, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
 in the average the differences are 0.46
 the modified edges are 4.41
 the correct edges are 3999.54
 the Q is 0.885

 the wrong edges are: [0, 0, 0, 4, 1, 0, 0, 0, 1, 1, 0, 0, 1, 4, 0, 0, 4, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 2, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 2, 0, 0, 0, 0, 0, 1, 0, 2, 0, 0, 0, 0, 3, 0, 1, 0, 4, 1, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 3, 0, 1, 0, 2, 0, 0, 0, 0, 0]
 in the average the differences are 0.55
 the modified edges are 4.43
 the correct edges are 3999.45
 the Q is 0.8625

 1000!
 in the average the differences are 0.408
 the modified edges are 4.329
 the correct edges are 3999.592
 the Q is 0.898

 1000!
 in the average the differences are 0.386
 the modified edges are 4.322
 the correct edges are 3999.614
 the Q is 0.9035
1000!
 the wrong edges are: [0, 7, 1, 0, 0, 2, 1, 0, 0, 0, 2, 0, 0, 4, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 1, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 2, 0, 0, 0, 2, 1, 0, 0, 0, 0, 0, 2, 0, 0, 0, 4, 0, 0, 0, 0, 0, 3, 0, 0, 0, 4, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 4, 1, 0, 1, 2, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 1, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 1, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 4, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 4, 0, 1, 0, 0, 0, 0, 1, 0, 3, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 2, 0, 2, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 4, 1, 0, 0, 1, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 3, 1, 1, 0, 1, 1, 1, 7, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 2, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 4, 0, 4, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 2, 0, 1, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 5, 0, 0, 1, 2, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 10, 0, 4, 0, 0, 6, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 2, 0, 0, 0, 2, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 1, 0, 2, 0, 2, 4, 0, 0, 0, 0, 1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 1, 0, 0, 7, 0, 3, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 1, 0, 0, 0, 2, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 1, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 3, 0, 0, 2, 0, 0, 6, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 4, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 2, 0, 1, 0, 0, 0, 4, 0, 0, 0, 0, 4, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 2, 4, 0, 0, 2, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 4, 4, 0, 1, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 2, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 1, 0, 0, 2, 2, 4, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 0, 2, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 4, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 3, 0, 0, 0, 0, 0, 1, 0, 1, 4, 0, 0, 0, 1, 0, 4, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 2, 0, 0, 0, 0, 1, 4, 1, 0, 0, 0, 0, 2, 1, 1, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 2, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 5, 0, 4, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 2, 0, 0, 0, 3, 1, 1, 0, 0, 0, 0, 0, 2, 0, 1, 0, 0, 0, 3, 0, 4, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 1, 4, 1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 13, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 2, 1, 8, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 2, 0, 4, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 2, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 4, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 4, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 2, 0, 0, 0, 0, 0, 4, 0, 2, 0, 1, 3, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 2, 0, 0, 0, 1]
 in the average the differences are 0.386
 the modified edges are 4.3155
 the correct edges are 3999.614
 the Q is 0.9035

 DEPTH=6
 in the average the differences are 0.4
 the modified edges are 4.5
 the correct edges are 3999.6
 the Q is 0.9

 in the average the differences are 0.47
 the modified edges are 4.32
 the correct edges are 3999.53
 the Q is 0.8825000000000001

 the wrong edges are: [0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 4, 0, 0, 0, 4, 0, 0, 0, 2, 1, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 4, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 2, 1, 0, 2, 2, 0, 0, 0, 1, 0, 0, 1, 4, 1, 8, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 4]
 in the average the differences are 0.62
 the modified edges are 4.8
 the correct edges are 3999.38
 the Q is 0.845


 DEPTH=7

 in the average the differences are 0.37
 the modified edges are 4.14
 the correct edges are 3999.63
 the Q is 0.9075

 the wrong edges are: [0, 1, 0, 0, 0, 3, 3, 2, 0, 0, 0, 1, 0, 2, 0, 2, 0, 1, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 9, 3, 2, 2, 0, 0, 0, 1, 0, 1, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 10, 2, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 2, 4, 1, 0, 0, 0, 0, 0]
 in the average the differences are 0.72
 the modified edges are 4.79
 the correct edges are 3999.28
 the Q is 0.8200000000000001

 in the average the differences are 0.26
 the modified edges are 4.14
 the correct edges are 3999.74
 the Q is 0.935
 *
 * 
 */
public class app12_fabio {
    public static int K = 2;
    public static int count = 20;
    public static int iterations=1;
    public static int run=1;
    public static int depth=1;
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        for (int b = 1; b <= run; b++) {
            ArrayList<Integer> errors = new ArrayList<Integer>();
            ArrayList<Integer> modified_edges = new ArrayList<Integer>();
            ArrayList<Integer> comparisons = new ArrayList<Integer>();
            for (int a = 0; a < iterations; a++) {
                // Generate some random nodes and add them to the graphs
                Random r = new Random();
                //create the two arrays of nodes
                ArrayList<Node> nodes_1 = new ArrayList<Node>(count);
                ArrayList<Node> nodes_2 = new ArrayList<Node>(count);

                for (int i = 0; i < count; i++) {
                    // The value of our nodes will be an int
                    int value = r.nextInt(2000000);
                    if (i != 0) nodes_2.add(new Node<Integer>(String.valueOf(i), value));
                    nodes_1.add(new Node<Integer>(String.valueOf(i), value));
                }

                Brute builder_1 = new Brute<Integer>();
                builder_1.setK(K);
                builder_1.setSimilarity(new SimilarityInterface<Integer>() {

                    public double similarity(Integer value1, Integer value2) {
                        return 1.0 / (1.0 + Math.abs(value1 - value2));
                    }
                });

                Graph<Integer> graph_1 = builder_1.computeGraph(nodes_1);
                OnlineGraph<Integer> online_graph_1 = new OnlineGraph<Integer>(graph_1);


                //create the online graph 2

                Brute builder_2 = new Brute<Integer>();
                builder_2.setK(K);
                builder_2.setSimilarity(new SimilarityInterface<Integer>() {

                    public double similarity(Integer value1, Integer value2) {
                        return 1.0 / (1.0 + Math.abs(value1 - value2));
                    }
                });

                Graph<Integer> graph_2 = builder_1.computeGraph(nodes_2);
                OnlineGraph<Integer> online_graph_2 = new OnlineGraph<Integer>(graph_2);
                //find the node0 info and delete it
                Node<Integer> N0 = null;
                NeighborList N0_list = null;
                for (Node<Integer> n : online_graph_1.getNodes()) {
                    if (Integer.parseInt(n.id) == 0) {
                        N0 = new Node<Integer>(n.id, n.value);
                        N0_list = online_graph_1.get(n);
                    }
                }
                  System.out.println("\n the node to delete is N0: " + N0);
                System.out.println("\n its' nl was: " + N0_list);

                //online_graph_1.removeNodeFromNeighbourlist(N0);
                int node_comparisons = online_graph_1.removeAndUpdate_3_depth(N0, depth, true);
                comparisons.add(node_comparisons);
                int wrong_edge = 0;

                for (Node<Integer> node_all : online_graph_2.getNodes()) {
                    NeighborList nl = online_graph_2.get(node_all);
                    System.out.print(node_all);
                    System.out.println(nl);
                }

                // Iterate the nodes to see the differences
                for (Node n : online_graph_2.getNodes()) {
                    NeighborList nl1 = online_graph_1.get(n);
                    NeighborList nl2 = online_graph_2.get(n);
                    int node_wrong_edges = K - nl1.countCommons(nl2);
                    if (node_wrong_edges > 0) {
                        System.out.print("\n node: " + n);

                         System.out.println("\n updated graph: " + nl1);
                          System.out.println("brute graph: " + nl2);

                            System.out.print("the differences are: " + (Integer.toString(node_wrong_edges)) + "\n");
                    }
                    wrong_edge += node_wrong_edges;


                }

                //System.out.print("the modified nodes are: " + (Integer.toString(modified_nodes)) + " and the wrong edges are: " + wrong_edge + "\n");
                errors.add(wrong_edge);

            }
            System.out.print("the wrong edges are: " + errors + "\n");
            int sum = 0;
            int sum_comparisons = 0;
            for (int a : errors) sum += a;
            for (int a : comparisons) sum_comparisons += a;
            double avg = (double) sum / errors.size();
            double avg_comparisons = (double) sum_comparisons / comparisons.size();
            double correct_edges_all = (double) K * count;
            System.out.print("in the average the differences are " + avg + "\n");

            System.out.print("the correct edges are " + Double.toString(correct_edges_all - avg) + "\n");
            System.out.print("the Q is " + Double.toString(1 - avg / K) + "\n");
            System.out.print("the comparisons were in avg " + avg_comparisons+ "\n");
            if (b%3==0) depth++;}

    }

}