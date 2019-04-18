package org.insa.algo.shortestpath;

import java.util.*;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Label;
import org.insa.graph.Node;
import org.insa.graph.Path;



public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
	protected ShortestPathSolution doRun() {
	    ShortestPathData data = getInputData();
	    Graph graph = data.getGraph();
	    
	    final int nbNodes = graph.size();
	    
	    // A mettre plus tard dans le code
	    
	    //Initialize array of distances.
	    //double[] distances = new double[nbNodes];
	    //Arrays.fill(distances, Double.POSITIVE_INFINITY);
	    //distances[data.getOrigin().getId()] = 0;
	    
	    notifyOriginProcessed(data.getOrigin());
	    Arc[] predecessorArcs = new Arc[nbNodes];
	    
	    ArrayList<Label> tablab=new ArrayList<Label>();
	    //boolean marque=false;
	    //Label L;
	    //Arc Daron_init=null;
	    boolean marked=false;
	    Label x=new Label(data.getOrigin(),false,0,(Arc)null);
	    
	    BinaryHeap<Label> Tas_label= new BinaryHeap<Label>();
	    
	    //lab.add(new Label(data.getOrigin(),false,0,(Arc)null));
	    Tas_label.insert(tablab.get(tablab.size()-1)); //insère le point d'origine
	   
	    
	    
	    while (!marked) {
	    	//utiliser un index pour chaque noeud par exmple tab[50]=noeud avec index =50
	    	//on peut add ds un arraylist à un index particulier. on choisit l'index du noeuds. noeuds.getID
	    	x = Tas_label.findMin();
	    	tablab.add(new Label(x.getNode(),true,0,(Arc)null));
	    	for(Arc successeur : x.getNode().getSuccessors()) {
	    		
	    		//y n'appartienenet pas à tablab
	    		if(findLabel(tablab,successeur.getDestination())==Integer.MAX_VALUE) { 
	            	tablab.add(new Label(successeur.getDestination(),false,0,successeur));
	        		if(tablab.get(tablab.size()-1).getCost() > tablab.get(this.findLabel(tablab, x.getNode())).getCost()+successeur.getLength()) {
	        			//Cost(y=cost(x)+W(x,y)
	        			tablab.get(tablab.size()-1).setCost(tablab.get(this.findLabel(tablab, x.getNode())).getCost()+successeur.getLength());
	        			//mieux de verifier si il est marqué 
	        			try {
	        				Tas_label.remove(tablab.get(findLabel(tablab,successeur.getDestination())));
	        				Tas_label.insert(new Label(successeur.getDestination(),false,0,successeur));
	        				predecessorArcs[successeur.getDestination().getId()] = successeur;
	        			}catch(Exception ElementNotFoundException){
	        				Tas_label.insert(new Label(successeur.getDestination(),false,0,successeur));
	        				predecessorArcs[successeur.getDestination().getId()] = successeur;
	        			}
	        				
	        		}
	        	}else if(!tablab.get(findLabel(tablab,successeur.getDestination())).isMarked()){ // test y appartient a tablab 
	        			
	            	if(tablab.get(findLabel(tablab,successeur.getDestination())).getCost() > tablab.get(this.findLabel(tablab, x.getNode())).getCost()+successeur.getLength()) {
	            			//Cost(y=cost(x)+W(x,y)
	            		tablab.get(findLabel(tablab,successeur.getDestination())).setCost(tablab.get(this.findLabel(tablab, x.getNode())).getCost()+successeur.getLength());
	            			
	            		try {
	            			Tas_label.remove(tablab.get(findLabel(tablab,successeur.getDestination())));
	            			Tas_label.insert(new Label(successeur.getDestination(),false,0,successeur));
	            			predecessorArcs[successeur.getDestination().getId()] = successeur;
	            		}catch(Exception ElementNotFoundException){
	            			Tas_label.insert(new Label(successeur.getDestination(),false,0,successeur));
	            			predecessorArcs[successeur.getDestination().getId()] = successeur;
	
	            		}
	            	}
	    		}
	    	}
    	}
	    
	    ShortestPathSolution solution = null;
	    
	 // Destination has no predecessor, the solution is infeasible...
	    if (predecessorArcs[data.getDestination().getId()] == null) {
	        solution = new ShortestPathSolution(data, Status.INFEASIBLE);
	    }
	    else {
	
	        // The destination has been found, notify the observers.
	        notifyDestinationReached(data.getDestination());
	
	        // Create the path from the array of predecessors...
	        ArrayList<Arc> arcs = new ArrayList<>();
	        Arc arc = predecessorArcs[data.getDestination().getId()];
	        while (arc != null) {
	            arcs.add(arc);
	            arc = predecessorArcs[arc.getOrigin().getId()];
	        }
	
	        // Reverse the path...
	        Collections.reverse(arcs);
	
	        // Create the final solution.
	        solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
	    }
	    
	    	return solution;
	}
    
	

    
    private int findLabel(ArrayList<Label> labels,Node node) {
    	int index=0;
    	boolean Trouve=false;

			while(!Trouve) {
				if(labels.get(index).getNode().equals(node)) {
					Trouve=true;
				}else if(index==labels.size()-1) {
					index=Integer.MAX_VALUE;
				}else {
					index++;
				}
			}
		return index;
    }
    
	/*private void L(Node noeud_courant, boolean b, double positiveInfinity, Arc daron_init) {
		// TODO Auto-generated method stub
		
	}*/

}
