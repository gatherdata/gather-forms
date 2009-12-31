<?xml version="1.0"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
        xmlns:xforms="http://www.w3.org/2002/xforms" xmlns:xhtml="http://www.w3.org/1999/xhtml"
        xmlns:ev="http://www.w3.org/2001/xml-events" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:jr="http://openrosa.org/javarosa">

    <xsl:output method="xml" indent="yes" />


    <xsl:key name="kItem" match="item" use="value"/>
    
    <xsl:template match="xhtml:html">
        <xhtml:html>

            <xsl:apply-templates select="xhtml:head"/>
            <xsl:apply-templates select="xhtml:body"/>

        </xhtml:html>
    </xsl:template>

    <xsl:template match="item">
        <!-- count(. | key('kItem', REL_ID)[1]) = 1 -->
        <!-- generate-id() = generate-id(key('kItem', REL-id)[1]) -->
        <xsl:if test="count(. | key('kItem', value)[1]) = 1">
            <jr:text>
                <xsl:attribute name="id"><xsl:value-of select="value" /></xsl:attribute>
                <jr:value><xsl:value-of select="label"/></jr:value>
            </jr:text>
    	</xsl:if>
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
    
        <xsl:for-each select=".//xforms:bind">
            <xforms:bind>
                <xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
                <xsl:attribute name="nodeset"><xsl:value-of select="../@nodeset"/>/<xsl:value-of select="@nodeset"/></xsl:attribute>
                <xsl:attribute name="name"><xsl:value-of select="@name"/></xsl:attribute>
                <xsl:if test="@type "><xsl:attribute name="type"><xsl:value-of select="@type"/></xsl:attribute></xsl:if>
                <xsl:if test="@required "><xsl:attribute name="required"><xsl:value-of select="@required"/></xsl:attribute></xsl:if>
                <xsl:if test="@relevant "><xsl:attribute name="relevant"><xsl:value-of select="@relevant"/></xsl:attribute></xsl:if>
            </xforms:bind>
        </xsl:for-each>    
        
    </xsl:template>
    
    <xsl:template match="xhtml:body">

        <xhtml:body>
        
        <xsl:apply-templates select=".//xhtml:td/*"/>

        </xhtml:body>
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
                       
            <xforms:label>
                <xsl:variable name="resourceReference" select="substring-before(substring-after(xforms:label/@ref, '/'), '/')"/>
                <xsl:attribute name="ref">jr:itext('<xsl:value-of select="$resourceReference"/>')</xsl:attribute>            
            </xforms:label>
            <xsl:variable name="itemsetReference" select="substring-before(substring-after(xforms:itemset/@nodeset, '/'), '/')"/>
            <xsl:for-each select="/xhtml:html/xhtml:head/xforms:model/xforms:instance[@id='fr-form-resources']//*[local-name() = $itemsetReference]/item">
                <item>
                    <xsl:attribute name="id"><xsl:value-of select="./value"/></xsl:attribute>        
                    <xforms:label><xsl:attribute name="ref">jr:itext('<xsl:value-of select="./value"/>')</xsl:attribute></xforms:label>
                    <xforms:value><xsl:value-of select="./value"/></xforms:value>
                </item>
            </xsl:for-each>
        </xforms:select1>

    </xsl:template>
    
</xsl:stylesheet>
