User wants find "Ministros en Chile"

Narrative:
In order to find all "Ministros" that were born in "Chile"
As a User of BCN
I want to obtain all entities of type "Ministro" that were born in Chile

Scenario:
An ontology is loaded with classes "Persona" and "Ministro", and one property of "Persona", 
it is "nacido_en". The ontology defines that "Ministro" is subClassOf "Persona". On the other hand, 
in data model there are instances that are typed as "Ministro" but not as "Person".

Given the ontology src/test/resources/ontoTest5.owl
When I ask for "Ministros nacidos en chile"
Then I should get all entities of type "Ministro" that were born in "Chile" although they are not typed as "Persona"