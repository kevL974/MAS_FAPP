package com.mas.entite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Agent {
	private int id;
	private int idDom;
	private Map<Integer, Contrainte> contraintes;
	
	private Integer parent;
	private List<Integer> pseudoParents;
	
	public Agent(int id, int idDom) {
		super();
		this.id = id;
		this.idDom = idDom;
		
		this.contraintes = new HashMap<Integer, Contrainte>();
		
		this.parent = -1;
		this.pseudoParents = new ArrayList<Integer>();
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdDom() {
		return idDom;
	}
	public void setIdDom(int idDom) {
		this.idDom = idDom;
	}
	public Map<Integer, Contrainte> getContraintes() {
		return contraintes;
	}
	public void setContraintes(Map<Integer, Contrainte> contraintes) {
		this.contraintes = contraintes;
	}
	public Integer getParent() {
		return parent;
	}
	public void setParent(Integer parent) {
		this.parent = parent;
	}
	public List<Integer> getPseudoParents() {
		return pseudoParents;
	}
	public void setPseudoParents(List<Integer> pseudoParents) {
		this.pseudoParents = pseudoParents;
	}
	
	public void ajoutContrainte(int idAgent, Contrainte contrainte){
		this.contraintes.put(idAgent, contrainte);
	}
	
	public void ajoutPseudoParent(int idAgent) {
		this.pseudoParents.add(idAgent);
	}
	
	public String toString() {
		return "Agent "+id+" (Domaine " + idDom+")";
	}
	
	public String toStringAvecContraintes() {
		StringBuilder affichage = new StringBuilder(toString());
		affichage.append("\n");
		
		Object[] clefs = contraintes.keySet().toArray();
		
		for(Object i : clefs) {
			affichage.append("\t[");
			affichage.append(i);
			affichage.append(" @ ");
			affichage.append(contraintes.get(i));
			affichage.append("] ");
		}
		
		return affichage.toString();
	}
}
