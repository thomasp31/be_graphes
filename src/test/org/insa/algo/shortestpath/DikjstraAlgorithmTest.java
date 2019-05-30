package org.insa.algo.shortestpath;

import org.insa.algo.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
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
import java.io.File;


public class DikjstraAlgorithmTest {
	
	// Small graph use for tests
    private static Graph graph;

    // List of nodes
    private static Node[] nodes;

    // List of arcs in the graph, x1to2 is the arc from node A (0) to B (1).
    @SuppressWarnings("unused")
    private static Arc x1tox2, x1tox3, x2tox4, x2tox5, x2tox8, x3tox1, x3tox2, x3tox8, x5tox4, x5tox8, x5tox3, x8tox5;
    
    // Some paths...
    private static ShortestPathData A_Path, B_Path, C_Path, D_Path;
    private static ShortestPathData A1_Path, B1_Path, C1_Path, D1_Path;
    private static ShortestPathData A2_Path, B2_Path, C2_Path, D2_Path;
    private static ShortestPathData A3_Path, B3_Path, C3_Path, D3_Path;
    private static ShortestPathData A4_Path, B4_Path, C4_Path, D4_Path;
    private static ShortestPathData A5_Path, B5_Path, C5_Path, D5_Path;
    private static ShortestPathData A7_Path, B7_Path;
    private static ShortestPathData A8_Path, B8_Path, C8_Path, D8_Path;


    private static ShortestPathData[] PathTab = new ShortestPathData[38];

    
    @BeforeClass
    public static void initAll() throws IOException {

        // 10 and 20 meters per seconds
        RoadInformation speed10 = new RoadInformation(RoadType.MOTORWAY, null, true, 38, "");

        // Create nodes
        nodes = new Node[8];
        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = new Node(i, null);
        }

        // Add arcs...
        x1tox2 = Node.linkNodes(nodes[0], nodes[1], 8, speed10, null);
        x1tox3 = Node.linkNodes(nodes[0], nodes[2], 8, speed10, null);
        x2tox4 = Node.linkNodes(nodes[1], nodes[3], 4, speed10, null);
        x5tox4 = Node.linkNodes(nodes[4], nodes[3], 2, speed10, null);
        x2tox5 = Node.linkNodes(nodes[1], nodes[4], 1, speed10, null);
        x2tox8 = Node.linkNodes(nodes[1], nodes[5], 5, speed10, null);
        x3tox1 = Node.linkNodes(nodes[2], nodes[0], 8, speed10, null);
        x3tox2 = Node.linkNodes(nodes[2], nodes[1], 2, speed10, null);
        x3tox8 = Node.linkNodes(nodes[2], nodes[5], 2, speed10, null);
        x5tox8 = Node.linkNodes(nodes[4], nodes[5], 3, speed10, null);
        x5tox3 = Node.linkNodes(nodes[4], nodes[2], 2, speed10, null);
        x8tox5 = Node.linkNodes(nodes[5], nodes[4], 3, speed10, null);

        graph = new Graph("ID", "", Arrays.asList(nodes), null);
        List<ArcInspector> L = ArcInspectorFactory.getAllFilters();
        
        A_Path = new ShortestPathData(graph,nodes[0],nodes[1],L.get(0));
        B_Path = new ShortestPathData(graph,nodes[0],nodes[2],L.get(0));
        C_Path = new ShortestPathData(graph,nodes[0],nodes[3],L.get(0));
        D_Path = new ShortestPathData(graph,nodes[0],nodes[4],L.get(0));
        A1_Path = new ShortestPathData(graph,nodes[0],nodes[5],L.get(0));
        
        B1_Path = new ShortestPathData(graph,nodes[1],nodes[0],L.get(0));
        C1_Path = new ShortestPathData(graph,nodes[1],nodes[2],L.get(0));
        D1_Path = new ShortestPathData(graph,nodes[1],nodes[3],L.get(0));
        A2_Path = new ShortestPathData(graph,nodes[1],nodes[4],L.get(0));
        B2_Path = new ShortestPathData(graph,nodes[1],nodes[5],L.get(0));
        
