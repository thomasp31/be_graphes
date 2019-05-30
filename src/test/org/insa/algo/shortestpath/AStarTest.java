package org.insa.algo.shortestpath;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import java.util.List;

import org.insa.algo.ArcInspector;
import org.insa.algo.ArcInspectorFactory;

import org.insa.graph.Graph;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.BinaryPathReader;
import org.insa.graph.io.GraphReader;
import org.insa.graph.io.PathReader;

import org.junit.Test;
import java.io.File;

public class AStarTest {

	@Test
	public void AStarTestPerformance() throws IOException {
		
		//***************Path Ordinateur Morvan map INSA*********************/
		//String mapName = "C:/Users/Katillï¿½r/Documents/INSA/3ir/be_graphes/insa/europe/france/insa.mapgr";
		//String pathName = "C:/Users/Katillï¿½r/Documents/INSA/3ir/be_graphes/insa/europe/france/path_fr31insa_rangueil_r2.path";
		
		//***************Path Ordinateur Morvan map BZH*********************/
		//String mapName = "C:/Users/Katillär/Documents/INSA/3ir/be_graphes/insa/europe/france/bretagne.mapgr";
		//String pathName = "C:/Users/Katillär/Documents/INSA/3ir/be_graphes/insa/europe/france/bretagne.path";
		
		//***************Path Ordinateur Morvan map Midi-pyrénées*********************/
		//String mapName = "C:/Users/Katillär/Documents/INSA/3ir/be_graphes/insa/europe/france/midi-pyrenees.mapgr";
		//String pathName = "C:/Users/Katillär/Documents/INSA/3ir/be_graphes/insa/europe/france/midi-pyrenees.path";
		
		//***************Path Ordinateur Morvan map Belgique*********************/
		String mapName = "C:/Users/Katillär/Documents/INSA/3ir/be_graphes/insa/europe/france/belgium.mapgr";
		String pathName = "C:/Users/Katillär/Documents/INSA/3ir/be_graphes/insa/europe/france/belgium.path";
		
		//***************Path Ordinateur Thomas map INSA*********************/
	    //String pathName = "/home/thomasp/Documents/insa/be_graphes/europe/france/path_fr31insa_rangueil_r2.path";
	    //String mapName = "/home/thomasp/Documents/insa/be_graphes/europe/france/insa.mapgr";
		
		//***************Path Ordinateur Thomas map midi-pyrennees*********************/
	    //String pathName = "/home/thomasp/Documents/insa/be_graphes/europe/france/midi-pyrenees.mapfg";
	    //String mapName = "/home/thomasp/Documents/insa/be_graphes/europe/france/midi-pyrenees.mapgr";
	    

	    // Create a graph reader.
	    GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

	    // TODO: Read the graph.
	    Graph graph = reader.read();


	    // TODO: Create a PathReader.
	    //PathReader pathReader = new BinaryPathReader(new DataInputStream(new BufferedInputStream(new FileInputStream(pathName))));

	    // TODO: Read the path.
	    //Path path = pathReader.readPath(graph);

	    int idOrigin=966454;
	    int idDest=8836;
	    
	    List<ArcInspector> L = ArcInspectorFactory.getAllFilters();

		
		ShortestPathData SP=new ShortestPathData(graph,graph.get(idOrigin),graph.get(idDest),L.get(2));
		ShortestPathData SPAS=new ShortestPathData(graph,graph.get(idOrigin),graph.get(idDest),L.get(2));
		DijkstraAlgorithm D=new DijkstraAlgorithm(SP);
		AStarAlgorithm AS = new AStarAlgorithm(SPAS);
		
		//Calcule du temps d'execution pour Dikstra
		long debD=System.currentTimeMillis();
		ShortestPathSolution SPD = D.run();
		long finD=System.currentTimeMillis();
		long diffD=finD-debD;
		
		System.out.println("temps d'éxecution de Dijkstra:"+diffD);
		
		//Calcule du temps d'execution pour AStar
		long debA=System.currentTimeMillis();
		ShortestPathSolution SPASS= AS.run();
		long finA=System.currentTimeMillis();
		long diffA=finA-debA;
		
		//Affichage des temps d'execution par curiosite puisque assertTrue() le vÃ©rifie
		System.out.println("temps d'éxecution de Astar:"+diffA);
		System.out.println("sol: "+SPD);
		System.out.println("sol: "+SPASS);
		
		File ff=new File("C:/Users/Katillär/Documents/INSA/3ir/be_graphes/resultat.txt"); // définir l'arborescence
		ff.createNewFile();
		FileWriter ffw=new FileWriter(ff,true);
		
		ffw.write("temps d'éxecution de Dijkstra:"+diffD+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("temps d'éxecution de Astar:"+diffA+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("sol: "+SPD+"\n");  // écrire une ligne dans le fichier resultat.txt
		ffw.write("sol: "+SPASS+"\n");  // écrire une ligne dans le fichier resultat.txt

		ffw.write("\n"); // forcer le passage à la ligne
		ffw.close(); // fermer le fichier à la fin des traitements
		//renvoie exactement la meme valeur donc ok
		//tests rÃ©alisÃ©s sur plusieurs map
		
		assertTrue(diffA<diffD);
		assertEquals(SPD.getPath().getLength(), SPASS.getPath().getLength(),0.001);
		





	}

}
