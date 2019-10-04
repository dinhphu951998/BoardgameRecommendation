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
        
        <xsl:call-template name="CrawlImages">
            <xsl:with-param name="gallery" select="$doc//a[@itemprop='thumbnailUrl']"/>
        </xsl:call-template>
        
        <xsl:call-template name="CrawlGameInfo">
            <xsl:with-param name="TableInfo" select="$doc//table[@itemscope='itemscope']"/>
        </xsl:call-template>

        <description>
            
            <xsl:variable name="description">
                <xsl:apply-templates select="$doc//div[@id='av_section_1']//div[@itemprop='text']"/>
            </xsl:variable>
            
            <xsl:value-of select="normalize-space($description)"/>
            
        </description>

    </xsl:template>
    
    <xsl:template name="CrawlImages">
        <xsl:param name="gallery" select="'Default gallery'"/>
        <images>
            <xsl:for-each select="$gallery">
                <image>
                    <xsl:value-of select=".//img/@src"/>
                </image>
            </xsl:for-each>
        </images>
        
    </xsl:template>
    
    <xsl:template name="CrawlGameInfo">
        <xsl:param name="TableInfo" select="'Default info'"/>
        
        <xsl:variable name="info" select="$TableInfo//tr"/>
        
        <numPlayer>
            <xsl:value-of select="$info[3]/td[2]/text()"/>
        </numPlayer>
        
        <age>
            <xsl:value-of select="$info[4]/td[2]/text()"/>
        </age>
        
        <time>
            <xsl:value-of select="$info[5]/td[2]/text()"/>
        </time>
        
        <category>
            <xsl:value-of select="$info[6]/td[2]/text()"/>
        </category>
        
        
    </xsl:template>
    
</xsl:stylesheet>
