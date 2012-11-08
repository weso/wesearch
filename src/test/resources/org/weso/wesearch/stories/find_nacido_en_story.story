User wants to find "Ministros nacidos en Chile"

Narrative:
In order to find "Ministros" that were born in "Chile" 
As a User of BCN
I want to incrementally find and select the necessary Matters (Ministros) and 
 properties ("nacido_en") and Value_selectors ("Location").
 
Scenario: 
An ontology is loaded with "Ministros" and "Personas" and properties ("nacido_en", "representa_a") for "Ministros" 
and properties ("lugar_nacimiento", "fecha_nacimiento") for "Personas" 

Given an ontology is loaded with Ministros, nacido_en and representa_a
When I ask for property with "" and Matter Ministro
Then I should get property nacido_en
Then I should get property representa_a

Given an ontology is loaded with Ministros, nacido_en and representa_a
When I ask for property with "nacido" and Matter Ministro
Then I should get property nacido_en
Then I should not get property representa_a