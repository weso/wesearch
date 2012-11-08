User wants to find "Ministros nacidos en Chile en 1938"

Narrative:
In order to find "Ministros" that were born in "Chile" in 1938
As a User of BCN
I want to incrementally find and select the necessary Matters (Ministros), 
 properties ("nacido_en", "fecha_nacimiento"), Value_selectors ("Location", "Year") and Value ("Chile", "1938").
 
Scenario:
A user has selected Matter "Ministro", Property "nacido_en" and "Value selector" Chile

Given a matter Ministro, property fecha_nacimiento, value selector 1938 and query SELECT ?uri WHERE {?uri rdf:type ont:Ministro . ?uri ont:nacido_en "Chile" .}
When I ask for a combined query
Then I get query SELECT ?uri WHERE {?uri rdf:type ont:Ministro . ?uri ont:nacido_en "Chile" . 
?uri onto:fecha_nacimiento "1938" .}