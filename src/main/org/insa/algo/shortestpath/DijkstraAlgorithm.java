package org.insa.algo.shortestpath;

import java.util.*;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Label;
//import org.insa.graph.Node;
import org.insa.graph.Path;




public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
	protected ShortestPathSolution doRun() {
    	
	    ShortestPathData data = getInputData();
	    Graph graph = data.getGraph();
	    notifyOriginProcessed(data.getOrigin());
	    final int nbNodes = graph.size();

	    //Chemin: liste noeuds pour faire chemin plus rapide
	    //tablab: liste noeuds rencontrer pendant l'algorithme
        ArrayList<Label> Chemin = new ArrayList<Label>();
	    Label tablab[]=new Label[nbNodes];
	    
	    //Tas_label: tas necessaire � l'alorithme de Dijkstra
	    BinaryHeap<Label> Tas_label= new BinaryHeap<Label>();

	    Label x=new Label(data.getOrigin(),false,0,(Arc)null);

	    tablab[x.getNode().getId()]=x;
	    Tas_label.insert(x); //insere le label du point d'origine
	   
	    //zone debug
    	System.out.println("nb successeur: "+ tablab[x.getNode().getId()].getNode().getNumberOfSuccessors());
    	int index=0; //compteur pour 5 premiers points
    	//for(Arc successeur : x.getNode().getSuccessors()) {
        	//System.out.println("id des points successeurs: "+successeur.getDestination().getId()+" cout: "+successeur.getLength());
    	//}
	    
	    while(!Tas_label.isEmpty()) {
	    	
	    	x = Tas_label.deleteMin();
	    	
	    	if(tablab[x.getNode().getId()]==null){
	    		tablab[x.getNode().getId()]= x;
	    	}
	    	tablab[x.getNode().getId()].setMark(true);

	    	//c'est x qu'on doit mettre ds le chemin le plus court et pas le y qui est juste une mise � jour du tableau
	    	//on cr�e une list de label qui forme le chemin le plus court ensuite on fera la liste des arcs "papa" 
	    	Chemin.add(tablab[x.getNode().getId()]);

	    	for(Arc successeur : tablab[x.getNode().getId()].getNode().getSuccessors()) {
	    		
	    		//y n'appartienenet pas a� tablab on l'ajoute
	    		
	    		if(tablab[successeur.getDestination().getId()]==null) {     			
	            	tablab[successeur.getDestination().getId()]=new Label(successeur.getDestination(),false,Float.MAX_VALUE,successeur);	            	
	    		}
	    		
	    		if(!tablab[successeur.getDestination().getId()].isMarked()){ // test si y marked on saute
	    			
	        		if(tablab[successeur.getDestination().getId()].getCost()>tablab[x.getNode().getId()].getCost() + successeur.getLength()) {
	            		//Cost(y)=cost(x)+W(x,y)
	            		try {
		            		//pour faire la mise a jour:
		            		//on supprime et on remet avec le getCost � jour
	        				Tas_label.remove(tablab[successeur.getDestination().getId()]);
		            		tablab[successeur.getDestination().getId()].setCost(tablab[x.getNode().getId()].getCost() + successeur.getLength());
	        				Tas_label.insert(new Label(successeur.getDestination(),false,tablab[successeur.getDestination().getId()].getCost(),successeur));
	        				
	    	            	//zone debug
	    	    	    	if(index<4) {
	    	    		    	System.out.println("update de Tas_label: "+successeur.getDestination().getId());
	    	    	    	}
	    	    	    	
	        			}catch(Exception ElementNotFoundException){
	        				
	    	            	//zone debug
	    	    	    	if(index<4) {
	    	    		    	System.out.println("insertion ds Tas_label: "+successeur.getDestination().getId());
	    	    	    	}
	    	    	    	
	        				Tas_label.insert(new Label(successeur.getDestination(),false,tablab[successeur.getDestination().getId()].getCost(),successeur));
	        			}
            		}
	        	}
	    	}
    	index++;
	    }
	    
	    ShortestPathSolution solution = null;
	    
	 // Destination has no predecessor, the solution is infeasible...
	    if (Chemin.isEmpty()) {
	        solution = new ShortestPathSolution(data, Status.INFEASIBLE);
	    }
	    else {
	
	        // The destination has been found, notify the observers.
	        notifyDestinationReached(data.getDestination());
	
	        // Create the path from the arrayList of Node...
	        ArrayList<Arc> arcs = new ArrayList<>();
	        
	        //on commence au dernier indice et on finit � l'indice 1
	        for(int i=Chemin.size()-1;i>0;i--) {
	        	Label courant=Chemin.get(i);
		    	System.out.println("Chemin: "+courant.getNode().getId()+" i: "+i);

	            arcs.add(courant.getFather());
	        }
        Collections.reverse(arcs);

	        // Create the final solution.
	        solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
	    }
	    
	    	return solution;
	}
}




