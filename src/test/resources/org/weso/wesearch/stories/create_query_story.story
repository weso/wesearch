User wants to find "Ministros nacidos en Chile"

Narrative:
In order to find "Ministros" that were born in "Chile" 
As a User of BCN
I want to incrementally find and select the necessary Matters (Ministros) and 
 properties ("nacido_en") and Value_selectors ("textfield", "numeric", "date", 
 "object" and "undefined").
 
Scenario:
A user has selected Matter "Ministro", Property "nacido_en" and "Value selector" Chile

Given a matter http://datos.bcn.cl/ontologies/bcn-biographies#Ministro, property http://datos.bcn.cl/ontologies/bcn-biographies#nacido_en and value selector textfield with value Chile
When I ask for a query
Then I get query SELECT DISTINCT ?res WHERE { ?res <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?a . ?res <http://datos.bcn.cl/ontologies/bcn-biographies#nacido_en> ?b . FILTER( regex(?b, "Chile", "i") ) .FILTER( ?a = <http://datos.bcn.cl/ontologies/bcn-biographies#Ministro> || ?a = <http://www.w3.org/2002/07/owl#Nothing>  ) .}