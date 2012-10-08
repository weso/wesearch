User wants to find "Ministros nacidos en Chile"

Narrative:
In order to find "Ministros" that were born in "Chile" 
As a User of BCN
I want to incrementally find and select the necessary Matters (Ministros) and 
 properties ("nacido_en") and Value_selectors ("Location").
 
Scenario: 
An ontology is loaded with "Ministros" and property ("nacido_en"). The value selector for 
property "nacido_en" is "Location"

Given an ontology is loaded with Minitros and nacido_en
When I ask for value selector of nacido_en, Ministro and ""
Then I should get value selector Location  