package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Collections;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.LabelStar;
import org.insa.graph.Label;

import org.insa.graph.Path;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    @Override
	protected ShortestPathSolution doRun() {
    	
	    ShortestPathData data = getInputData();
	    Graph graph = data.getGraph();
	    notifyOriginProcessed(data.getOrigin());
	    final int nbNodes = graph.size();

	    //Chemin: liste noeuds mini qui sortent du tas (il faut ensuite choisir le bon noeud pour remonter le chemin)
	    //un noeud sortant du tas n'est pas forcément impliqué ds le chemin le plus court
	    //tablab: liste noeuds rencontre pendant l'algorithme
        //Label Chemin[] = new Label[nbNodes];
	    LabelStar tablab[]=new LabelStar[nbNodes];
	    
	    //Tas_label: tas necessaire à l'alorithme de Dijkstra
	    BinaryHeap<LabelStar> Tas_label= new BinaryHeap<LabelStar>();

	    LabelStar x=new LabelStar(data.getOrigin(),false,0.0,(Arc)null);
	    x.setEstimation(data.getDestination());
	    tablab[x.getNode().getId()]=x;
	    tablab[x.getNode().getId()].setInsert();
	    Tas_label.insert(x); //insere le label du point d'origine
	    
	    int iter=0;
	    boolean valid=true;
	    while(!Tas_label.isEmpty() && x.getNode()!=data.getDestination()) {
	    	iter++;
	    	
		    	if(!Tas_label.IsValid()) {
		    		valid=false;
		    	}	    		
	    	

	    	x = Tas_label.deleteMin();
	    	//System.out.println("min: "+x.getNode().getId()+" cout: " +x.getCost()+" taille Tas: "+Tas_label.size());
	    	//Chemin[x.getNode().getId()]=x;
	    	
        	//informe les observateurs qu'un noeud a été marqué
	    	notifyNodeMarked(tablab[x.getNode().getId()].getNode());
	    	
	    	tablab[x.getNode().getId()].setMark(true);

	    	for(Arc successeur : tablab[x.getNode().getId()].getNode().getSuccessors()) {
	    		
	    		//y n'appartienenet pas a  tablab on l'ajoute
	    		
	    		if(tablab[successeur.getDestination().getId()]==null) {     			
	            	tablab[successeur.getDestination().getId()]=new LabelStar(successeur.getDestination(),false,Double.MAX_VALUE,successeur);
	            	tablab[successeur.getDestination().getId()].setEstimation(data.getDestination());
	            	//informe les observateurs qu'un noeud a été visité pour la première fois
	    	    	notifyNodeReached(tablab[successeur.getDestination().getId()].getNode());
	    	    	
	    	    	tablab[successeur.getDestination().getId()].setInsert();
	    	    	Tas_label.insert(tablab[successeur.getDestination().getId()]);

	    		}

	    		
	    		if(!tablab[successeur.getDestination().getId()].isMarked()){ // test si y marked on saute
	    			
	        		if(tablab[successeur.getDestination().getId()].getCost()>tablab[x.getNode().getId()].getCost() + data.getCost(successeur)) {
	            		//Cost(y)=cost(x)+W(x,y)

		            	//pour faire la mise a jour:
		            	//on supprime et on remet avec le getCost à jour
    					tablab[successeur.getDestination().getId()].setCost(tablab[x.getNode().getId()].getCost() + data.getCost(successeur));
    					tablab[successeur.getDestination().getId()].setFather(successeur);

	            			if(tablab[successeur.getDestination().getId()].getInsert()) {
		        				Tas_label.remove(tablab[successeur.getDestination().getId()]);
	            			}

	    	    	    	Tas_label.insert(tablab[successeur.getDestination().getId()]);

            		}
	        	}
	    	}
	    }
	    
	    ShortestPathSolution solution = null;
	   
	    
	 // Destination has no predecessor, the solution is infeasible...
	    if (tablab.length==0 || tablab[data.getDestination().getId()]==null) {
	        solution = new ShortestPathSolution(data, Status.INFEASIBLE);
	    }
	    else {
	
	        // The destination has been found, notify the observers.
	        notifyDestinationReached(data.getDestination());
	
	        // Create the path from the arrayList of Node...
	        ArrayList<Arc> arcs = new ArrayList<>();
	        
	        LabelStar courant=tablab[data.getDestination().getId()];
	        int i=1;
	        //pb map insa point 230 à 1000???
	        while(courant.getNode().getId()!=data.getOrigin().getId()) {
	            arcs.add(courant.getFather());
	            i++;
	        	courant=tablab[courant.getFather().getOrigin().getId()];
	        }
        Collections.reverse(arcs);
    	System.out.println("taille chemin: "+i+" nb iterations: "+iter);
    	if(valid) {
        	System.out.println("youpi");

    	}else {
        	System.out.println("triste");

    	}

	        // Create the final solution.
	        solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
	    }
	    
	    	return solution;
    }

}
