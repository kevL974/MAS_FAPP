package com.mas.application;

import java.util.List;

import javax.swing.JFileChooser;

import sun.awt.shell.ShellFolder;

import com.mas.entite.Agent;
import com.mas.parser.Ecrivain;
import com.mas.parser.Lecteur;
import com.mas.tools.DfsTree;

public class Main {

	public static void main(String[] args) {
		try {
			//List<Integer[]> domaines = Lecteur.lectureDomaines();
			//List<Agent> antennes = Lecteur.lectureVariables();

			//Lecteur.lectureContaintes(antennes);
			
			//new DfsTree(antennes);
			//affichageListes(domaines,antennes);
			
			System.out.println(System.getProperty("user.dir"));
			System.out.println(System.getProperty("user.home"));
			System.out.println(ShellFolder.get("fileChooserDefaultFolder").toString());
			System.out.println(System.getProperty("eclipse.launcher"));
			System.out.println(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
			//Ecrivain.ecritureJACAMO(domaines, antennes);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void affichageListes(List<Integer[]> domaines, List<Agent> antennes) {
		int i;
		Integer[] tmpDom;
		Agent tmpAnt;
		int taille = domaines.size();

		System.out.println("Domaines:");
		for (i = 0; i < taille; i++) {
			tmpDom = domaines.get(i);
			System.out.print("\tDomaine "+(i+1)+": ");
			
			for (Integer s : tmpDom) {
				System.out.print(s + " ");
			}
			
			System.out.print("\n");
		}
		
		System.out.println();
		System.out.println("Agents:");
		taille = antennes.size();

		for (i = 0; i < taille; i++) {
			tmpAnt = antennes.get(i);
			//System.out.println(tmpAnt.toStringArbre());
			System.out.println("\t" + tmpAnt.toStringAvecContraintes());
		}
	}
}
