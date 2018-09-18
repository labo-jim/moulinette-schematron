<?xml version="1.0" encoding="UTF-8"?>
<schema
  xmlns="http://purl.oclc.org/dsdl/schematron"
  xmlns:sonar="http://jimetevenard.com/ns/sonar-chematron"
  queryBinding="xslt2">
  
 
  <pattern id="pattern-des-items">
    <rule context="counter" id="rule-plusieur-item">
      <sonar:description>Ceci est un exemple de description utilisée par sonar</sonar:description>
      <assert test="count(item) > 5">
        There must be at least five items in a counter
      </assert>
    </rule>
   
  </pattern>
</schema>