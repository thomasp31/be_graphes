package org.insa.algo.shortestpath;

import org.insa.algo.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import java.util.Arrays;

import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Node;

import org.insa.algo.shortestpath.DijkstraAlgorithm;
import org.insa.algo.shortestpath.ShortestPathData;

import org.insa.graph.RoadInformation;
import org.insa.graph.RoadInformation.RoadType;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.*;

public class DikjstraAlgorithmTest {
	
	// Small graph use for tests
    private static Graph graph;

    // List of nodes
    private static Node[] nodes;

    // List of arcs in the graph, x1to2 is the arc from node A (0) to B (1).
    @SuppressWarnings("unused")
    private static Arc x1tox2, x1tox3, x2tox4, x2tox5, x2tox6, x3tox1, x3tox2, x3tox6, x5tox4, x5tox6, x5tox3, x6tox5;
    
    // Some paths...
    private static ShortestPathData A_Path, B_Path, C_Path, D_Path;
    private static ShortestPathData[] PathTab = new ShortestPathData[36];

    
    @BeforeClass
    public static void initAll() throws IOException {

        // 10 and 20 meters per seconds
        RoadInformation speed10 = new RoadInformation(RoadType.MOTORWAY, null, true, 36, "");

        // Create nodes
        nodes = new Node[6];
        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = new Node(i, null);
        }

        // Add arcs...
        x1tox2 = Node.linkNodes(nodes[0], nodes[1], 7, speed10, null);
        x1tox3 = Node.linkNodes(nodes[0], nodes[2], 8, speed10, null);
        x2tox4 = Node.linkNodes(nodes[1], nodes[3], 4, speed10, null);
        x5tox4 = Node.linkNodes(nodes[4], nodes[3], 2, speed10, null);
        x2tox5 = Node.linkNodes(nodes[1], nodes[4], 1, speed10, null);
        x2tox6 = Node.linkNodes(nodes[1], nodes[5], 5, speed10, null);
        x3tox1 = Node.linkNodes(nodes[2], nodes[0], 7, speed10, null);
        x3tox2 = Node.linkNodes(nodes[2], nodes[1], 2, speed10, null);
        x3tox6 = Node.linkNodes(nodes[2], nodes[5], 2, speed10, null);
        x5tox6 = Node.linkNodes(nodes[4], nodes[5], 3, speed10, null);
        x5tox3 = Node.linkNodes(nodes[4], nodes[2], 2, speed10, null);
        x6tox5 = Node.linkNodes(nodes[5], nodes[4], 3, speed10, null);

        graph = new Graph("ID", "", Arrays.asList(nodes), null);
        List<ArcInspector> L = ArcInspectorFactory.getAllFilters();
        
        A_Path = new ShortestPathData(graph,nodes[0],nodes[1],L.get(0));
        B_Path = new ShortestPathData(graph,nodes[3],nodes[4],L.get(0));
        C_Path = new ShortestPathData(graph,nodes[0],nodes[5],L.get(0));
        D_Path = new ShortestPathData(graph,nodes[5],nodes[1],L.get(0));
        
        for(int i=0;i<6;i++) {
        	for(int p=0;i<6;i++) {
        		PathTab[6*i+p] = new ShortestPathData(graph,nodes[i],nodes[p],L.get(0));
        	}
        }
        }
    
    @Test
    public void testConstructor() {
        assertEquals(graph, A_Path.getGraph());
        assertEquals(graph, B_Path.getGraph());
    }
    
    @Test
    public void testDijkstra() {
    	
    	//DijkstraAlgorithm[] Dtab= null; //= new DijkstraAlgorithm[36];
		/*
		 * ShortestPathSolution[] PS = new ShortestPathSolution[36]; for(int
		 * i=0;i<36;i++) { DijkstraAlgorithm Dtab= new DijkstraAlgorithm(PathTab[i]);
		 * PS[i] = Dtab.run(); if(i%7==0) { System.out.print("  -  "); }else if(i%5==0){
		 * System.out.println("  ("+PS[i].getPath().getLength()+", x"+(PS[i].getPath().
		 * getArcs().get(PS[i].getPath().getArcs().size()).getOrigin().getId()+1)+")  "
		 * ); }else {
		 * System.out.print("  ("+PS[i].getPath().getLength()+", x"+(PS[i].getPath().
		 * getArcs().get(PS[i].getPath().getArcs().size()).getOrigin().getId()+1)+") ");
		 * 
		 * }
		 * 
		 * //System.out.println("sol: "+PS[i]);
		 * //System.out.println("Nombre d'iteration : "+ Dtab[i].nbIter()); //ATTENTION
		 * IsValid() retournera toujours vrai si on ne décommente pas le test de
		 * validité dans l'algorithme de Dijkstra assertTrue(Dtab.IsValid()); }
		 */
    	
    	DijkstraAlgorithm D1 = new DijkstraAlgorithm(A_Path);
    	ShortestPathSolution PS1 = D1.run();
    	System.out.println("sol: "+PS1);
    	System.out.println("Nombre d'iteration : "+ D1.nbIter());
    	//ATTENTION IsValid() retournera toujours vrai si on ne décommente pas le test de validité dans l'algorithme de Dijkstra
    	assertTrue(D1.IsValid());
    	
    	DijkstraAlgorithm D2 = new DijkstraAlgorithm(B_Path);
    	ShortestPathSolution PS2 = D2.run();
    	System.out.println("sol: "+PS2);
    	System.out.println("Nombre d'iteration : "+ D2.nbIter());
    	//ATTENTION IsValid() retournera toujours vrai si on ne décommente pas le test de validité dans l'algorithme de Dijkstra
    	assertTrue(D2.IsValid());
    	
    	DijkstraAlgorithm D3 = new DijkstraAlgorithm(C_Path);
    	ShortestPathSolution PS3 = D3.run();
    	System.out.println("sol: "+PS3);
    	System.out.println("Nombre d'iteration : "+ D3.nbIter());
    	//ATTENTION IsValid() retournera toujours vrai si on ne décommente pas le test de validité dans l'algorithme de Dijkstra
    	assertTrue(D3.IsValid());
    	
    	DijkstraAlgorithm D4 = new DijkstraAlgorithm(D_Path);
    	ShortestPathSolution PS4 = D4.run();
    	System.out.println("sol: "+PS4);
    	System.out.println("Nombre d'iteration : "+ D4.nbIter());
    	//ATTENTION IsValid() retournera toujours vrai si on ne décommente pas le test de validité dans l'algorithme de Dijkstra
    	assertTrue(D4.IsValid());
    }
}
