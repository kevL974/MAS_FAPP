// Agent antenne1 in project mas_project

/* Initial beliefs and rules */
neighbors([antenna1, antenna2]).
domains([value_fq1, value_fq2]).
constraints(Neighbor1, [constraint1, constraint2]).
constraints(Neighbor2, [constraint1, constraint2]).
/* Initial goals */

!start.

/* Plans */

+!start : true <- .print("hello world.").

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have a agent that always complies with its organization  
//{ include("$jacamoJar/templates/org-obedient.asl") }
