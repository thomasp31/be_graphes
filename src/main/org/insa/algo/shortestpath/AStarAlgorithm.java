package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Collections;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.LabelStar;
import org.insa.graph.Node;
import org.insa.graph.Label;

import org.insa.graph.Path;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    @Override
    protected Label newLabel(Node cour, boolean marq, double cout, Arc papa, ShortestPathData data) {
    	Label LS = new LabelStar(cour, marq, cout, papa, data);
    	return LS;
    }
    
}


