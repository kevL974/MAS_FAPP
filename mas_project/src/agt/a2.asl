// Agent a2 in project mas_project

/* Initial beliefs and rules */


/* Initial goals */

!getFrequence.

/* Plans */

+!getFrequence : 	doUtilPhase & leaf <- .print("reçu doUtilephase").


+!getFrequence : 	doUtilPhase & fils(X) <- !sendDoUtilPhase(X);
					-doUtilPhase.
+!getFrequence : 	true <- .wait(7);
					!getFrequence.					

+!sendDoUtilPhaseAll : fils(L) <- !sendDoUtilPhase(L).
+!sendDoUtilPhase([T|Q]) : true <- .send(T,tell, doUtilPhase); !sendDoUtilPhase(Q).
+!sendDoUtilPhase([]) : true.


{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have a agent that always complies with its organization  
//{ include("$jacamoJar/templates/org-obedient.asl") }
