User wants find the property <http://www.geonames.org/ontology#officialName> 
of ontology class <http://datos.bcn.cl/ontologies/bcn-geographics#Place> 
searching with text "official name" and Matter "Country"

Narrative:
In order to find the property gn:officialName of Matter Country
As a user 
I want to obtain all properties of bcngeo:Place that contains "name"

Scenario:
The ontology <http://datos.bcn.cl/ontologies/bcn-geographics/> is loaded

Given the ontology http://datos.bcn.cl/ontologies/bcn-geographics
When I ask for property official name and Matter http://datos.bcn.cl/ontologies/bcn-norms#Country
Then I should get property http://www.geonames.org/ontology#officialName 