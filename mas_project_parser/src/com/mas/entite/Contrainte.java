package com.mas.entite;


/**
 * Classe décrivant une contrainte.
 * 
 * @author Kevin
 *
 */
public class Contrainte {
	// Type de contrainte.
	private String operateur;
	// Valeur laissée en Sring car non utilisée dans le java
	private String valeur;
	
	public Contrainte(String operateur, String valeur) {
		super();
		this.operateur = operateur;
		this.valeur = valeur;
	}
	
	public String getOperateur() {
		return operateur;
	}
	public void setOperateur(String operateur) {
		this.operateur = operateur;
	}
	public String getValeur() {
		return valeur;
	}
	public void setValeur(String valeur) {
		this.valeur = valeur;
	}
	
	public String toString() {
		return operateur+ " " + valeur;
	}
}
