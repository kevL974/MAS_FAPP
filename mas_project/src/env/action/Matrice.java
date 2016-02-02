// CArtAgO artifact code for project mas_project

package action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cartago.*;



public class Matrice extends Artifact {
	private String name;
	private HashMap<String,List<Integer>> knowledge = new HashMap<String,List<Integer>>();
	
	void init(String name) {
		this.name =  name; 
	}
	
	@OPERATION
	void combinaison() {
		ObsProperty prop = getObsProperty("domain");
		Object[] tab = prop.getValues();
		if(tab != null) {
			for(Object i : tab) {
				System.out.println(i);
			}
		}
	}
	
	@OPERATION
	void addDomain(String name, int valeur) {
		List<Integer> domain = null;
		if(knowledge.containsKey(name)) {
			domain = knowledge.get(name);
			if(domain != null) {
				domain.add(valeur);
			}
		} else {
			domain = new ArrayList<Integer>();
			domain.add(valeur);
			knowledge.put(name, domain);
		}
	}
	

	@OPERATION 
	void afficherKnowledge(){
		System.out.println("_________________" + name + "_________________");
		for(String cle : knowledge.keySet()) {
			List<Integer> domain = knowledge.get(cle);
			System.out.print("[" + cle + "] => ");
			for(Integer valeur : domain){
				System.out.println(valeur);
			}
		}
	}
}

