package org.insa.algo.carpooling;

import org.insa.algo.AbstractSolution;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.graph.Path;

public class CarPoolingSolution extends AbstractSolution {
   
	// Optimal solution.
    private Path path1, path2;
    
    protected CarPoolingSolution(CarPoolingData data, Status status) {
        super(data, status);
    }

    /**
     * Create a new shortest-path solution.
     * 
     * @param data Original input data for this solution.
     * @param status Status of the solution (FEASIBLE / OPTIMAL).
     * @param path Path corresponding to the solution.
     */
    public CarPoolingSolution(CarPoolingData data, Status status, Path path1, Path path2) {
        super(data, status);
        this.path1 = path1;
        this.path2 = path2;
    }

}
