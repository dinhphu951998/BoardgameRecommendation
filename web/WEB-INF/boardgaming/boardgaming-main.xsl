<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : hocvienboardgame.xsl
    Created on : September 30, 2019, 10:54 AM
    Author     : PhuNDSE63159
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:prefix="https://boardgaming.com/" 
                xmlns:exsl="http://exslt.org/common"
                version="1.0">
    <xsl:output method="xml" indent="yes"/>

    <xsl:import href="boardgaming-category.xsl"/>
    <xsl:import href="../xsl/tokenizer.xsl"/>
    
    <xsl:template match="prefix:board-game">
        <xsl:variable name="crawlPages">
            <xsl:call-template name="tokenize">
                <xsl:with-param name="string" select="normalize-space(@link)"/>
                <xsl:with-param name="delim" select="@delim"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="host" select="@host"/>
        
        <board-game>
            
            <xsl:variable name="nodeSet" select="exsl:node-set($crawlPages)"/>
            <xsl:for-each select="$nodeSet/token">
                <xsl:call-template name="CrawlTheLink">
                    <xsl:with-param name="link" select="."/>
                    <xsl:with-param name="host" select="$host"/>
                </xsl:call-template>
            </xsl:for-each>

        </board-game>
    </xsl:template>
    
    <xsl:template name="CrawlTheLink">
        <xsl:param name="link" select="'Default pagerTop value'"/>
        <xsl:param name="host" select="'Default host value'"/>
        
        <xsl:variable name="page" select="document($link)"/>
        
        <xsl:call-template name="CrawlCategory">
            <xsl:with-param name="doc" select="$page"/>
            <xsl:with-param name="host" select="$host"/>
        </xsl:call-template>
        
        <xsl:variable name="pagination" select="$page//div[@class='navigation']//div[@class='pagination']"/>
        
        <xsl:if test="$pagination/span[@class='current']/following-sibling::*[1]">
            <xsl:variable name="nextLink" select="$pagination/span[@class='current']/following-sibling::*[1]/@href"/>
            <xsl:call-template name="CrawlTheLink">
                <xsl:with-param name="link" select="$nextLink"/>
                <xsl:with-param name="host" select="$host"/>
            </xsl:call-template>
        </xsl:if>
        
    </xsl:template>

</xsl:stylesheet>
