// Agent a2 in project mas_project

/* Initial beliefs and rules */
listSender([]).
//id(X):-.my_name(X).

//leaf :- not fils(Y).
receivedAllDomains :- listSender(L) & fils(L).

/* Initial goals */

!getFrequence.

/* Plans */


/*getFrequence------------------------------------------*/
+!getFrequence : 	true 
					<- !utilPhase;
					!valuePhase.

+!utilPhase : 	doUtilPhase & leaf & pere(Pere) & domain(L) & id(Id)
				<- .print("reçu doUtilephase");
				!initDomain;
				.send(Pere, tell, domainFils(Id,L)).
				
+!utilPhase :   doUtilPhase & fils(X) 
				<- !initDomain;
				!sendDoUtilPhase(X);
				!waitAllSonsDomains;
				true.
				
+!utilPhase : true <- !utilPhase.
+!valuePhase : true.


/*waitAllCostMatrix-------------------------------------*/
+!waitAllSonsDomains : 	receivedAllDomains
						<- afficherKnowledge; true.
+!waitAllSonsDomains :	listSender(List) & domainFils(Id,Domain)
						<- !addItemToList(Id,List, List1);
						-listSender(List);
						+listSender(List1);
						!addAllDomain(Id,Domain);
						-domainFils(Id,Domain);
						!waitAllSonsDomains.
+!waitAllSonsDomains : true <- !waitAllSonsDomains.
					
/*sendDomain--------------------------------------------*/
+!sendDomain(Id) : 	true
					<-.send(Id, tell, domain(X)).

/*sendDoUtilPhaseAll------------------------------------*/
+!sendDoUtilPhaseAll : fils(L) <- !sendDoUtilPhase(L).
+!sendDoUtilPhase([T|Q]) : true <- .send(T,tell, doUtilPhase); !sendDoUtilPhase(Q).
+!sendDoUtilPhase([]) : true.


/*initDomain-------------------------------------------*/
+!initDomain : 	id(X) & domain(L1) & pere(Pere) & domainPere(L2) 
				<- !addAllDomain(X, L1);
				!addAllDomain(Pere,L2);
				afficherKnowledge.

/*addDomainALL---------------------------------------- */
+!addAllDomain(X, [T|Q]) :	true <- addDomain(X, T);
						!addAllDomain(X, Q).
						
+!addAllDomain(X, []) :	true.


/*addItemToList----------------------------------------*/
+!addItemToList(Item, List, List1) : true <- .print(List);List1 = [Item|List]; .print(List1).

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have a agent that always complies with its organization  
//{ include("$jacamoJar/templates/org-obedient.asl") }
