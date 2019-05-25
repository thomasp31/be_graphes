package org.insa.algo.carpooling;

import org.insa.algo.AbstractInputData;
import org.insa.algo.ArcInspector;
import org.insa.graph.Graph;
import org.insa.graph.Node;

public class CarPoolingData extends AbstractInputData {

    private final Node originU1,originU2, destinationD1,destinationD2, intersection;

    protected CarPoolingData(Graph graph,Node origin1, Node destination1,Node origin2, Node destination2, Node intersection, ArcInspector arcFilter) {
        super(graph, arcFilter);
        this.originU1 = origin1;
        this.destinationD1 = destination1;
        this.originU2 = origin2;
        this.destinationD2 = destination2;
        this.intersection = intersection;
    }

    /**
     * @return Origin node for the path.
     */
    public Node getOriginU1() {
        return originU1;
    }
    
    public Node getOriginU2() {
        return originU2;
    }

    /**
     * @return Destination node for the path.
     */
    public Node getDestinationD1() {
        return destinationD1;
    }
    public Node getDestinationD2() {
        return destinationD2;
    }

    /**
     * @return Intersection node for the path.
     */
    public Node getIntersection() {
        return intersection;
    }
    
    /**
     * @return ArcInpsector node for the path.
     */
    public ArcInspector getArcInspector() {
        return this.arcInspector;
    }
    
    @Override
    public String toString() {
        return "Car pooling from #" + originU1.getId()+ " and "+ originU2.getId() + " to #" + destinationD1.getId() + " and " 
        		+ destinationD2.getId() + " ["
                + this.arcInspector.toString().toLowerCase() + "]";
    }
}
