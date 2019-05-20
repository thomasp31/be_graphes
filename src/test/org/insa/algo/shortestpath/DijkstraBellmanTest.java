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
import org.insa.graph.Path;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.BinaryPathReader;
import org.insa.graph.io.GraphReader;
import org.insa.graph.io.PathReader;
import org.junit.Test;

public class DijkstraBellmanTest{
	
	/*public boolean AssertEquals(double d1, double d2) {
		return d1==d2;
	}*/
	
	@Test
	public void DijkstraTestOracle() throws IOException {
		//String mapName = "C:/Users/Katill�r/Documents/INSA/3ir/be_graphes/insa/europe/france/insa.mapgr";
	    // faire un telechargement du path sur commetud
	    String pathName = "/home/thomasp/Documents/insa/be_graphes/europe/france/path_fr31insa_rangueil_r2.path";
	    String mapName = "/home/thomasp/Documents/insa/be_graphes/europe/france/insa.mapgr";
	    //String pathName = "C:/Users/Katill�r/Documents/INSA/3ir/be_graphes/insa/europe/france/path_fr31insa_rangueil_r2.path";

	    // Create a graph reader.
	    GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

	    // TODO: Read the graph.
	    Graph graph = reader.read();


	    // TODO: Create a PathReader.
	    PathReader pathReader = new BinaryPathReader(new DataInputStream(new BufferedInputStream(new FileInputStream(pathName))));

	    // TODO: Read the path.
	    Path path = pathReader.readPath(graph);

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
    	System.out.println(" path longueur "+ SPD.getPath().getLength());
    	//renvoie exactement la meme valeur donc ok
    	//Avis de la prof: areter les print c'est pas beau et peut engendrer des erreurs quand on aura beaucoup d'erreurs
    	//privilégier les assert
    	
    	
    	//******************************************************************************************//
    	
    	/*System.out.println("cout di: "+ D.getCout());
    	System.out.println("cout bi: "+ B.getCout());*/

    	//Pour un ShortestPathSolution SPS, faire SPS.getPath().getLength() directement
    	
    	//assertTrue(PSB.equals(PSD));
    	//assertEquals(D.getCout(), B.getCout(),0.001);//ne pas oublier la précision 
    	
    	assertEquals(SPD.getPath().getLength(), SPB.getPath().getLength(),0.001);
    	
	}

	
	


}
