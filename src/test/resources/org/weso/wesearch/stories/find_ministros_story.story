User wants to find "Ministros nacidos en Chile"

Narrative:
In order to find "Ministros" that were born in "Chile" 
As a User of BCN
I want to incrementally find and select the necessary Matters (Ministros) and 
 properties ("nacido_en") and Value_selectors ("textfield").

Scenario: 
The ontology http://datos.bcn.cl/ontologies/bcn-biographies/bcn-biographies.rdf
is loaded in the system

Given the ontology http://datos.bcn.cl/ontologies/bcn-biographies/bcn-biographies.rdf
When I ask for matters with ""
Then I should get matter Ministro 
And I should get matter Diputado

When I ask for matters with Minist
Then I should get matter Ministro
And I should not get matter Diputado