        A8_Path = new ShortestPathData(graph,nodes[2],nodes[0],L.get(0));
        C2_Path = new ShortestPathData(graph,nodes[2],nodes[1],L.get(0));
        D2_Path = new ShortestPathData(graph,nodes[2],nodes[3],L.get(0));
        A3_Path = new ShortestPathData(graph,nodes[2],nodes[4],L.get(0));
        B3_Path = new ShortestPathData(graph,nodes[2],nodes[5],L.get(0));
        
        B8_Path = new ShortestPathData(graph,nodes[3],nodes[0],L.get(0));
        C3_Path = new ShortestPathData(graph,nodes[3],nodes[2],L.get(0));
        D3_Path = new ShortestPathData(graph,nodes[3],nodes[1],L.get(0));
        A4_Path = new ShortestPathData(graph,nodes[3],nodes[4],L.get(0));
        B4_Path = new ShortestPathData(graph,nodes[3],nodes[5],L.get(0));
        
        C8_Path = new ShortestPathData(graph,nodes[4],nodes[0],L.get(0));
        C4_Path = new ShortestPathData(graph,nodes[4],nodes[2],L.get(0));
        D4_Path = new ShortestPathData(graph,nodes[4],nodes[3],L.get(0));
        A5_Path = new ShortestPathData(graph,nodes[4],nodes[1],L.get(0));
        B5_Path = new ShortestPathData(graph,nodes[4],nodes[5],L.get(0));
        