/*public class DijkstraAlgorithm extends ShortestPathAlgorithm {

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
	    Label x=new Label(data.getOrigin(),true,0,(Arc)null);
	    
	    BinaryHeap<Label> Tas_label= new BinaryHeap<Label>();
	    tablab.add(0,x);
	    Tas_label.insert(x);
	    //lab.add(new Label(data.getOrigin(),false,0,(Arc)null));
	    //Tas_label.insert(tablab.get(tablab.size()-1)); //insère le point d'origine
	   
	    
	    
	    while (!marked) {
	    	//utiliser un index pour chaque noeud par exmple tab[50]=noeud avec index =50
	    	//on peut add ds un arraylist à un index particulier. on choisit l'index du noeuds. noeuds.getID
	    	x = Tas_label.findMin();
	    	tablab.add(new Label(x.getNode(),true,0,(Arc)null));
	    	//tablab.add(x.getNode().getId(), new Label(x.getNode(),true,0,(Arc)null));
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
	    		}else {
	    			marked=true;
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
}*/



/*
 * package org.insa.algo.shortestpath;
 * 
 * import java.util.*; import org.insa.algo.AbstractSolution.Status; import
 * org.insa.algo.utils.BinaryHeap; import org.insa.graph.Arc; import
 * org.insa.graph.Graph; import org.insa.graph.Label; import
 * org.insa.graph.Node; import org.insa.graph.Path;
 * 
 * 
 * 
 * public class ijkstraAlgorithm extends ShortestPathAlgorithm {
 * 
 * public ijkstraAlgorithm(ShortestPathData data) { super(data); }
 * 
 * @Override protected ShortestPathSolution doRun() { ShortestPathData data =
 * getInputData(); Graph graph = data.getGraph();
 * 
 * final int nbNodes = graph.size();
 * 
 * 
 * notifyOriginProcessed(data.getOrigin()); Arc[] predecessorArcs = new
 * Arc[nbNodes];
 * 
 * ArrayList<Label> tablab=new ArrayList<Label>();
 * 
 * boolean marked=false; Label x=new Label(data.getOrigin(),true,0,(Arc)null);
 * 
 * BinaryHeap<Label> Tas_label= new BinaryHeap<Label>(); tablab.add(0,x);
 * Tas_label.insert(x); //insère le label du point d'origine
 * 
 * 
 * 
 * while (!Tas_label.isEmpty()) { //utiliser un index pour chaque noeud par
 * exmple tab[50]=noeud avec index =50 //on peut add ds un arraylist à un index
 * particulier. on choisit l'index du noeuds. noeuds.getID x =
 * Tas_label.deleteMin(); x.setMark(true);
 * 
 * if(!tablab.contains(x)){ tablab.add(x.getNode().getId(), x); }
 * 
 * for(Arc successeur : x.getNode().getSuccessors()) {
 * 
 * //y n'appartienenet pas à tablab
 * 
 * if(!tablab.get(successeur.getDestination().getId()).getNode().equals(
 * successeur.getDestination())) {
 * 
 * tablab.add(successeur.getDestination().getId(),new
 * Label(successeur.getDestination(),false,0,successeur));
 * 
 * if(tablab.get(successeur.getDestination().getId()).getCost()>tablab.get(x.
 * getNode().getId()).getCost() + successeur.getLength()) {
 * //Cost(y=cost(x)+W(x,y)
 * tablab.get(successeur.getDestination().getId()).setCost(tablab.get(x.getNode(
 * ).getId()).getCost() + successeur.getLength()); } try {
 * Tas_label.remove(tablab.get(successeur.getDestination().getId()));
 * Tas_label.insert(new Label(successeur.getDestination(),true,0,successeur));
 * predecessorArcs[successeur.getDestination().getId()] = successeur;
 * }catch(Exception ElementNotFoundException){ Tas_label.insert(new
 * Label(successeur.getDestination(),true,0,successeur));
 * predecessorArcs[successeur.getDestination().getId()] = successeur; }
 * 
 * }else if(!tablab.get(successeur.getDestination().getId()).isMarked()){ //
 * test si y marked ou non
 * 
 * if(tablab.get(successeur.getDestination().getId()).getCost()>tablab.get(x.
 * getNode().getId()).getCost() + successeur.getLength()) {
 * //Cost(y=cost(x)+W(x,y)
 * tablab.get(successeur.getDestination().getId()).setCost(tablab.get(x.getNode(
 * ).getId()).getCost() + successeur.getLength()); } try {
 * Tas_label.remove(tablab.get(successeur.getDestination().getId()));
 * Tas_label.insert(new Label(successeur.getDestination(),true,0,successeur));
 * predecessorArcs[successeur.getDestination().getId()] = successeur;
 * }catch(Exception ElementNotFoundException){ Tas_label.insert(new
 * Label(successeur.getDestination(),true,0,successeur));
 * predecessorArcs[successeur.getDestination().getId()] = successeur; } } } }
 * 
 * ShortestPathSolution solution = null;
 * 
 * // Destination has no predecessor, the solution is infeasible... if
 * (predecessorArcs[data.getDestination().getId()] == null) { solution = new
 * ShortestPathSolution(data, Status.INFEASIBLE); } else {
 * 
 * // The destination has been found, notify the observers.
 * notifyDestinationReached(data.getDestination());
 * 
 * // Create the path from the array of predecessors... ArrayList<Arc> arcs =
 * new ArrayList<>(); Arc arc = predecessorArcs[data.getDestination().getId()];
 * while (arc != null) { arcs.add(arc); arc =
 * predecessorArcs[arc.getOrigin().getId()]; }
 * 
 * // Reverse the path... Collections.reverse(arcs);
 * 
 * // Create the final solution. solution = new ShortestPathSolution(data,
 * Status.OPTIMAL, new Path(graph, arcs)); }
 * 
 * return solution; } }
 */
