package org.insa.graph;

public class Label implements Comparable<Label> {
	private Node courant;	
	private boolean marque;
	private double cout;
	private Arc pere;
	private boolean insert;
	
	public Label(Node cour, boolean marq, double cout, Arc papa) {
		this.courant=cour;
		this.marque=marq;
		this.cout=cout;
		this.pere=papa;
		this.insert=false;
	}
	
	public int compareTo(Label other) {
		if(this.getTotalCost()==other.getTotalCost()) {
			return 0;
		}else if(this.getTotalCost()>other.getTotalCost()) {
			return 1;
		}else{
			return -1;
		}
	}
	
	public void setMark(boolean B) {
		this.marque=B;
	}
	
	public double getCost() {
		return this.cout;
	}
	
	
	public void setCost(double new_cost) {
		this.cout = new_cost;
	}
	
	public void setFather(Arc new_father) {
		this.pere = new_father;
	}
	
	public void setInsert() {
		this.insert = true;
	}
	
	public boolean getInsert() {
		return this.insert;
	}
	
	public boolean isMarked() {
		return this.marque;
	}
	
	public Node getNode() {
		return this.courant;
	}
	public Arc getFather() {
		return this.pere;
	}
	
	public double getTotalCost() {
		return this.cout;
	}
	
}
