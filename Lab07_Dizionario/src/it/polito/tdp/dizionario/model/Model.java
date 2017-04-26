package it.polito.tdp.dizionario.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.dizionario.db.WordDAO;

public class Model {
	private UndirectedGraph<String, DefaultEdge> grafo = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
	private List<String> vertici = null;
	
	private WordDAO wdao = new WordDAO();
	private int dim=0;

	public List<String> createGraph(int numeroLettere) {
		dim = numeroLettere;
		vertici = new ArrayList<String>(wdao.getAllWordsFixedLength(dim));
		
		//creo i vertici
		for(String parola: vertici){
			grafo.addVertex(parola);
		}
		
		for(int i=0; i<vertici.size(); i++){
			for(int j=i+1; j<vertici.size(); j++){
				if(this.isDifferent(vertici.get(i),vertici.get(j) ))
					grafo.addEdge(vertici.get(i),vertici.get(j));
			}
		}
		
		System.out.println(grafo.toString());
		return vertici;
	}
	private boolean isDifferent(String parola1, String parola2){
		int cnt=0;		
		for(int i=0; i<dim; i++){
				if(parola1.charAt(i)!=parola2.charAt(i))
					cnt ++;				
			}		
		if(cnt==1)
			return true;
		
		return false;
	}

	public List<String> displayNeighbours(String parolaInserita) {
		List<String> vicini = new ArrayList<String>(Graphs.neighborListOf(grafo, parolaInserita));
		
		System.out.format("\nNeighbours of %s: ", parolaInserita);
		System.out.println(vicini);
		
		return vicini;
	}

	public String findMaxDegree() {
		/*
		 * per vedere se il grafo è stato creato posso definirlo =null 
		 * e poi controllo se è ancora ==null allora richiamo createGraph
		 * altrimenti cerco il grado massimo
		 * 
		 * la soluzione che ho scelto è di disattivare gli altri 2 bottoni se non viene prima premuto createGraph
		 */
		int maxDegree = grafo.degreeOf(vertici.get(0));
		String result="";
	
		for(String vertex: vertici){
				if(maxDegree < grafo.degreeOf(vertex)){
					maxDegree = grafo.degreeOf(vertex);
					result = vertex;
				}
			}
		result += "\ndi grado: "+maxDegree +"\nCon vicini: "+ this.displayNeighbours(result).toString();
				//grado, il vertice e la lista dei suoi diretti vicini
		
		System.out.println(result.toString());
		return result;
	}
}
