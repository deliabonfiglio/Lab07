package it.polito.tdp.dizionario.model;

import java.util.*;
import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.dizionario.db.WordDAO;

public class Model {
	private UndirectedGraph<String, DefaultEdge> grafo = null;
	private List<String> vertici = null;
	
	private WordDAO wdao = new WordDAO();
	private int dim=0;

	public List<String> createGraph(int numeroLettere) {
		grafo = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
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
		
		//System.out.format("\nNeighbours of %s: ", parolaInserita);
		//System.out.println(vicini+"\n");
		
		return vicini;
	}

	public String findMaxDegree() {
		/*
		 * per vedere se il grafo è stato creato posso definirlo =null 
		 * e poi controllo se è ancora ==null allora richiamo createGraph
		 * altrimenti cerco il grado massimo
		 * 
		 * la soluzione che ho scelto è di disattivare gli altri 2 bottoni 
		 * se non viene prima premuto createGraph
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

	public List<String> displayAllNeighbours(String parola){
		List<String> visited = new ArrayList<String>();
		
		BreadthFirstIterator<String, DefaultEdge> bvf= new BreadthFirstIterator<>(grafo, parola);
		
		while(bvf.hasNext()){			
			visited.add(bvf.next());
		}
		
		System.out.println(visited.toString());
		return visited;
	}

	public List<String> displayAllNeighboursIterative(String parola) {
		Set<String> davisitare= new HashSet<String>();//nei nodi da visitare ogni volta metto i vicini del nodo
		davisitare.add(parola);
		
		Set<String> visited = new HashSet<String>();
		
		while(!davisitare.isEmpty()){
			String s = davisitare.iterator().next();
			
			visited.add(s);		
			davisitare.remove(s);
			
			davisitare.addAll(Graphs.neighborListOf(grafo, s));
				
			davisitare.removeAll(visited);
	
		}

		return new ArrayList<String>(visited);
	}
	
}
