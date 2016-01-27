// Agent a2 in project mas_project

/* Initial beliefs and rules */


validFreq(domain([T|Q])) :- T . 

/* Initial goals */

!getFrequence.

/* Plans */

+!getFrequence : 	doUtilPhase & leaf <- .print("reçu doUtilephase").
+!getFrequence : 	doUtilPhase & fils(X) <- .send(X, tell, doUtilPhase);
					-doUtilPhase. 
+!getFrequence : 	true <- .wait(7);
					!getFrequence.


//+!utilPhase(Constraint) : constraint()

//+!consMatCout(domain)

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have a agent that always complies with its organization  
//{ include("$jacamoJar/templates/org-obedient.asl") }
