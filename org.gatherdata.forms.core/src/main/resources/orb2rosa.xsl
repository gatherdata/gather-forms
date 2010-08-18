<?xml version="1.0"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
        xmlns:xforms="http://www.w3.org/2002/xforms" xmlns:xhtml="http://www.w3.org/1999/xhtml"
        xmlns:ev="http://www.w3.org/2001/xml-events" xmlns:xsd="http://www.w3.org/2001/XMLSchema" 
        xmlns:jr="http://openrosa.org/javarosa"
        xmlns:xxforms="http://orbeon.org/oxf/xml/xforms"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:fr="http://orbeon.org/oxf/xml/form-runner"

        >

    <xsl:output method="xml" indent="yes" />

    <xsl:key name="kItem" match="item" use="label"/>
    
    <xsl:template match="xhtml:html">
        <xhtml:html>

            <xsl:apply-templates select="xhtml:head"/>
            <xsl:apply-templates select="xhtml:body"/>

        </xhtml:html>
    </xsl:template>

    <xsl:template match="item">
        <!-- count(. | key('kItem', REL_ID)[1]) = 1 -->
        <!-- generate-id() = generate-id(key('kItem', REL-id)[1]) -->
        <!-- xsl:if test="count(. | key('kItem', label)[1]) = 1" -->
            <jr:text>
                <xsl:attribute name="id"><xsl:value-of select="local-name(..)"/>_<xsl:value-of select="value" /></xsl:attribute>
                <jr:value><xsl:value-of select="label"/></jr:value>
            </jr:text>
    	<!-- /xsl:if -->
    </xsl:template>
	
    <xsl:template match="xhtml:head">
        <xhtml:head>
            <xhtml:title>
                <xsl:value-of select="xhtml:title" />
            </xhtml:title>
    
            <xforms:model>
    
                <xforms:instance>
                    <xsl:copy-of select="xforms:model/xforms:instance[@id='fr-form-instance']/form" />
                </xforms:instance>
                
                <xsl:apply-templates select="xforms:model/xforms:bind[@id='fr-form-binds']/xforms:bind"/>
                
                <jr:itext>
                    <xsl:for-each
                        select="xforms:model/xforms:instance[@id='fr-form-resources']/resources/resource">
                        <jr:translation>
                            <xsl:attribute name="lang">
                                <xsl:value-of select="@xml:lang" />
                            </xsl:attribute>
                            <xsl:for-each select="./*">
                                <jr:text>
                                    <xsl:attribute name="id"><xsl:value-of select="local-name()" /></xsl:attribute>
                                    <jr:value><xsl:value-of select="label"/></jr:value>
                                </jr:text>
                            </xsl:for-each>
                            
                            <xsl:apply-templates select=".//item"/>
                            
                        </jr:translation>
                    </xsl:for-each>
                </jr:itext>
    
            </xforms:model>
        </xhtml:head>

    </xsl:template>
    
    <xsl:template match="xforms:bind">

      <xforms:bind>
        <xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
        <xsl:attribute name="nodeset"><xsl:value-of select="@nodeset"/></xsl:attribute>
        <xsl:attribute name="name"><xsl:value-of select="@name"/></xsl:attribute>
        <xsl:if test="@relevant "><xsl:attribute name="relevant"><xsl:value-of select="@relevant"/></xsl:attribute></xsl:if>
      </xforms:bind>
    
        <xsl:for-each select=".//xforms:bind">
            <xforms:bind>
                <xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
                <xsl:attribute name="nodeset"><xsl:value-of select="../@nodeset"/>/<xsl:value-of select="@nodeset"/></xsl:attribute>
                <xsl:attribute name="name"><xsl:value-of select="@name"/></xsl:attribute>
                <xsl:if test="@type "><xsl:attribute name="type"><xsl:value-of select="@type"/></xsl:attribute></xsl:if>
                <xsl:if test="@required "><xsl:attribute name="required"><xsl:value-of select="@required"/></xsl:attribute></xsl:if>
                <xsl:if test="@relevant "><xsl:attribute name="relevant"><xsl:value-of select="@relevant"/></xsl:attribute></xsl:if>
                <xsl:if test="@readonly "><xsl:attribute name="readonly"><xsl:value-of select="@readonly"/></xsl:attribute></xsl:if>
                <xsl:if test="starts-with(@xxforms:default,'preload')">
                    <xsl:variable name="jrPreload" select="substring-after(@xxforms:default, '/')"/>
                    <xsl:variable name="preload" select="substring-before($jrPreload, '/')"/>
                    <xsl:variable name="preloadParams" select="substring-after($jrPreload, '/')"/>
                    <xsl:attribute name="jr:preload"><xsl:value-of select="$preload"/></xsl:attribute>
                    <xsl:attribute name="jr:preloadParams">
                        <xsl:choose>
                            <xsl:when test="$preloadParams='this'"><xsl:value-of select="@name"/></xsl:when>
                            <xsl:when test="true()"><xsl:value-of select="$preloadParams"/></xsl:when>
                        </xsl:choose>
                    </xsl:attribute>
                </xsl:if>
            </xforms:bind>
        </xsl:for-each>    

    </xsl:template>
     
    <xsl:template match="xhtml:body">

        <xhtml:body>
        
        <xsl:apply-templates select=".//fr:section"/>

        </xhtml:body>
    </xsl:template>
    
    <xsl:template match="fr:section">

      <xforms:group>
        <!-- ABKDEBUG bind the group also? -->
        <xsl:attribute name="bind"><xsl:value-of select="@bind" /></xsl:attribute>

        <xsl:variable name="sectionReference" select="substring-before(@bind, '-bind')"/>

        <xforms:label>
          <xsl:attribute name="ref">jr:itext('<xsl:value-of select="$sectionReference"/>')</xsl:attribute>            
        </xforms:label>
        
        <xsl:choose>
          <xsl:when test="boolean(@repeat)">
            <xforms:repeat>
            <xsl:attribute name="nodeset">/form/<xsl:value-of select="$sectionReference"/></xsl:attribute>
            <xsl:apply-templates select=".//xhtml:td/*"/>
            </xforms:repeat>
          </xsl:when>
          <xsl:otherwise>
            <xsl:apply-templates select=".//xhtml:td/*"/>
          </xsl:otherwise>
        </xsl:choose>
        
      </xforms:group>
 
    </xsl:template>
    
    <xsl:template match="xhtml:td">
        <xsl:apply-templates select="./*" />
    </xsl:template>
    
    <xsl:template match="xforms:input">
        <xforms:input>
            <xsl:attribute name="bind"><xsl:value-of select="@bind" /></xsl:attribute>
            <xsl:attribute name="id"><xsl:value-of select="@id" /></xsl:attribute>            
            <xforms:label>
                <xsl:variable name="resourceReference" select="substring-before(substring-after(xforms:label/@ref, '/'), '/')"/>
                <xsl:attribute name="ref">jr:itext('<xsl:value-of select="$resourceReference"/>')</xsl:attribute>            
            </xforms:label>
        </xforms:input>
    </xsl:template>
    
    <xsl:template match="xforms:select1">
        <xforms:select1>
            <xsl:attribute name="bind"><xsl:value-of select="@bind" /></xsl:attribute>
            <xsl:attribute name="id"><xsl:value-of select="@id" /></xsl:attribute>            
                       
            <xsl:variable name="resourceReference" select="substring-before(substring-after(xforms:label/@ref, '/'), '/')"/>
            <xforms:label>
                <xsl:attribute name="ref">jr:itext('<xsl:value-of select="$resourceReference"/>')</xsl:attribute>            
            </xforms:label>
            <xsl:variable name="itemsetReference" select="substring-before(substring-after(xforms:itemset/@nodeset, '/'), '/')"/>
            <xsl:for-each select="/xhtml:html/xhtml:head/xforms:model/xforms:instance[@id='fr-form-resources']//*[local-name() = $itemsetReference]/item">
                <xforms:item>
                    <xsl:attribute name="id"><xsl:value-of select="./value"/></xsl:attribute>        
                    <xforms:label><xsl:attribute name="ref">jr:itext('<xsl:value-of select="$resourceReference"/>_<xsl:value-of select="./value"/>')</xsl:attribute></xforms:label>
                    <xforms:value><xsl:value-of select="./value"/></xforms:value>
                </xforms:item>
            </xsl:for-each>
        </xforms:select1>

    </xsl:template>
    
</xsl:stylesheet>
