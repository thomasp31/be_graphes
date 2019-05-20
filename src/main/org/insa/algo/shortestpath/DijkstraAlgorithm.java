package org.insa.algo.shortestpath;

import java.util.*;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Label;
import org.insa.graph.Node;
//import org.insa.graph.Node;
import org.insa.graph.Path;




public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    int i=1;	
    private double cout;
    
    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    protected Label newLabel(Node cour, boolean marq, double cout, Arc papa,ShortestPathData data) {
    	Label L=new Label(cour, marq, cout, papa);
    	return L;
    }
    
    public double getCout() {
    	return this.cout;
    }

    @Override
	protected ShortestPathSolution doRun() {
    	
	    ShortestPathData data = getInputData();
	    Graph graph = data.getGraph();
	    notifyOriginProcessed(data.getOrigin());
	    final int nbNodes = graph.size();

	    //Chemin: liste noeuds mini qui sortent du tas (il faut ensuite choisir le bon noeud pour remonter le chemin)
	    //un noeud sortant du tas n'est pas forc�ment impliqu� ds le chemin le plus court
	    //tablab: liste noeuds rencontre pendant l'algorithme
        //Label Chemin[] = new Label[nbNodes];
	    Label tablab[]=new Label[nbNodes];
	    
	    //Tas_label: tas necessaire � l'alorithme de Dijkstra
	    BinaryHeap<Label> Tas_label= new BinaryHeap<Label>();

	    Label x=newLabel(data.getOrigin(),false,0.0,(Arc)null, data);
	    tablab[x.getNode().getId()]=x;
	    tablab[x.getNode().getId()].setInsert();
	    Tas_label.insert(x); //insere le label du point d'origine
	    
	    int iter=0;
	    boolean valid=true;
	    while(!Tas_label.isEmpty() && x.getNode()!=data.getDestination()) {
	    	iter++;
	    	
		    	/*if(!Tas_label.IsValid()) {
		    		valid=false;
		    	}*/		
	    	

	    	x = Tas_label.deleteMin();
	    	//System.out.println("min: "+x.getNode().getId()+" cout: " +x.getCost()+" taille Tas: "+Tas_label.size());
	    	//Chemin[x.getNode().getId()]=x;
	    	
        	//informe les obseLabelrvateurs qu'un noeud a �t� marqu�
	    	notifyNodeMarked(tablab[x.getNode().getId()].getNode());
	    	
	    	tablab[x.getNode().getId()].setMark(true);

	    	for(Arc successeur : tablab[x.getNode().getId()].getNode().getSuccessors()){
	    		
	    		//y n'appartienenet pas a� tablab on l'ajoute
	    		
	    		if(tablab[successeur.getDestination().getId()]==null) {     			
	            	tablab[successeur.getDestination().getId()]=newLabel(successeur.getDestination(),false,Double.MAX_VALUE,successeur, data);
	            	
	            	//informe les observateurs qu'un noeud a �t� visit� pour la premi�re fois
	    	    	notifyNodeReached(tablab[successeur.getDestination().getId()].getNode());
	    	    	
	    	    	tablab[successeur.getDestination().getId()].setInsert();
	    	    	Tas_label.insert(tablab[successeur.getDestination().getId()]);

	    		}

	    		
	    		if(!tablab[successeur.getDestination().getId()].isMarked()){ // test si y marked on saute
	    			
	        		if(tablab[successeur.getDestination().getId()].getCost()>tablab[x.getNode().getId()].getCost() + data.getCost(successeur)) {
	            		//Cost(y)=cost(x)+W(x,y)
	        			
	        			if(tablab[successeur.getDestination().getId()].getInsert()) {
	        				Tas_label.remove(tablab[successeur.getDestination().getId()]);
            			}
	        			
		            	//pour faire la mise a jour:
		            	//on supprime et on remet avec le getCost � jour
    					tablab[successeur.getDestination().getId()].setCost(tablab[x.getNode().getId()].getCost() + data.getCost(successeur));
    					tablab[successeur.getDestination().getId()].setFather(successeur);


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
	        
	        Label courant=tablab[data.getDestination().getId()];

	        //pb map insa point 230 � 1000???
	        while(courant.getNode().getId()!=data.getOrigin().getId()) {
	            arcs.add(courant.getFather());
	            i++;
	            //cout+=courant.getCost();
	        	courant=tablab[courant.getFather().getOrigin().getId()];
	        }
        Collections.reverse(arcs);
        //Calcul du cout du chemin
        /*for(Arc A: arcs) {
        	cout+=A.getLength();
        }*/
    	/*System.out.println("taille chemin: "+i+" nb iterations: "+iter);
    	if(valid) {
        	System.out.println("youpi");

    	}else {
        	System.out.println("triste");

    	}*/

	        // Create the final solution.
	        solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
	    }
	    
	    	return solution;
	}
}
