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
    <xsl:import href="boardgamehub-game-detail.xsl"/>
    
    <xsl:template name="CrawlCategory">
        <xsl:param name="doc" select="'Default value for page'"/>
        <xsl:param name="host" select="'Default value for host'"/>
        
        <xsl:apply-templates select="$doc//div[@id='av-masonry-1']"/>
        
    </xsl:template>
    
    <xsl:template match="div[@id='av-masonry-1']">
        
        <xsl:for-each select=".//a">
            <game>
                
                <xsl:variable name="link" select="@href"/>
            
                <title>
                    <xsl:value-of select="@title"/>
                </title>
                <link>
                    <xsl:value-of select="$link"/>
                </link>
            
                <thumbnail>
                    <xsl:value-of select=".//img/@src"/>
                </thumbnail>
            
                <xsl:variable name="doc" select="document($link)"/>
                <xsl:call-template name="CrawlGameDetail">
                    <xsl:with-param name="doc" select="$doc"/>
                </xsl:call-template>
                
            </game>
        </xsl:for-each>
    </xsl:template>
    
</xsl:stylesheet>
