User wants to find "Ministros nacidos en Chile"

Narrative:
In order to find "Ministros" that were born in "Chile" 
As a User 
I want to incrementally find and select the necessary Matters (Ministros) and 
 properties ("nacido_en") and Value_selectors ("textfield").
 
Scenario: 
An ontology is loaded with "Ministros" and property ("nacido_en"). The value selector for 
property "nacido_en" is "textfield"

Given the ontology src/test/resources/ontoTest2.owl
When I ask for value selector of http://datos.bcn.cl/ontologies/bcn-biographies#nacido_en and http://datos.bcn.cl/ontologies/bcn-biographies#Ministro
Then I should get value selector textfield  