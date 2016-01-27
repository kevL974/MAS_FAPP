package com.mas.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mas.entite.Agent;
import com.mas.entite.Contrainte;
import com.mas.tools.Constantes;

public class Lecteur {
	public static List<String[]> lectureDomaines() throws Exception {
		// On laisse les valeurs des domaines en String puisqu'elles ne seront
		// pas utilisées dans le java.
		ArrayList<String[]> domaines;
		BufferedReader lecteur;
		String ligne;
		String[] champs;
		int idDom;

		File ficDom = new File(Constantes.FIC_DOM);
		if (ficDom.isFile()) {
			domaines = new ArrayList<String[]>();
			lecteur = new BufferedReader(new FileReader(ficDom));

			while ((ligne = lecteur.readLine()) != null) {
				champs = ligne.trim().split("\\s+");
				
				idDom = Integer.parseInt(champs[0]);
				
				domaines.add(idDom, Arrays.copyOfRange(champs, 1, champs.length));
			}

			lecteur.close();

			return domaines;
		}
		return null;
	}

	public static List<Agent> lectureVariables() throws Exception {
		ArrayList<Agent> antennes;
		BufferedReader lecteur;
		String ligne;
		String[] champs;
		int id;

		File ficVar = new File(Constantes.FIC_VAR);
		if (ficVar.isFile()) {
			antennes = new ArrayList<Agent>();
			lecteur = new BufferedReader(new FileReader(ficVar));

			while ((ligne = lecteur.readLine()) != null) {
				champs = ligne.trim().split("\\s+");
				id = Integer.parseInt(champs[0]);

				antennes.add(new Agent(id, Integer.parseInt(champs[1])));
			}

			lecteur.close();

			return antennes;
		}
		return null;
	}
	
	public static void lectureContaintes(List<Agent> antennes) throws Exception {
		BufferedReader lecteur;
		Agent ant1;
		Agent ant2;
		int id1;
		int id2;
		Contrainte cont;
		String ligne;
		String[] champs;

		File ficCtr = new File(Constantes.FIC_CTR);
		if (ficCtr.isFile() && antennes != null && !antennes.isEmpty()) {
			lecteur = new BufferedReader(new FileReader(ficCtr));

			while ((ligne = lecteur.readLine()) != null) {
				champs = ligne.trim().split("\\s+");
				id1 = Integer.parseInt(champs[0]);
				id2 = Integer.parseInt(champs[1]);
				
				ant1 = antennes.get(id1-1);
				ant2 = antennes.get(id2-1);
				
				cont = new Contrainte(champs[3], champs[4]);
				
				ant1.ajoutContrainte(id2, cont);
				ant2.ajoutContrainte(id1, cont);
				
				antennes.set(id1-1, ant1);
				antennes.set(id2-1, ant2);
			}

			lecteur.close();
		}
	}	
}
