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
    <xsl:import href="boardgamebliss-game-detail.xsl"/>
    
    <xsl:template name="CrawlCategory">
        <xsl:param name="doc" select="'Default value for page'"/>
        <xsl:param name="host" select="'Default value for host'"/>
        <xsl:apply-templates select="$doc//div[@class='product-item product-item--vertical  1/3--tablet-and-up 1/4--desk']">
            <xsl:with-param name="host" select="$host"/>
        </xsl:apply-templates>
        
    </xsl:template>
    
    <xsl:template match="div[@class='product-item product-item--vertical  1/3--tablet-and-up 1/4--desk']">
        <xsl:param name="host" select="'Default host'"/>
        <xsl:for-each select=".">
            <game>
                <xsl:variable name="href" select="a/@href"/>
                <xsl:variable name="link" select="concat($host, $href)"/>
                
                <thumbnail>
                    <xsl:value-of select="a//noscript/img/@src"/>
                </thumbnail>
                <link>
                    <xsl:value-of select="$link"/>
                </link>
                <title>
                    <xsl:value-of select=".//div[@class='product-item__info-inner']/a"/>
                </title>

                <xsl:variable name="doc" select="document($link)"/>
                <xsl:call-template name="CrawlGameDetail">
                    <xsl:with-param name="doc" select="$doc"/>
                </xsl:call-template>
                
            </game>
        </xsl:for-each>
        
    </xsl:template>
    
</xsl:stylesheet>
