<?xml version="1.0"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
        xmlns:xforms="http://www.w3.org/2002/xforms" xmlns:xhtml="http://www.w3.org/1999/xhtml"
        xmlns:ev="http://www.w3.org/2001/xml-events" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:jr="http://openrosa.org/javarosa">

    <xsl:output method="xml" indent="yes" />

    <xsl:template match="xhtml:html">
        <xhtml:html>
            
            <xsl:apply-templates select="xhtml:head"/>
            <xsl:apply-templates select="xhtml:body"/>

        </xhtml:html>
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
                                <jr:text id="one-section">
                                    <xsl:attribute name="id">
                                        <xsl:value-of select="local-name()" />
                                    </xsl:attribute>
                                    <jr:value><xsl:value-of select="label"/></jr:value>
                                </jr:text>
                            </xsl:for-each>
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
            </xforms:bind>
        </xsl:for-each>    
        
    </xsl:template>
    
    <xsl:template match="xhtml:body">

        <xhtml:body>
        
        <xsl:apply-templates select=".//xhtml:td"/>

        </xhtml:body>
    </xsl:template>
    
    <xsl:template match="xhtml:td">
        <xsl:for-each select="xforms:input">
            <xforms:input>
                <xsl:attribute name="bind"><xsl:value-of select="@bind" /></xsl:attribute>
                <xsl:attribute name="id"><xsl:value-of select="@id" /></xsl:attribute>            
                <xforms:label>
                    <xsl:variable name="resourceReference" select="substring-before(substring-after(xforms:label/@ref, '/'), '/')"/>
                    <xsl:attribute name="ref">jr:itext('<xsl:value-of select="$resourceReference"/>')</xsl:attribute>            
                </xforms:label>
            </xforms:input>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>
