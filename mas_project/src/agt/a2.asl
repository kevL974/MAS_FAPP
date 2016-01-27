// Agent a2 in project mas_project

/* Initial beliefs and rules */


validFreq(domain([T|Q])) :- T . 

/* Initial goals */

!getFrequence.

/* Plans */

+!getFrequence : 	doUtilPhase & leaf <- .print("reçu doUtilephase").
+!getFrequence : 	doUtilPhase & fils(X) <- !sendDoUtilPhase(X);
					-doUtilPhase.
+!getFrequence : 	true <- .wait(7);
					!getFrequence.					

+!sendDoUtilPhase(X) : 	fils(X) & fils(Y) & X \== Y <- .send(X, tell, doUtilPhase);
						!sendDoUtilPhase(Y).
+!sendDoUtilPhase(X) : fils(X)<- .send(X, tell, doUtilPhase).


{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have a agent that always complies with its organization  
//{ include("$jacamoJar/templates/org-obedient.asl") }