        D8_Path = new ShortestPathData(graph,nodes[5],nodes[0],L.get(0));
        C5_Path = new ShortestPathData(graph,nodes[5],nodes[2],L.get(0));
        D5_Path = new ShortestPathData(graph,nodes[5],nodes[3],L.get(0));
        A7_Path = new ShortestPathData(graph,nodes[5],nodes[4],L.get(0));
        B7_Path = new ShortestPathData(graph,nodes[5],nodes[1],L.get(0));
        
        
        /*for(int i=0;i<8;i++) {
        	for(int p=0;i<8;i++) {
        		PathTab[8*i+p] = new ShortestPathData(graph,nodes[i],nodes[p],L.get(0));
        	}
        }*/
        }
    
    @Test
    public void testConstructor() {
        assertEquals(graph, A_Path.getGraph());
        assertEquals(graph, B_Path.getGraph());
    }
    
    @Test
    public void testDijkstra() throws IOException {
    	
    	//DijkstraAlgorithm[] Dtab= null; //= new DijkstraAlgorithm[38];
		/*
		 * ShortestPathSolution[] PS = new ShortestPathSolution[38]; for(int
		 * i=0;i<38;i++) { DijkstraAlgorithm Dtab= new DijkstraAlgorithm(PathTab[i]);
		 * PS[i] = Dtab.run(); if(i%8==0) { System.out.print("  -  "); }else if(i%5==0){
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
		 * IsValid() retournera toujours vrai si on ne dÃ©commente pas le test de
		 * validitÃ© dans l'algorithme de Dijkstra assertTrue(Dtab.IsValid()); }
		 */
    	
    	DijkstraAlgorithm D1 = new DijkstraAlgorithm(A_Path);
    	ShortestPathSolution PS1 = D1.run();
    	System.out.println("sol: "+PS1+"\n");
    	System.out.println("Nombre d'iteration : "+ D1.nbIter());
		File ff=new File("C:/Users/Katillär/Documents/INSA/3ir/be_graphes/resultatcartesimple.txt"); // définir l'arborescence
		ff.createNewFile();
		FileWriter ffw=new FileWriter(ff,true);
		
		ffw.write("sol: "+PS1+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("Nombre d'iteration : "+ D1.nbIter()+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("\n"); // forcer le passage à la ligne
		// fermer le fichier à la fin des traitements
		//renvoie exactement la meme valeur donc ok
		//tests rÃ©alisÃ©s sur plusieurs map
    	//ATTENTION IsValid() retournera toujours vrai si on ne dÃ©commente pas le test de validitÃ© dans l'algorithme de Dijkstra
    	assertTrue(D1.IsValid());
    	
    	DijkstraAlgorithm D2 = new DijkstraAlgorithm(B_Path);
    	ShortestPathSolution PS2 = D2.run();
    	System.out.println("sol: "+PS2);
    	System.out.println("Nombre d'iteration : "+ D2.nbIter());
    	//ATTENTION IsValid() retournera toujours vrai si on ne dÃ©commente pas le test de validitÃ© dans l'algorithme de Dijkstra
    	assertTrue(D2.IsValid());
		ffw.write("sol: "+PS2+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("Nombre d'iteration : "+ D2.nbIter()+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("\n"); // forcer le passage à la ligne
    	
    	DijkstraAlgorithm D3 = new DijkstraAlgorithm(C_Path);
    	ShortestPathSolution PS3 = D3.run();
    	System.out.println("sol: "+PS3);
    	System.out.println("Nombre d'iteration : "+ D3.nbIter());
    	//ATTENTION IsValid() retournera toujours vrai si on ne dÃ©commente pas le test de validitÃ© dans l'algorithme de Dijkstra
    	assertTrue(D3.IsValid());
		ffw.write("sol: "+PS3+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("Nombre d'iteration : "+ D3.nbIter()+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("\n"); // forcer le passage à la ligne
 
    	
    	DijkstraAlgorithm D4 = new DijkstraAlgorithm(D_Path);
    	ShortestPathSolution PS4 = D4.run();
    	System.out.println("sol: "+PS4);
    	System.out.println("Nombre d'iteration : "+ D4.nbIter());
    	assertTrue(D4.IsValid());
		ffw.write("sol: "+PS4+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("Nombre d'iteration : "+ D4.nbIter()+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("\n"); // forcer le passage à la ligne
		 // fermer le fichier à la fin des traitements
		
    	DijkstraAlgorithm D5 = new DijkstraAlgorithm(A1_Path);
    	ShortestPathSolution PS5 = D5.run();
    	System.out.println("sol: "+PS5);
    	System.out.println("Nombre d'iteration : "+ D5.nbIter());
    	assertTrue(D5.IsValid());
		ffw.write("sol: "+PS5+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("Nombre d'iteration : "+ D5.nbIter()+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("\n"); // forcer le passage à la ligne
		  // fermer le fichier à la fin des traitements
		
    	DijkstraAlgorithm D8 = new DijkstraAlgorithm(A2_Path);
    	ShortestPathSolution PS8 = D8.run();
    	System.out.println("sol: "+PS8);
    	System.out.println("Nombre d'iteration : "+ D8.nbIter());
    	assertTrue(D8.IsValid());
		ffw.write("sol: "+PS8+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("Nombre d'iteration : "+ D8.nbIter()+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("\n"); // forcer le passage à la ligne
		  // fermer le fichier à la fin des traitements
		
		
    	DijkstraAlgorithm D20 = new DijkstraAlgorithm(A3_Path);
    	ShortestPathSolution PS20 = D20.run();
    	System.out.println("sol: "+PS20);
    	System.out.println("Nombre d'iteration : "+ D20.nbIter());
    	assertTrue(D20.IsValid());
		ffw.write("sol: "+PS20+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("Nombre d'iteration : "+ D20.nbIter()+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("\n"); // forcer le passage à la ligne
		  // fermer le fichier à la fin des traitements
		
    	DijkstraAlgorithm D12 = new DijkstraAlgorithm(A4_Path);
    	ShortestPathSolution PS12 = D12.run();
    	System.out.println("sol: "+PS12);
    	System.out.println("Nombre d'iteration : "+ D12.nbIter());
    	assertTrue(D12.IsValid());
		ffw.write("sol: "+PS12+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("Nombre d'iteration : "+ D12.nbIter()+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("\n"); // forcer le passage à la ligne
		  // fermer le fichier à la fin des traitements
		
    	DijkstraAlgorithm D35 = new DijkstraAlgorithm(A5_Path);
    	ShortestPathSolution PS17 = D35.run();
    	System.out.println("sol: "+PS17);
    	System.out.println("Nombre d'iteration : "+ D35.nbIter());
    	assertTrue(D35.IsValid());
		ffw.write("sol: "+D35+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("Nombre d'iteration : "+ D35.nbIter()+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("\n"); // forcer le passage à la ligne
		  // fermer le fichier à la fin des traitements
		
    	DijkstraAlgorithm D36 = new DijkstraAlgorithm(A8_Path);
    	ShortestPathSolution PS36 = D36.run();
    	System.out.println("sol: "+PS36);
    	System.out.println("Nombre d'iteration : "+ D36.nbIter());
    	assertTrue(D36.IsValid());
		ffw.write("sol: "+PS36+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("Nombre d'iteration : "+ D36.nbIter()+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("\n"); // forcer le passage à la ligne
		  // fermer le fichier à la fin des traitements
		
    	DijkstraAlgorithm D19 = new DijkstraAlgorithm(A7_Path);
    	ShortestPathSolution PS19 = D19.run();
    	System.out.println("sol: "+PS19);
    	System.out.println("Nombre d'iteration : "+ D19.nbIter());
    	assertTrue(D19.IsValid());
		ffw.write("sol: "+PS19+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("Nombre d'iteration : "+ D19.nbIter()+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("\n"); // forcer le passage à la ligne
		  // fermer le fichier à la fin des traitements
		
		
		
		
		
		
		
		DijkstraAlgorithm D31 = new DijkstraAlgorithm(B1_Path);
    	ShortestPathSolution PS31 = D31.run();
    	System.out.println("sol: "+PS31);
    	System.out.println("Nombre d'iteration : "+ D31.nbIter());
    	assertTrue(D5.IsValid());
		ffw.write("sol: "+PS31+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("Nombre d'iteration : "+ D31.nbIter()+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("\n"); // forcer le passage à la ligne
		  // fermer le fichier à la fin des traitements
		
    	DijkstraAlgorithm D32 = new DijkstraAlgorithm(B2_Path);
    	ShortestPathSolution PS32 = D32.run();
    	System.out.println("sol: "+PS32);
    	System.out.println("Nombre d'iteration : "+ D32.nbIter());
    	assertTrue(D32.IsValid());
		ffw.write("sol: "+PS32+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("Nombre d'iteration : "+ D32.nbIter()+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("\n"); // forcer le passage à la ligne
		  // fermer le fichier à la fin des traitements
		
		
    	DijkstraAlgorithm D33 = new DijkstraAlgorithm(B3_Path);
    	ShortestPathSolution PS33 = D8.run();
    	System.out.println("sol: "+PS33);
    	System.out.println("Nombre d'iteration : "+ D33.nbIter());
    	assertTrue(D33.IsValid());
		ffw.write("sol: "+PS33+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("Nombre d'iteration : "+ D33.nbIter()+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("\n"); // forcer le passage à la ligne
		  // fermer le fichier à la fin des traitements
		
    	DijkstraAlgorithm D34 = new DijkstraAlgorithm(B4_Path);
    	ShortestPathSolution PS34 = D34.run();
    	System.out.println("sol: "+PS34);
    	System.out.println("Nombre d'iteration : "+ D34.nbIter());
    	assertTrue(D34.IsValid());
		ffw.write("sol: "+PS34+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("Nombre d'iteration : "+ D34.nbIter()+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("\n"); // forcer le passage à la ligne
		  // fermer le fichier à la fin des traitements
		
    	DijkstraAlgorithm D45 = new DijkstraAlgorithm(B5_Path);
    	ShortestPathSolution PS45 = D45.run();
    	System.out.println("sol: "+PS45);
    	System.out.println("Nombre d'iteration : "+ D45.nbIter());
    	assertTrue(D45.IsValid());
		ffw.write("sol: "+D45+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("Nombre d'iteration : "+ D45.nbIter()+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("\n"); // forcer le passage à la ligne
		  // fermer le fichier à la fin des traitements
		
    	DijkstraAlgorithm D46 = new DijkstraAlgorithm(B8_Path);
    	ShortestPathSolution PS46 = D46.run();
    	System.out.println("sol: "+PS46);
    	System.out.println("Nombre d'iteration : "+ D46.nbIter());
    	assertTrue(D46.IsValid());
		ffw.write("sol: "+PS46+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("Nombre d'iteration : "+ D46.nbIter()+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("\n"); // forcer le passage à la ligne
		  // fermer le fichier à la fin des traitements
		
    	DijkstraAlgorithm D49 = new DijkstraAlgorithm(B7_Path);
    	ShortestPathSolution PS49 = D49.run();
    	System.out.println("sol: "+PS49);
    	System.out.println("Nombre d'iteration : "+ D49.nbIter());
    	assertTrue(D49.IsValid());
		ffw.write("sol: "+PS49+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("Nombre d'iteration : "+ D49.nbIter()+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("\n"); // forcer le passage à la ligne
		  // fermer le fichier à la fin des traitements
		
		
		
		
		
		DijkstraAlgorithm D41 = new DijkstraAlgorithm(C1_Path);
    	ShortestPathSolution PS41 = D41.run();
    	System.out.println("sol: "+PS41);
    	System.out.println("Nombre d'iteration : "+ D41.nbIter());
    	assertTrue(D41.IsValid());
		ffw.write("sol: "+PS41+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("Nombre d'iteration : "+ D41.nbIter()+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("\n"); // forcer le passage à la ligne
		  // fermer le fichier à la fin des traitements
		
    	DijkstraAlgorithm D42 = new DijkstraAlgorithm(C2_Path);
    	ShortestPathSolution PS42 = D42.run();
    	System.out.println("sol: "+PS42);
    	System.out.println("Nombre d'iteration : "+ D42.nbIter());
    	assertTrue(D42.IsValid());
		ffw.write("sol: "+PS42+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("Nombre d'iteration : "+ D42.nbIter()+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("\n"); // forcer le passage à la ligne
		  // fermer le fichier à la fin des traitements
		
		
    	DijkstraAlgorithm D43 = new DijkstraAlgorithm(C3_Path);
    	ShortestPathSolution PS43 = D43.run();
    	System.out.println("sol: "+PS43);
    	System.out.println("Nombre d'iteration : "+ D33.nbIter());
    	assertTrue(D43.IsValid());
		ffw.write("sol: "+PS43+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("Nombre d'iteration : "+ D43.nbIter()+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("\n"); // forcer le passage à la ligne
		  // fermer le fichier à la fin des traitements
		
    	DijkstraAlgorithm D44 = new DijkstraAlgorithm(C4_Path);
    	ShortestPathSolution PS44 = D44.run();
    	System.out.println("sol: "+PS44);
    	System.out.println("Nombre d'iteration : "+ D44.nbIter());
    	assertTrue(D44.IsValid());
		ffw.write("sol: "+PS44+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("Nombre d'iteration : "+ D44.nbIter()+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("\n"); // forcer le passage à la ligne
		  // fermer le fichier à la fin des traitements
		
    	DijkstraAlgorithm D55 = new DijkstraAlgorithm(C5_Path);
    	ShortestPathSolution PS55 = D55.run();
    	System.out.println("sol: "+PS55);
    	System.out.println("Nombre d'iteration : "+ D55.nbIter());
    	assertTrue(D55.IsValid());
		ffw.write("sol: "+D55+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("Nombre d'iteration : "+ D55.nbIter()+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("\n"); // forcer le passage à la ligne
		  // fermer le fichier à la fin des traitements
		
    	DijkstraAlgorithm D56 = new DijkstraAlgorithm(C8_Path);
    	ShortestPathSolution PS56 = D56.run();
    	System.out.println("sol: "+PS56);
    	System.out.println("Nombre d'iteration : "+ D56.nbIter());
    	assertTrue(D56.IsValid());
		ffw.write("sol: "+PS56+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("Nombre d'iteration : "+ D56.nbIter()+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("\n"); // forcer le passage à la ligne
		  // fermer le fichier à la fin des traitements

		
		
		
		
		DijkstraAlgorithm D61 = new DijkstraAlgorithm(D1_Path);
    	ShortestPathSolution PS61 = D61.run();
    	System.out.println("sol: "+PS61);
    	System.out.println("Nombre d'iteration : "+ D61.nbIter());
    	assertTrue(D61.IsValid());
		ffw.write("sol: "+PS61+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("Nombre d'iteration : "+ D61.nbIter()+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("\n"); // forcer le passage à la ligne
		  // fermer le fichier à la fin des traitements
		
    	DijkstraAlgorithm D62 = new DijkstraAlgorithm(D2_Path);
    	ShortestPathSolution PS62 = D62.run();
    	System.out.println("sol: "+PS62);
    	System.out.println("Nombre d'iteration : "+ D62.nbIter());
    	assertTrue(D62.IsValid());
		ffw.write("sol: "+PS62+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("Nombre d'iteration : "+ D62.nbIter()+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("\n"); // forcer le passage à la ligne
		  // fermer le fichier à la fin des traitements
		
		
    	DijkstraAlgorithm D63 = new DijkstraAlgorithm(D3_Path);
    	ShortestPathSolution PS63 = D63.run();
    	System.out.println("sol: "+PS63);
    	System.out.println("Nombre d'iteration : "+ D63.nbIter());
    	assertTrue(D63.IsValid());
		ffw.write("sol: "+PS63+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("Nombre d'iteration : "+ D63.nbIter()+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("\n"); // forcer le passage à la ligne
		  // fermer le fichier à la fin des traitements
		
    	DijkstraAlgorithm D64 = new DijkstraAlgorithm(D4_Path);
    	ShortestPathSolution PS64 = D64.run();
    	System.out.println("sol: "+PS64);
    	System.out.println("Nombre d'iteration : "+ D64.nbIter());
    	assertTrue(D64.IsValid());
		ffw.write("sol: "+PS64+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("Nombre d'iteration : "+ D64.nbIter()+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("\n"); // forcer le passage à la ligne
		  // fermer le fichier à la fin des traitements
		
    	DijkstraAlgorithm D65 = new DijkstraAlgorithm(D5_Path);
    	ShortestPathSolution PS65 = D65.run();
    	System.out.println("sol: "+PS65);
    	System.out.println("Nombre d'iteration : "+ D65.nbIter());
    	assertTrue(D65.IsValid());
		ffw.write("sol: "+D65+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("Nombre d'iteration : "+ D65.nbIter()+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("\n"); // forcer le passage à la ligne
		  // fermer le fichier à la fin des traitements
		
    	DijkstraAlgorithm D66 = new DijkstraAlgorithm(D8_Path);
    	ShortestPathSolution PS66 = D66.run();
    	System.out.println("sol: "+PS66);
    	System.out.println("Nombre d'iteration : "+ D66.nbIter());
    	assertTrue(D66.IsValid());
		ffw.write("sol: "+PS66+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("Nombre d'iteration : "+ D66.nbIter()+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("\n"); // forcer le passage à la ligne
		ffw.close();  // fermer le fichier à la fin des traitements
    }
}
