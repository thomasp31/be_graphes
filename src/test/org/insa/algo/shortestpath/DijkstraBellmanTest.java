package org.insa.algo.shortestpath;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import java.io.IOException;

import java.util.List;

import org.insa.algo.ArcInspector;
import org.insa.algo.ArcInspectorFactory;

import org.insa.graph.Graph;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;

import org.junit.Test;


public class DijkstraBellmanTest{
	
	@Test
	public void DijkstraTestOracleShortest() throws IOException {
		
		//***************Path Ordinateur Morvan map INSA*********************/
		//String mapName = "C:/Users/Katill�r/Documents/INSA/3ir/be_graphes/insa/europe/france/insa.mapgr";
		//String pathName = "C:/Users/Katill�r/Documents/INSA/3ir/be_graphes/insa/europe/france/path_fr31insa_rangueil_r2.path";
		
		//***************Path Ordinateur Thomas map INSA*********************/
	    //String pathName = "/home/thomasp/Documents/insa/be_graphes/europe/france/path_fr31insa_rangueil_r2.path";
	    //String mapName = "/home/thomasp/Documents/insa/be_graphes/europe/france/insa.mapgr";
	    
	    //***************Path Ordinateur Morvan map carre-dense*********************/
	    //String mapName = "C:/Users/Katill�r/Documents/INSA/3ir/be_graphes/insa/europe/france/extras/carre-dense.mapgr";
  
	    //***************Path Ordinateur Thomas map carre-dense*********************/
	    String mapName = "/home/thomasp/Documents/insa/be_graphes/europe/carre-dense.mapgr"; 

	    // Create a graph reader.
	    GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

	    // TODO: Read the graph.
	    Graph graph = reader.read();

	    //Mettre en commentaire pour certaine carte comme CARRE DENSE
	    // TODO: Create a PathReader.
	    //PathReader pathReader = new BinaryPathReader(new DataInputStream(new BufferedInputStream(new FileInputStream(pathName))));
	    
	    //Mettre en commentaire pour certaine carte comme CARRE DENSE
	    // TODO: Read the path.
	    //Path path = pathReader.readPath(graph);

        int idOrigin=1;
        int idDest=121;
        
        List<ArcInspector> L = ArcInspectorFactory.getAllFilters();

		
		ShortestPathData SP=new ShortestPathData(graph,graph.get(idOrigin),graph.get(idDest),L.get(0));
		DijkstraAlgorithm D=new DijkstraAlgorithm(SP);
    	ShortestPathSolution SPD = D.run();
    	BellmanFordAlgorithm B= new BellmanFordAlgorithm(SP);
    	ShortestPathSolution SPB= B.run();
    	System.out.println("sol: "+SPD);
    	System.out.println("sol: "+SPB);
    	System.out.println(" path longueur Dijkstra"+ SPD.getPath().getLength());
    	System.out.println(" path longueur BellmanFord"+ SPB.getPath().getLength());
    	
    	//renvoie exactement la meme valeur donc ok
    	//test réalisés sur plusieurs map
    	
    	assertEquals(SPD.getPath().getLength(), SPB.getPath().getLength(),0.001);
	}
	
	@Test
	public void DijkstraTestOracleFastest() throws IOException {
		
		//***************Path Ordinateur Morvan map INSA*********************/
		//String mapName = "C:/Users/Katill�r/Documents/INSA/3ir/be_graphes/insa/europe/france/insa.mapgr";
		//String pathName = "C:/Users/Katill�r/Documents/INSA/3ir/be_graphes/insa/europe/france/path_fr31insa_rangueil_r2.path";
		
		//***************Path Ordinateur Thomas map INSA*********************/
	    //String pathName = "/home/thomasp/Documents/insa/be_graphes/europe/france/path_fr31insa_rangueil_r2.path";
	    //String mapName = "/home/thomasp/Documents/insa/be_graphes/europe/france/insa.mapgr";
	    
	    //***************Path Ordinateur Morvan map carre-dense*********************/
	    //String mapName = "C:/Users/Katill�r/Documents/INSA/3ir/be_graphes/insa/europe/france/extras/carre-dense.mapgr";
  
	    //***************Path Ordinateur Thomas map carre-dense*********************/
	    String mapName = "/home/thomasp/Documents/insa/be_graphes/europe/carre-dense.mapgr"; 

	    // Create a graph reader.
	    GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

	    // TODO: Read the graph.
	    Graph graph = reader.read();

	    //Mettre en commentaire pour certaine carte comme CARRE DENSE
	    // TODO: Create a PathReader.
	    //PathReader pathReader = new BinaryPathReader(new DataInputStream(new BufferedInputStream(new FileInputStream(pathName))));

	    //Mettre en commentaire pour certaine carte comme CARRE DENSE
	    // TODO: Read the path.
	    //Path path = pathReader.readPath(graph);

        int idOrigin=1;
        int idDest=121;
        
        List<ArcInspector> L = ArcInspectorFactory.getAllFilters();

		
		ShortestPathData SP=new ShortestPathData(graph,graph.get(idOrigin),graph.get(idDest),L.get(2));
		DijkstraAlgorithm D=new DijkstraAlgorithm(SP);
    	ShortestPathSolution SPD = D.run();
    	BellmanFordAlgorithm B= new BellmanFordAlgorithm(SP);
    	ShortestPathSolution SPB= B.run();
    	System.out.println("sol: "+SPD);
    	System.out.println("sol: "+SPB);
    	System.out.println("Path longueur Dijkstra"+ SPD.getPath().getLength());
    	System.out.println("Path longueur BellmanFord"+ SPB.getPath().getLength());
    	
    	//renvoie exactement la meme valeur donc ok
    	//tests réalisés sur plusieurs map
    	
    	assertEquals(SPD.getPath().getLength(), SPB.getPath().getLength(),0.001);
	}
}
