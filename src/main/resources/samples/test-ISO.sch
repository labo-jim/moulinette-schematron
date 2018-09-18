<?xml version="1.0" encoding="UTF-8"?>
<schema
  xmlns="http://purl.oclc.org/dsdl/schematron"
  xmlns:sonar="http://jimetevenard.com/ns/sonar-chematron"
  queryBinding="xslt2">
  
  <extends href="five-items.sch"/>
  
  <pattern id="pattern-attributs-obligatoires">
    <rule context="/*" sonar:severity="minor" id="toto-attribute-rule">
      <sonar:description>
        
      </sonar:description>
      <assert test="@toto">
        Root element mus have a toto attribute
      </assert>
    </rule>
    <rule context="/counter/item" id="id-on-items">
      <assert test="@id">
        Items element mus have an id attribute
      </assert>
    </rule>
  </pattern>

   
</schema>