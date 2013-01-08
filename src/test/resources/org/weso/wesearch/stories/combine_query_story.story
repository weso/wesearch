User wants to find "Ministros nacidos en Chile"

Narrative:
In order to find "Ministros" that were born in "Chile"
As a User of BCN
I want to incrementally find and select the necessary Matters (Ministros, Country), 
 property ("bornPlace"), Value_selectors ("textfield", "numeric", "date", 
 "object" and "undefined") and Value ("Chile").
 
Scenario:
A user has selected Matter "Ministro", Property "bornPlace", Value selector "object"

Given a matter http://datos.bcn.cl/ontologies/bcn-biographies#Place, property http://datos.bcn.cl/ontologies/bcn-biographies#countryName, value selector textfield, value Chile and clauses query ?res <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?a and ?res <http://datos.bcn.cl/ontologies/bcn-biographies#bornPlace> ?b
When I ask for a combined query
Then I get combined query SELECT DISTINCT ?res WHERE { ?res <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?a . ?res <http://datos.bcn.cl/ontologies/bcn-biographies#bornPlace> ?b . ?b <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?c . ?b <http://datos.bcn.cl/ontologies/bcn-biographies#countryName> ?d . FILTER( regex(?d, "Chile", "i") ) .FILTER( ?c = <http://datos.bcn.cl/ontologies/bcn-biographies#Place>  ) .FILTER( ?a = <http://datos.bcn.cl/ontologies/bcn-biographies#Ministro> || ?a = <http://www.w3.org/2002/07/owl#Nothing>  ) .}