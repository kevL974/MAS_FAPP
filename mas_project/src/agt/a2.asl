// Agent a2 in project mas_project

/* Initial beliefs and rules */
listSender([]).
//id(X):-.my_name(X).

//leaf :- not fils(Y).
receivedAllDomains :- listSender(L1) & fils(L2) & L1 = L2.

/* Initial goals */

!getFrequence.

/* Plans */


/*getFrequence------------------------------------------*/
+!getFrequence : 	true 
					<- ?utilPhase;
					!valuePhase.

+?utilPhase : 	doUtilPhase & leaf & pere(Pere) & domain(L) & id(Id)
				<- .print("reçu doUtilephase");
				!initDomain;
				.send(Pere, tell, domainFils(Id,L)).
				
+?utilPhase :   doUtilPhase & fils(X) 
				<- !initDomain;
				!sendDoUtilPhase(X);.print("entrer dans waitAllSons");
				?waitAllSonsDomains;.print("sorti dans waitAllSons");
				+doValuePhase.

-?utilPhase : 	true <- .wait(10);
				?utilPhase.				
+!valuePhase : true.


/*waitAllCostMatrix-------------------------------------*/
+?waitAllSonsDomains : 	receivedAllDomains
						<- .print("receivedAllDomain");afficherKnowledge.
						
+?waitAllSonsDomains :	listSender(List) & domainFils(Id,Domain) & fils(X)
						<- !addItemToList(Id,List, List1);.print("listSender");
						-listSender(List);
						+listSender(List1);
						!addAllDomain(Id,Domain);
						-domainFils(Id,Domain)[source(Id)];.print(List, List1, X);
						?waitAllSonsDomains.
						
-?waitAllSonsDomains : 	true <- .print("-?waitAllSonsDomain");.wait(1000);
						?waitAllSonsDomains.
					
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
+!addItemToList(Item, List, List1) : true <- List1 = [Item|List]; .print(List1).

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have a agent that always complies with its organization  
//{ include("$jacamoJar/templates/org-obedient.asl") }
