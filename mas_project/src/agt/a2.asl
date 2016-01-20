// Agent a2 in project mas_project

/* Initial beliefs and rules */

voisin(1, 80).

/* Initial goals */

!start.

/* Plans */

+!start : true <- .print("hello world.").

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have a agent that always complies with its organization  
//{ include("$jacamoJar/templates/org-obedient.asl") }
