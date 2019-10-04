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
    
    <xsl:template name="CrawlGameDetail">
        <xsl:param name="doc" select="'Default value for page'"/>
        <xsl:param name="host" select="'Default value for host'"/>
        
        <xsl:apply-templates  select="$doc//div[@class='general-description']"/>

    </xsl:template>
    
    <xsl:template match="div[@class='general-description']" >
        <xsl:apply-templates select="div[@class='position-left']"/>
        <xsl:apply-templates select="div[@class='position-right']"/>
    </xsl:template>
        
    <xsl:template match="div[@class='position-left']">
        <images>
            <xsl:for-each select=".//img">
                <image>
                    <xsl:value-of select="@src"/>
                </image>    
            </xsl:for-each>
        </images>
    </xsl:template>
        
    <xsl:template match="div[@class='position-right']">
        <title>
            <xsl:value-of select=".//div[@class='introduce-game']//h2[@class='title']/text()"/>
        </title>
        <category>
            <xsl:value-of select=".//div[@class='category']//a/text()"/>
        </category>
        <description>
            <xsl:value-of select=".//div[@class='description-content']/p/text()"/>
        </description>
        
        <xsl:apply-templates select=".//div[@class='icon-game']"/>
        
    </xsl:template>
    
    <xsl:template match="div[@class='icon-game']">
        <numPlayer>
            <xsl:variable name="numPlayer" select=".//li[@class='number-play ']//span/text()"/>
            <xsl:value-of select="normalize-space($numPlayer)"/>
        </numPlayer>
        <time>
            <xsl:variable name="time" select=".//li[@class='time']//span/text()"/>
            <xsl:value-of select="normalize-space($time)"/>
        </time>
        <age>
            <xsl:variable name="age" select=".//li[@class='age']/text()"/>
            <xsl:value-of select="$age"/>
        </age>
        <weight>
            <xsl:variable name="weight" select=".//li[@class='weight']/text()"/>
            <xsl:value-of select="$weight"/>
        </weight>
    </xsl:template>
</xsl:stylesheet>
