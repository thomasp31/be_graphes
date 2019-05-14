package org.insa.algo.shortestpath;

import org.insa.algo.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.Path;

import org.insa.algo.shortestpath.DijkstraAlgorithm;
import org.insa.algo.shortestpath.BellmanFordAlgorithm;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.algo.AbstractInputData;

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

    // List of arcs in the graph, a2b is the arc from node A (0) to B (1).
    @SuppressWarnings("unused")
    private static Arc a2b, a2c, b2d, b2e, b2f, c2a, c2b, c2f, e2d, e2f, e2c, f2e;
    
    // Some paths...
    private static ShortestPathData A_Path, B_Path, C_Path, D_Path, F_Path;
    
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
        a2b = Node.linkNodes(nodes[0], nodes[1], 7, speed10, null);
        a2c = Node.linkNodes(nodes[0], nodes[2], 8, speed10, null);
        e2d = Node.linkNodes(nodes[4], nodes[3], 2, speed10, null);
        b2e = Node.linkNodes(nodes[1], nodes[4], 1, speed10, null);
        b2f = Node.linkNodes(nodes[1], nodes[5], 5, speed10, null);
        c2a = Node.linkNodes(nodes[2], nodes[0], 7, speed10, null);
        c2b = Node.linkNodes(nodes[2], nodes[1], 2, speed10, null);
        c2f = Node.linkNodes(nodes[2], nodes[5], 2, speed10, null);
        e2f = Node.linkNodes(nodes[4], nodes[5], 3, speed10, null);
        e2c = Node.linkNodes(nodes[4], nodes[2], 2, speed10, null);
        f2e = Node.linkNodes(nodes[5], nodes[4], 3, speed10, null);

        graph = new Graph("ID", "", Arrays.asList(nodes), null);
        List<ArcInspector> L = ArcInspectorFactory.getAllFilters();
        
        A_Path = new ShortestPathData(graph,nodes[0],nodes[1],L.get(0));
        B_Path = new ShortestPathData(graph,nodes[3],nodes[4],L.get(0));
        C_Path = new ShortestPathData(graph,nodes[0],nodes[5],L.get(0));
        D_Path = new ShortestPathData(graph,nodes[5],nodes[1],L.get(0));
        }
    
    @Test
    public void testConstructor() {
        assertEquals(graph, A_Path.getGraph());
        assertEquals(graph, B_Path.getGraph());
    }
    
    @Test
    public void testDijkstra() {
    	DijkstraAlgorithm D1 = new DijkstraAlgorithm(A_Path);
    	ShortestPathSolution PS1 = D1.run();
    	System.out.println("sol: "+PS1);
    	
    	DijkstraAlgorithm D2 = new DijkstraAlgorithm(B_Path);
    	ShortestPathSolution PS2 = D2.run();
    	System.out.println("sol: "+PS2);
    	
    	DijkstraAlgorithm D3 = new DijkstraAlgorithm(C_Path);
    	ShortestPathSolution PS3 = D3.run();
    	System.out.println("sol: "+PS3);
    	
    	DijkstraAlgorithm D4 = new DijkstraAlgorithm(D_Path);
    	ShortestPathSolution PS4 = D4.run();
    	System.out.println("sol: "+PS4);
    }
    
    
    
    @Test
    public void testFromA() {
    	
    }

}
