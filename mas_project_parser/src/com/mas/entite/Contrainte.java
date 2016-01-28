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

	private int valeur;
	
	public Contrainte(String operateur, int valeur) {
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
	public int getValeur() {
		return valeur;
	}
	public void setValeur(int valeur) {
		this.valeur = valeur;
	}
	
	public String toString() {
		return operateur+ " " + valeur;
	}
}
