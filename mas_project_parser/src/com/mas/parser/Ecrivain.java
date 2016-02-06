package com.mas.parser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.mas.entite.Agent;
import com.mas.tools.Constantes;

public class Ecrivain {
	public static void ecritureJACAMO(List<Integer[]> domaines, List<Agent> antennes) {
		try {
			ecritureFic(Constantes.FIC_JCM, ficJCM(domaines, antennes));
			ecritureFic(Constantes.REPO_ASL+File.separator+"a1.asl", ficASL1(domaines, antennes));
			ecritureFic(Constantes.REPO_ASL+File.separator+"a2.asl", ficASL2(domaines, antennes));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void ecritureFic(String nomFic, String contenu) throws IOException{
		File fic = new File(nomFic);
		
		if(fic.isFile()) {
			fic.delete();
		}
		fic.createNewFile();
		FileWriter ecrivain = new FileWriter(fic);
		
		ecrivain.write(contenu);
		
		ecrivain.close();
	}
	
	private static String ficJCM(List<Integer[]> domaines, List<Agent> antennes) {
		int t;
		List<Integer> list;
		Integer[][] mat;
		Agent pere;
		StringBuilder jcm = new StringBuilder("mas mas_project {\n");
		
		for(Agent a : antennes) {
			pere = null;
			
			jcm.append("agent a");
			jcm.append(a.getId());
			if(a.getParent() == -2) {
				jcm.append(" : a1.asl {\n");
			} else {
				jcm.append(" : a2.asl {\n");
			}

			jcm.append("beliefs : id(\"a");
			jcm.append(a.getId());
			jcm.append("\")\n");

			if(a.getParent() == -2) {
				jcm.append("beliefs : racine\n");
			} else {
				jcm.append("beliefs : pere(\"a");
				jcm.append(a.getParent());
				jcm.append("\")\n");
				pere = antennes.get(a.getParent()-1);
			}
			
			list = a.getFils();
			t = list.size()-1;
			if(t >= 0) {
				jcm.append("beliefs : fils(");
				jcm.append(listeAntenne(list));
				jcm.append(")\n");
			} else {
				jcm.append("beliefs : leaf\n");
				if(pere != null) {
					mat = a.matriceCout(pere, domaines);
					jcm.append("beliefs : mat_cout([");
					jcm.append(tableau(mat[0]));
					jcm.append(", ");
					jcm.append(tableau(mat[1]));
					jcm.append(", ");
					jcm.append(tableau(mat[2]));
					jcm.append("])\n");
				}
			}
			
			list = a.getPseudoFils();
			t = list.size()-1;
			if(t >= 0) {
				jcm.append("beliefs : pseudoFils(");
				jcm.append(listeAntenne(list));
				jcm.append(")\n");
			}
			
			list = a.getPseudoParents();
			t = list.size()-1;
			if(t >= 0) {
				jcm.append("beliefs : pseudoPere(");
				jcm.append(listeAntenne(list));
				jcm.append(")\n");
			} 
			
			jcm.append("beliefs : domain(");
			jcm.append(tableau(domaines.get(a.getIdDom())));
			jcm.append(")\n");
			
			if(pere != null) {
				jcm.append("beliefs : domainPere(");
				jcm.append(tableau(domaines.get(pere.getIdDom())));
				jcm.append(")\n");
			}
			
			jcm.append("focus : region");
			jcm.append(a.getId());
			jcm.append(".evaluateur\n");
			
			jcm.append("}\n");
		}
		
		for(Agent a : antennes) {
			jcm.append("workspace region");
			jcm.append(a.getId());
			jcm.append("{\n");
			jcm.append("artifact evaluateur : action.Matrice(\"a");
			jcm.append(a.getId());
			jcm.append("\")\n");
			jcm.append("}\n");
		}
		
		jcm.append("}\n");
		
		return jcm.toString();
	}
	
	private static String ficASL1(List<Integer[]> domaines, List<Agent> antennes) {
		return "";
	}
	
	private static String ficASL2(List<Integer[]> domaines, List<Agent> antennes) {
		return "";
	}
	
	private static String listeAntenne(List<Integer> list) {
		int t = list.size()-1;
		StringBuilder aff = new StringBuilder("[");
		
		for(int i = 0; i < t; i++) {
			aff.append("\"a");
			aff.append(list.get(i));
			aff.append("\",");
		}
		aff.append("\"a");
		aff.append(list.get(t));
		
		aff.append("\"]");
		return aff.toString();
	}
	
	private static String tableau(Object[] tab) {
		int t = tab.length-1;
		StringBuilder aff = new StringBuilder("[");
		
		for(int i = 0; i < t; i++) {
			aff.append(tab[i]);
			aff.append(",");
		}
		aff.append(tab[t]);
		
		aff.append("]");
		return aff.toString();
	}
}
