package org.insa.graph;
//import java.util.ArrayList;

public class Label implements Comparable<Label> {
	private Node courant;	
	private boolean marque;
	private float cout;
	private Arc pere;
	
	
	public Label(Node cour, boolean marq, float cout, Arc papa) {
		this.courant=cour;
		this.marque=marq;
		this.cout=cout;
		this.pere=papa;
	}
	
	public int compareTo(Label other) {
		if(this.cout==other.cout) {
			return 0;
		}else if(this.cout>other.cout) {
			return 1;
		}else{
			return -1;
		}
	}
	
	public void setMark(boolean B) {
		this.marque=B;
	}
	
	public float getCost() {
		return this.cout;
	}
	
	
	public void setCost(float new_cost) {
		this.cout = new_cost;
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
}
