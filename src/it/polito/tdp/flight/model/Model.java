package it.polito.tdp.flight.model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import it.polito.tdp.flight.db.FlightDAO;

public class Model {
	
	FlightDAO dao;
	Graph< Airport, DefaultWeightedEdge> grafo;
	Map<Integer, Airport> idMap;
	List<Airport> aereoporti;
	
	public Model() {
		dao= new  FlightDAO();
		idMap=new HashMap<>();
		aereoporti = dao.getAllAirports(this.idMap);
	}

	public void creaGrafo(int kilometriMax) {
		
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		//Aggiungo nodi
		Graphs.addAllVertices(grafo, aereoporti);
		
		//Aggiungo archi
		List<Tratte> tratte = dao.getTratte(kilometriMax);
		
		for (Tratte t : tratte) {
			Airport a1 = idMap.get(t.getId1());
			Airport a2 = idMap.get(t.getId2());
			double durata = (t.getKm())/800; //Durata in ore (Gli aerei viaggiano a 800km/h)
			
			if (!a1.equals(a2)) Graphs.addEdge(this.grafo, a1, a2, durata);
		}
	
		System.out.println("GRAFO CREATO");
		System.out.println("#NODI: "+this.grafo.vertexSet().size());
		System.out.println("#ARCHI: "+this.grafo.edgeSet().size()+"\n");
	}
	
	public boolean tuttiCollegati() {
		
		ConnectivityInspector<Airport, DefaultWeightedEdge> inspector = new ConnectivityInspector<>(this.grafo);
		
		//Rimuovo aereoporti non connessi
		List<Airport> removed = new ArrayList<>();
		for (Airport a : this.grafo.vertexSet()) {
			if (grafo.inDegreeOf(a)==0 && grafo.outDegreeOf(a)==0) removed.add(a);
		}
		this.grafo.removeAllVertices(removed);
		
		System.out.print("Componenti connesse: "+inspector.connectedSets().size()+"\n");
		if (inspector.connectedSets().size()==1) return true;
		else return false;
		
	}

}
