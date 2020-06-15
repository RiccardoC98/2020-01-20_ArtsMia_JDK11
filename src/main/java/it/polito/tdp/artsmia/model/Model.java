package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	ArtsmiaDAO dao = new ArtsmiaDAO();
	Graph<Artist, DefaultWeightedEdge> grafo;
	HashMap<Integer, Artist> idMap;
	List<Artist> percorso;
	
	public List<String> getRuoli() {
		return dao.getRuoli();
		
	}
	
	public void creaGrafo(String ruolo) {
		idMap = dao.listArtists();
		grafo = new SimpleWeightedGraph(DefaultWeightedEdge.class);
		List<Artist> vertici = dao.getVertici(ruolo, idMap);
		
		Graphs.addAllVertices(grafo, vertici);
		
		//now gli archi
		for (Arco a : dao.getArchi(ruolo, idMap) ) {
			Graphs.addEdge(grafo, a.getA1(), a.getA2(), a.getPeso());
		}
	}
	
	public String getInfo() {
		return "#vertici: " + grafo.vertexSet().size() + "\n#archi: " + grafo.edgeSet().size() + "\n";
	}
	
	public boolean grafoContiene(Integer id) {
		if (grafo.containsVertex( idMap.get(id) )) {
			return true;
		}
		return false;
	}
	
	public List<Arco> artistiConnessi(String ruolo) {
		List<Arco> result = dao.getArchi(ruolo, idMap);
		Collections.sort(result);
		
		return result;
	}
	
	public List<Artist> trovaPercorso(Integer id) {
		Artist partenza = idMap.get(id);
		percorso = new ArrayList<>();
		List<Artist> parziale = new ArrayList<>();
		parziale.add(partenza);
		
		ricorsione(parziale,-1);
		
		return percorso;
		
	}

	private void ricorsione(List<Artist> parziale, int peso) {
		if (parziale.size() > percorso.size()) {
			percorso = new ArrayList<>(parziale);
		}
		Artist last = parziale.get(parziale.size()-1);
		List<Artist> vicini = Graphs.neighborListOf(grafo, last);
		
		
		for (Artist a : vicini) {
			if (!parziale.contains(a) && peso == -1) {
				int lastWeight = (int) this.grafo.getEdgeWeight(this.grafo.getEdge(last, a));
				parziale.add(a);
				ricorsione(parziale, lastWeight);
				parziale.remove(a);
			} else {
				if (!parziale.contains(a) && this.grafo.getEdgeWeight( grafo.getEdge( last, a)) == peso) {
					parziale.add(a);
					ricorsione(parziale, peso);
					parziale.remove(a);
				}
			}
			
		}
		
	}
}
