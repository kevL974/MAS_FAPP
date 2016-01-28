// Agent sample_agent in project mas_project

/* Initial beliefs and rules */


/* Initial goals */

!dpop.

/* Plans */

+!dpop : fils([X|Q]) <- .send(X, tell, doUtilPhase).

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have a agent that always complies with its organization  
//{ include("$jacamoJar/templates/org-obedient.asl") }
