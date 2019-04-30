package org.insa.algo.shortestpath;

import java.util.*;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Label;
import org.insa.graph.Node;
import org.insa.graph.Path;



public class ijkstraAlgorithm extends ShortestPathAlgorithm {

    public ijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
	protected ShortestPathSolution doRun() {
	    ShortestPathData data = getInputData();
	    Graph graph = data.getGraph();
	    
	    final int nbNodes = graph.size();
	    
	   
	    notifyOriginProcessed(data.getOrigin());
	    Arc[] predecessorArcs = new Arc[nbNodes];
	    
	    ArrayList<Label> tablab=new ArrayList<Label>();
	    
	    boolean marked=false;
	    Label x=new Label(data.getOrigin(),true,0,(Arc)null);
	    
	    BinaryHeap<Label> Tas_label= new BinaryHeap<Label>();
	    tablab.add(0,x);
	    Tas_label.insert(x); //insère le label du point d'origine
	   
	    
	    
	    while (!Tas_label.isEmpty()) {
	    	//utiliser un index pour chaque noeud par exmple tab[50]=noeud avec index =50
	    	//on peut add ds un arraylist à un index particulier. on choisit l'index du noeuds. noeuds.getID
	    	x = Tas_label.deleteMin();
	    	x.setMark(true);
	    	
	    	if(!tablab.contains(x)){
	    		tablab.add(x.getNode().getId(), x);
	    	}
	    	
	    	for(Arc successeur : x.getNode().getSuccessors()) {
	    		
	    		//y n'appartienenet pas à tablab
	    		
	    		if(!tablab.get(successeur.getDestination().getId()).getNode().equals(successeur.getDestination())) { 
	    			
	            	tablab.add(successeur.getDestination().getId(),new Label(successeur.getDestination(),false,0,successeur));
	            	
	            	if(tablab.get(successeur.getDestination().getId()).getCost()>tablab.get(x.getNode().getId()).getCost() + successeur.getLength()) {
	            		//Cost(y=cost(x)+W(x,y)
	            		tablab.get(successeur.getDestination().getId()).setCost(tablab.get(x.getNode().getId()).getCost() + successeur.getLength());
            		}
	            	try {
        				Tas_label.remove(tablab.get(successeur.getDestination().getId()));
        				Tas_label.insert(new Label(successeur.getDestination(),true,0,successeur));
        				predecessorArcs[successeur.getDestination().getId()] = successeur;
        			}catch(Exception ElementNotFoundException){
        				Tas_label.insert(new Label(successeur.getDestination(),true,0,successeur));
        				predecessorArcs[successeur.getDestination().getId()] = successeur;
        			}
	        
	        	}else if(!tablab.get(successeur.getDestination().getId()).isMarked()){ // test si y marked ou non
	        			
	        		if(tablab.get(successeur.getDestination().getId()).getCost()>tablab.get(x.getNode().getId()).getCost() + successeur.getLength()) {
	            		//Cost(y=cost(x)+W(x,y)
	            		tablab.get(successeur.getDestination().getId()).setCost(tablab.get(x.getNode().getId()).getCost() + successeur.getLength());
            		}
	            	try {
        				Tas_label.remove(tablab.get(successeur.getDestination().getId()));
        				Tas_label.insert(new Label(successeur.getDestination(),true,0,successeur));
        				predecessorArcs[successeur.getDestination().getId()] = successeur;
        			}catch(Exception ElementNotFoundException){
        				Tas_label.insert(new Label(successeur.getDestination(),true,0,successeur));
        				predecessorArcs[successeur.getDestination().getId()] = successeur;
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
}