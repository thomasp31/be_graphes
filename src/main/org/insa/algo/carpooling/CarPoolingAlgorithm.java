package org.insa.algo.carpooling;

import org.insa.algo.AbstractAlgorithm;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.shortestpath.AStarAlgorithm;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.algo.shortestpath.ShortestPathSolution;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.Path;

public abstract class CarPoolingAlgorithm extends AbstractAlgorithm<CarPoolingObserver> {

    protected CarPoolingAlgorithm(CarPoolingData data) {
        super(data);
    }

    @Override
    public CarPoolingSolution run() {
        return (CarPoolingSolution) super.run();
    }

    @Override
    protected  CarPoolingSolution doRun() {
		System.out.println("coucou");

	    CarPoolingData data = getInputData();
	    Graph graph = data.getGraph();
	    CarPoolingSolution solution=null;
	    //Calcul du chemin le plus court entre U1 et U2
		ShortestPathData U1U2=new ShortestPathData(graph,graph.get(data.getOriginU1().getId()),graph.get(data.getOriginU2().getId()),data.getArcInspector());
		AStarAlgorithm AU1U2 = new AStarAlgorithm(U1U2);
		ShortestPathSolution solutionU1U2 = AU1U2.run();

		//Calcul du chemin le plus court entre U1 et D1 et U2 et D1
		ShortestPathData U1D1=new ShortestPathData(graph,graph.get(data.getOriginU1().getId()),graph.get(data.getDestinationD1().getId()),data.getArcInspector());
		AStarAlgorithm AU1D1 = new AStarAlgorithm(U1D1);
		ShortestPathData U2D1=new ShortestPathData(graph,graph.get(data.getOriginU2().getId()),graph.get(data.getDestinationD1().getId()),data.getArcInspector());
		AStarAlgorithm AU2D1 = new AStarAlgorithm(U2D1);

		//on compare si le chemin entre U1 et U2 est plus long que le chemin dans le cas où il ne font pas de bla-bla
		ShortestPathSolution solutionU1D1 = AU1D1.run();
		ShortestPathSolution solutionU2D1 = AU2D1.run();

		if(solutionU1U2.getPath().getLength()>(solutionU1D1.getPath().getLength()+solutionU2D1.getPath().getLength())){
			solution= new CarPoolingSolution(data, Status.OPTIMAL, solutionU1D1.getPath(), solutionU2D1.getPath());
			System.out.println("coucou");
		}
		
		//on trouve la médiane puis on fait aller U1 et U2 jusqu'au centre de gravité
		else {
			System.out.println("j'ai faim");

			//on trouve le milieu entre U1 et U2
			float cout=0;
			Node Intersection;
			int i=0;
			while(i<solutionU1U2.getPath().getArcs().size() && cout<(solutionU1U2.getPath().getArcs().get(i).getLength()/2) ) {
				cout = cout + solutionU1U2.getPath().getArcs().get(i).getLength();
			}
			Intersection = solutionU1U2.getPath().getArcs().get(i).getOrigin();
			
			//Calcul du chemin le plus court entre Intersection et D1 (=médiane du triangle U1 D1 U2)
			ShortestPathData IntersectionD1=new ShortestPathData(graph,Intersection,graph.get(data.getDestinationD1().getId()),data.getArcInspector());
			AStarAlgorithm AIntersectionD1 = new AStarAlgorithm(IntersectionD1);
			ShortestPathSolution solutionIntersectionD1 = AIntersectionD1.run();

			//trouve le noeud représentant le centre de gravité (situé à 1/3 de la médiane en partant de Intersection)
			cout=0;
			Node centreGravite;
			i=0;
			while(i<solutionIntersectionD1.getPath().getArcs().size() && cout<(solutionIntersectionD1.getPath().getArcs().get(i).getLength()/3) ) {
				cout = cout + solutionIntersectionD1.getPath().getArcs().get(i).getLength();
			}
			centreGravite = solutionIntersectionD1.getPath().getArcs().get(i).getOrigin();
			
			//Calcul du chemin le plus court entre U1 - centreGravite et U2 - centreGravite
			ShortestPathData U1G=new ShortestPathData(graph,graph.get(data.getOriginU1().getId()),centreGravite,data.getArcInspector());
			AStarAlgorithm AU1G = new AStarAlgorithm(U1G);
			ShortestPathSolution solutionU1G = AU1G.run();

			ShortestPathData U2G=new ShortestPathData(graph,graph.get(data.getOriginU2().getId()),centreGravite,data.getArcInspector());
			AStarAlgorithm AU2G = new AStarAlgorithm(U2G);
			ShortestPathSolution solutionU2G = AU2G.run();

			solution= new CarPoolingSolution(data, Status.OPTIMAL, solutionU1G.getPath(), solutionU2G.getPath());

		}
		return solution;

    }

    @Override
    public CarPoolingData getInputData() {
        return (CarPoolingData) super.getInputData();
    }

}
