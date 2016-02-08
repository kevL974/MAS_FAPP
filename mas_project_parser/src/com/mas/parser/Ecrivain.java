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
			
			jcm.append("\t agent a");
			jcm.append(a.getId());
			if(a.getParent() == -2) {
				jcm.append(" : a1.asl {\n");
			} else {
				jcm.append(" : a2.asl {\n");
			}

			jcm.append("\t \t beliefs : id(\"a");
			jcm.append(a.getId());
			jcm.append("\")\n");

			if(a.getParent() == -2) {
				jcm.append("\t \t beliefs : racine\n");
			} else {
				jcm.append("\t \t beliefs : pere(\"a");
				jcm.append(a.getParent());
				jcm.append("\")\n");
				pere = antennes.get(a.getParent()-1);
			}
			
			list = a.getFils();
			t = list.size()-1;
			if(t >= 0) {
				jcm.append("\t \t beliefs : fils(");
				jcm.append(listeAntenne(list));
				jcm.append(")\n");
			} else {
				jcm.append("\t \t beliefs : leaf\n");
			}
			
			if(pere != null) {
				mat = a.matriceCout(pere, domaines);
				jcm.append("\t \t beliefs : mat_cout([");
				jcm.append(tableau(mat[0]));
				jcm.append(", ");
				jcm.append(tableau(mat[1]));
				jcm.append(", ");
				jcm.append(tableau(mat[2]));
				jcm.append("])\n");
			}
			
			list = a.getPseudoFils();
			t = list.size()-1;
			if(t >= 0) {
				jcm.append("\t \t beliefs : pseudoFils(");
				jcm.append(listeAntenne(list));
				jcm.append(")\n");
			}
			
			list = a.getPseudoParents();
			t = list.size()-1;
			if(t >= 0) {
				jcm.append("\t \t beliefs : pseudoPere(");
				jcm.append(listeAntenne(list));
				jcm.append(")\n");
			} 
			
			jcm.append("\t \t beliefs : domain(");
			jcm.append(tableau(domaines.get(a.getIdDom())));
			jcm.append(")\n");
			
			if(pere != null) {
				jcm.append("\t \t beliefs : domainPere(");
				jcm.append(tableau(domaines.get(pere.getIdDom())));
				jcm.append(")\n");
			}
			
			jcm.append("\t \t focus : region");
			jcm.append(a.getId());
			jcm.append(".evaluateur\n");
			
			jcm.append("\t }\n\n");
		}
		
		for(Agent a : antennes) {
			jcm.append("\t workspace region");
			jcm.append(a.getId());
			jcm.append("{\n");
			jcm.append("\t \t artifact evaluateur : action.Matrice(\"a");
			jcm.append(a.getId());
			jcm.append("\")\n");
			jcm.append("\t }\n\n");
		}
		
		jcm.append("\t asl-path: src/agt\n");
		jcm.append("\t \t src/agt/inc);\n");
		
		jcm.append("}\n\n");
		
		return jcm.toString();
	}
	
	private static String ficASL1(List<Integer[]> domaines, List<Agent> antennes) {
		StringBuilder asl = new StringBuilder("//Agent a1 in project mas_project\n\n");
		
		/* Initial beliefs and rules */


		/* Initial goals */
		asl.append("!dpop.\n\n");
		
		/* Plans */
		asl.append("+!dpop : fils([X|Q]) <- .send(X, tell, doUtilPhase).\n\n");

		asl.append("{ include(\"$jacamoJar/templates/common-cartago.asl\") }\n");
		asl.append("{ include(\"$jacamoJar/templates/common-moise.asl\") }\n");
		
		return asl.toString();
	}
	
	private static String ficASL2(List<Integer[]> domaines, List<Agent> antennes) {
		StringBuilder asl = new StringBuilder("//Agent a2 in project mas_project\n\n");
		
		/* Initial beliefs and rules */
		asl.append("listSender([]).\n\n\n");
		
		/* Initial goals */
		asl.append("!getFrequence.\n\n\n");
		
		/* Plans */
		/*getFrequence------------------------------------------*/
		asl.append("+!getFrequence : true\n");
			asl.append("\t <- ?utilPhase;\n");
			asl.append("\t !valuePhase.\n\n");

		asl.append("+?utilPhase : 	doUtilPhase & leaf & pere(Pere) & domain(L) & id(Id)\n");
			asl.append("\t <- .print(\"reçu doUtilephase\");\n");
			asl.append("\t !initDomain;\n");
			asl.append("\t .send(Pere, tell, domainFils(Id,L)).\n\n");
				
		asl.append("+?utilPhase :   doUtilPhase & fils(X)\n"); 
			asl.append("\t<- !initDomain;\n");
			asl.append("\t !sendDoUtilPhase(X);.print(\"entrer dans waitAllSons\");\n");
			asl.append("\t !waitAllSonsDomains;.print(\"sorti dans waitAllSons\");\n");
			asl.append("\t +doValuePhase.\n\n");

		asl.append("-?utilPhase : 	true <- .wait(10);\n");
			asl.append("\t ?utilPhase.\n\n");
		
		asl.append("+!valuePhase : true.\n\n");


		/*waitAllSonsDomains-------------------------------------*/	
		asl.append("+!waitAllSonsDomains : receivedAllDomains <- true.\n");
		asl.append("+!waitAllSonsDomains :		domainFils(Id,Domain) & listSender(Ls) & fils(Lf)\n");
			asl.append("\t <- for ( .member(Freq,Domain) ) {\n");
				asl.append("\t\t addDomain(Id, Freq);\n");
			asl.append("\t };\n");
			asl.append("\t .concat(Ls, [Id], Lc);\n");
			asl.append("\t .print(\"concatenation Ls et Id \", Ls, [Id], Lc);\n");
			asl.append("\t .difference(Lf, Lc, Lr);\n");
			asl.append("\t .print(\"difference entre Lf et Lc\", Lf, Lc, Lr);\n");
			asl.append("\t .abolish(domainFils(Id,Domain));\n");
			asl.append("\t .abolish(listSender(Ls));\n");
			asl.append("\t +listSender(Lc);\n");
			asl.append("\t if(.empty(Lr)) {\n");
				asl.append("\t\t .print(\"empty\");\n");
				asl.append("\t\t +receivedAllDomains;\n");
			asl.append("\t };!waitAllSonsDomains.\n\n");
						
						
		asl.append("+!waitAllSonsDomains : 	true <- .print(\"toto\");.wait(1000);\n");
			asl.append("\t !waitAllSonsDomains.\n\n");
					
		/*sendDomain--------------------------------------------*/
		asl.append("+!sendDomain(Id) : 	true\n");
			asl.append("\t <-.send(Id, tell, domain(X)).\n\n");

		/*sendDoUtilPhaseAll------------------------------------*/
		asl.append("+!sendDoUtilPhaseAll : fils(L) <- !sendDoUtilPhase(L).\n");
		asl.append("+!sendDoUtilPhase([T|Q]) : true <- .send(T,tell, doUtilPhase); !sendDoUtilPhase(Q).\n");
		asl.append("+!sendDoUtilPhase([]) : true.\n\n");


		/*initDomain-------------------------------------------*/
		asl.append("+!initDomain : 	id(X) & domain(L1) & pere(Pere) & domainPere(L2)\n");
			asl.append("\t <- !addAllDomain(X, L1);\n");
		asl.append("!addAllDomain(Pere,L2);\n");
			asl.append("\t afficherKnowledge.\n");

		/*addDomainALL---------------------------------------- */
		asl.append("+!addAllDomain(X, [T|Q]) :	true <- addDomain(X, T);\n");
		asl.append("!addAllDomain(X, Q).\n\n");
						
		asl.append("+!addAllDomain(X, []) :	true.\n\n");


		/*addItemToList----------------------------------------*/
		asl.append("+!addItemToList(Item, List, List1) : true <- List1 = [Item|List]; .print(List1).\n\n");

		/*isEqualsList------------------------------------------*/
		asl.append("+!isEqualsList(L1, L2, L3) :	true <- .difference(L1, L2, L3);.print(\"HOOOOOOOOOOOOOO\");.print(\"je suis ici\",L1, L2, L3);\n");
			asl.append("\t.empty(L3);\n");
			asl.append("\t+receivedAllDomains.\n");
							
		asl.append("{ include(\"$jacamoJar/templates/common-cartago.asl\") }\n");
		asl.append("{ include(\"$jacamoJar/templates/common-moise.asl\") }\n");
		
		return asl.toString();
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
