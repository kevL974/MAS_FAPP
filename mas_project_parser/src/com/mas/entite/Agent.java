package com.mas.entite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mas.tools.Constantes;

public class Agent {
	private int id;
	private int idDom;
	private Map<Integer, Contrainte> contraintes;
	
	private Integer parent;
	private List<Integer> pseudoParents;
	
	private List<Integer> fils;
	private List<Integer> pseudoFils;
	
	private List<Integer> voisinsVisites;
	
	public Agent(int id, int idDom) {
		super();
		this.id = id;
		this.idDom = idDom;
		
		this.contraintes = new HashMap<Integer, Contrainte>();
		
		this.parent = -1;
		this.pseudoParents = new ArrayList<Integer>();
		this.fils = new ArrayList<Integer>();
		this.pseudoFils = new ArrayList<Integer>();
		
		this.voisinsVisites = new ArrayList<Integer>();
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
	
	public List<Integer> getFils() {
		return fils;
	}


	public void setFils(List<Integer> fils) {
		this.fils = fils;
	}


	public List<Integer> getPseudoFils() {
		return pseudoFils;
	}


	public void setPseudoFils(List<Integer> pseudoFils) {
		this.pseudoFils = pseudoFils;
	}
	
	public List<Integer> getVoisinsVisites() {
		return voisinsVisites;
	}


	public void setVoisinsVisites(List<Integer> voisinsVisites) {
		this.voisinsVisites = voisinsVisites;
	}


	public void ajoutContrainte(int idAgent, Contrainte contrainte){
		this.contraintes.put(idAgent, contrainte);
	}
	
	public void ajoutPseudoParent(int idAgent) {
		this.pseudoParents.add(idAgent);
	}
	
	public void ajoutFils(int idAgent) {
		this.fils.add(idAgent);
	}
	
	public void ajoutPseudoFils(int idAgent) {
		this.pseudoFils.add(idAgent);
	}
	
	public void ajoutVoisinVisite(int idAgent) {
		this.voisinsVisites.add(idAgent);
	}
	
	public boolean contientPseudoParent(int idAgent) {
		return this.pseudoParents.contains(idAgent);
	}
	
	public boolean contientFils(int idAgent) {
		return this.fils.contains(idAgent);
	}
	
	public boolean contientPseudoFils(int idAgent) {
		return this.pseudoFils.contains(idAgent);
	}
	
	public boolean contientVoisinVisite(int idAgent) {
		return this.voisinsVisites.contains(idAgent);
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
	
	public String toStringArbre() {
		StringBuilder affichage = new StringBuilder(toString());
		affichage.append("\n");
		
		affichage.append("\tParent: \n\t\t");
		affichage.append(this.parent);
		affichage.append("\n");
		
		
		affichage.append("\tPseudoParents: \n\t\t");
		
		for(Integer i : this.pseudoParents) {
			affichage.append("[");
			affichage.append(i);
			affichage.append("] ");
		}
		affichage.append("\n");
		
		
		affichage.append("\tEnfants: \n\t\t");
		
		for(Integer i : this.fils) {
			affichage.append("[");
			affichage.append(i);
			affichage.append("] ");
		}
		affichage.append("\n");
		
		
		affichage.append("\tPseudoEnfants: \n\t\t");
		
		for(Integer i : this.pseudoFils) {
			affichage.append("[");
			affichage.append(i);
			affichage.append("] ");
		}
		affichage.append("\n");
		
		return affichage.toString();
	}
	
	public Integer[][] matriceCout(Agent voisin, List<Integer[]> domaines) {
		if(this.contraintes.containsKey((Integer)voisin.getId())) {
			Integer[] domAgent = domaines.get(this.idDom);
			Integer[] domVoisin = domaines.get(voisin.getIdDom());
			int longMat = domAgent.length*domVoisin.length;
			int ia;
			int iv;
			int i;
			
			Contrainte cont = this.contraintes.get((Integer)voisin.getId());
			
	
			Integer[][] matrice = new Integer[3][longMat];
			
			for(ia = 0; ia < domAgent.length; ia++) {
				for(iv = 0; iv < domVoisin.length; iv++) {
					i = ia*domVoisin.length+iv;
					matrice[0][i] = domAgent[ia];
					matrice[0][i] = domVoisin[iv];
					
					matrice[0][i] = calculCout(domAgent[ia], domVoisin[iv], cont);
				}
			}
			
			return matrice;
		}
		return null;
	}


	private Integer calculCout(Integer freqAgent, Integer freqVoisin, Contrainte cont) {
		String op = cont.getOperateur();
		int val = cont.getValeur();
		int diff = freqAgent - freqVoisin;
		
		if("=".equals(op)) {
			if(diff == val) {
				return 0;
			} else {
				return Constantes.INFINI;
			}
		} else {
			if(diff > val) {
				return diff-val-1;
			} else {
				return Constantes.INFINI;
			}
		}
	}
}
