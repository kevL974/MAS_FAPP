package com.mas.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.mas.entite.Agent;

/**
 * Classe permettant la construction d'un DFSTree depuis une liste d'Agent.
 */
public class DfsTree {
	List<Agent> antennes;
	List<Integer> visites;
	List<Integer> token;
	
	
	public DfsTree(List<Agent> antennes) {
		this.antennes = antennes;
		
		this.token = new ArrayList<Integer>();
		this.visites = new ArrayList<Integer>();
		
		constructionArbre();
	}
	
	private Integer choixRacine() {
		return 1;
	}
	
	
	private void constructionArbre() {
		Agent racine = antennes.get(choixRacine()-1);
		
		racine.setParent(-2);
		
		passageToken(racine.getId(), new Agent(-2,-2));
	}
	
	
	/*
	  	1. X owns the token: adds its own ID and sends it in turn to each of its
			neighbors, which become children
		2. Y receives the token from X: it marks X as visited. First time Y receives the
			token then parent(Y) = X. Other IDs in token which are also neighbors(Y)
			are pseudoparent. If Y receives token from neighbor W to which it was
			never sent, W is pseudochild.
		3. When all neighbors(X) visited, X removes its ID from token and sends it to
			parent(X).
	 */
	private void passageToken(Integer idAgent, Agent source) {
		Agent noeud = antennes.get(idAgent-1);
		Set<Integer> voisins = noeud.getContraintes().keySet();
		
		if(source.getId() != -2) {
			if(!visites.contains(source.getId()) 
					&& !noeud.contientVoisinVisite(source.getId())
					&& source.getId() != -2
					&& !source.contientVoisinVisite(noeud.getId())) {
				source.ajoutPseudoParent(noeud.getId());
				noeud.ajoutPseudoFils(source.getId());
			}
			
			visites.add(source.getId());
			
			if(!visites.contains(idAgent)) {
				if(noeud.getParent() == -1) {
					noeud.setParent(source.getId());
				}
				
				for(Integer i : token) {
					if(i != noeud.getParent() && voisins.contains(i) && !noeud.contientPseudoParent(i)) {
						noeud.ajoutPseudoParent(i);
						antennes.get(i-1).ajoutPseudoFils(noeud.getId());
					}
				}
			}
		}
			
		token.add(noeud.getId());
		
		for(Integer v : voisins) {
			if(!visites.contains(v) && v != noeud.getParent()) {
				noeud.ajoutFils(v);
				noeud.ajoutVoisinVisite(v);
					
				passageToken(v, noeud);
			}
		}
		
		token.remove((Integer) noeud.getId());
		if(noeud.getParent() >= 0) {
			passageToken(noeud.getParent(), noeud);
		}
	}
}
