package org.insa.graph;

import org.insa.graph.Point;
public class LabelStar extends Label implements Comparable<Label> {

	private double coutEstime;
	public LabelStar(Node cour, boolean marq, double cout, Arc papa){
		super(cour,marq,cout,papa);
		this.coutEstime=Double.MAX_VALUE;
	}
	

	public double getTotalCost() {
		return this.getCost()+this.coutEstime;
	}
	
	public double getEstimation(LabelStar other) {
		return Point.distance(this.getNode().getPoint(),other.getNode().getPoint());
	}
	
	public void setEstimation(Node other) {
		this.coutEstime=Point.distance(this.getNode().getPoint(), other.getPoint());
	}
}
