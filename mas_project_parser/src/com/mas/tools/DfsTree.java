package com.mas.tools;

import java.util.ArrayList;
import java.util.List;

import com.mas.entite.Agent;

public class DfsTree {
	List<Agent> antennes;
	List<Integer> token;
	
	/*
	 * 	1. X owns the token: adds its own ID and sends it in turn to each of its
			neighbors, which become children
		2. Y receives the token from X: it marks X as visited. First time Y receives the
			token then parent(Y) = X. Other IDs in token which are also neighbors(Y)
			are pseudoparent. If Y receives token from neighbor W to which it was
			never sent, W is pseudochild.
		3. When all neighbors(X) visited, X removes its ID from token and sends it to
			parent(X).
	 */
	
	public DfsTree(List<Agent> antennes) {
		this.antennes = antennes;
		
		this.token = new ArrayList<Integer>();
		
		constructionArbre();
	}
	
	private Agent choixRacine() {
		return antennes.get(0);
	}
	
	private void constructionArbre() {
		Agent racine = choixRacine();
	}
	
	private void passageToken() {
		
	}
}
