package org.insa.graph;

import org.insa.algo.AbstractInputData.Mode;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.graph.Point;
public class LabelStar extends Label {

	private double coutEstime;
	public LabelStar(Node cour, boolean marq, double cout, Arc papa, ShortestPathData data){
		super(cour,marq,cout,papa);
		setEstimation(data);
	}
	

	public double getTotalCost() {
		return this.getCost()+this.coutEstime;
	}
	
	public void setEstimation(ShortestPathData data) {
		if (data.getMode()==Mode.LENGTH) {
			this.coutEstime=Point.distance(this.getNode().getPoint(), data.getDestination().getPoint());
		}else if(data.getMode()==Mode.TIME) {
			this.coutEstime=Point.distance(this.getNode().getPoint(), data.getDestination().getPoint())/(Math.max(data.getGraph().getGraphInformation().getMaximumSpeed(), data.getMaximumSpeed())/3.6);
		}
		//this.coutEstime=0;
	}
}
