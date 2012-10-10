User wants to find "Ministros nacidos en Chile"

Narrative:
In order to find "Ministros" that were born in "Chile" 
As a User of BCN
I want to incrementally find and select the necessary Matters (Ministros) and 
 properties ("nacido_en") and Value_selectors ("Location").
 
Scenario:
A user has selected Matter "Ministro", Property "nacido_en" and "Value selector" Chile

Given a matter Ministro, property nacido_en and value selector Chile
When I ask for a query
Then I get query SELECT ?uri WHERE {?uri rdf:type ont:Ministro . ?uri ont:nacido_en "Chile" .}