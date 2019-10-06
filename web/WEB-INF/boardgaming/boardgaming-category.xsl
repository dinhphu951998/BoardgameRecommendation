<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : BoardGame.vn.Detail.xsl
    Created on : September 25, 2019, 1:36 PM
    Author     : PhuNDSE63159
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    
    <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
    <xsl:import href="boardgaming-game-detail.xsl"/>
    
    <xsl:template name="CrawlCategory">
        <xsl:param name="doc" select="'Default value for page'"/>
        <xsl:param name="host" select="'Default value for host'"/>
        
        <xsl:apply-templates select="$doc//div[@class='teasers']"/>
        
    </xsl:template>
    
    <xsl:template match="div[@class='teasers']">
        
        <xsl:for-each select="./div">
            <game>
                <xsl:variable name="link" select="div[@class='teaser-content']/h2/a/@href"/>
                <title>
                    <xsl:value-of select="div[@class='teaser-content']/h2/a/text()"/>
                </title>
                <link>
                    <xsl:value-of select="$link"/>
                </link>
                
                <thumbnail>
                    <xsl:value-of select="div[@class='post-image']//img/@src"/>
                </thumbnail>
                
                <category>
                    <xsl:apply-templates select=".//span[@class='cat-links']"/>
                </category>
            
                <xsl:variable name="doc" select="document($link)"/>
                <xsl:call-template name="CrawlGameDetail">
                    <xsl:with-param name="doc" select="$doc"/>
                </xsl:call-template>
            </game>
        </xsl:for-each>
        
    </xsl:template>
    
</xsl:stylesheet>
