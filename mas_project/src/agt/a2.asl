// Agent a2 in project mas_project

/* Initial beliefs and rules */
listSender([]).


leaf :- not fils(X).
receivedAllDomains :- listSender(L) & fils(L).

/* Initial goals */

!getFrequence.

/* Plans */


/*getFrequence------------------------------------------*/
+!getFrequence : 	true 
					<- !utilPhase.//;
					//!valuePhase.

+!utilPhase : 	doUtilPhase & leaf & pere(Pere) & domain(L) & id(Id)
				<- .print("reçu doUtilephase");
				!initDomain;
				.send(X, tell, domainFils(Id,L)).
				
+!utilPhase :   doUtilPhase & fils(X) 
				<- !sendDoUtilPhase(X);
				!waitAllSonsDomains;
				!utilPhase.
+!utilPhase : true.
				
+!getFrequence : 	doUtilPhase & fils(X) <- !sendDoUtilPhase(X);
					-doUtilPhase.
+!getFrequence : 	true <- .wait(7);
					!getFrequence.	

/*waitAllCostMatrix-------------------------------------*/
+!waitAllSonsDomains : 	receivedAllDomains
						<- true.
+!waitAllSonsDomains :	listSender(List)
						<- ?domainFils(Id,Domain);
						!addItemToList(Id,List);
						!addAllDomain(Id,Domain);
						-domainFils(Id);
						!waitAllSonsDomains.
					
/*sendDomain--------------------------------------------*/
+!sendDomain(Id) : 	domain(X)
					<-.send(Id, tell, X).

/*sendDoUtilPhaseAll------------------------------------*/
+!sendDoUtilPhaseAll : fils(L) <- !sendDoUtilPhase(L).
+!sendDoUtilPhase([T|Q]) : true <- .send(T,tell, doUtilPhase); !sendDoUtilPhase(Q).
+!sendDoUtilPhase([]) : true.


/*initDomain-------------------------------------------*/
+!initDomain : 	id(X) & domain(L) <- !addDomainAll(X, L);
				afficherKnowledge.

/*addDomainALL---------------------------------------- */
+!addAllDomain(X, [T|Q]) :	true <- addDomain(X, T);
						!addAllDomain(X, Q).
						
+!addAllDomain(X, []) :	true.


/*addItemToList----------------------------------------*/
+!addItemToList(Item, List) : true <- List = [Item|List].

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have a agent that always complies with its organization  
//{ include("$jacamoJar/templates/org-obedient.asl") }
