package it.polito.tdp.yelp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	
	private YelpDao dao;
	private Graph<User, DefaultWeightedEdge> grafo;
	private Map<String, User> userIdMap;
	
	
	public Model() {
		this.dao = new YelpDao();
	}
	
	
	public void creaGrafo(int X, int anno) {
		
		this.grafo = new SimpleWeightedGraph<User, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		this.userIdMap = new HashMap<>();
		
		Graphs.addAllVertices(this.grafo, getVertici(X));
		
		for(User u : this.grafo.vertexSet()) {
			this.userIdMap.put(u.getUserId(), u);
		}
		
		aggiungiArchi(anno);
		
		
	}
	
	public String infoGrafo() {
		return "Grafo creato!\n#Vertici: "+this.grafo.vertexSet().size()+"\n#Archi: "+this.grafo.edgeSet().size();
	}
	
	
	public List<User> getVertici(int X){
		return this.dao.getVertici(X);
	}
	
	public void aggiungiArchi(int anno){

		List<User> vertici = new LinkedList<>(this.grafo.vertexSet());

		for(User u1 : vertici) {
			for(User u2 : vertici){
				
				if(!u1.equals(u2) && u1.getUserId().compareTo(u2.getUserId())<0) {
					
					Integer sim = this.dao.getSim(u1.getUserId(), u2.getUserId(), anno);
					
					if(sim > 0) {
						Graphs.addEdgeWithVertices(this.grafo, u1, u2, sim);
					}
				}
			}
		}
	}
	
	public List<UserSimili> userSimili(User u){
		
		int max = 0;
		
		for(DefaultWeightedEdge e : this.grafo.edgesOf(u)) {
			if(this.grafo.getEdgeWeight(e)>max) {
				max = (int)this.grafo.getEdgeWeight(e);
			}
		}
		
		List<UserSimili> simili = new ArrayList<>();
		
		for(DefaultWeightedEdge e : this.grafo.edgesOf(u)) {
			if(this.grafo.getEdgeWeight(e) == max) {
				
				UserSimili u2 = new UserSimili(Graphs.getOppositeVertex(this.grafo, e, u), max);
				
				simili.add(u2);
			}
		}
		return simili;
	}

	public Graph<User, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}
	
}
