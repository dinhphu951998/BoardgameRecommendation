<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : BoardGame.vn.Detail.xsl
    Created on : September 25, 2019, 1:36 PM
    Author     : PhuNDSE63159
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" >
    <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
    
    <xsl:template name="CrawlGameDetail">
        <xsl:param name="doc" select="'Default value for page'"/>
        <xsl:param name="host" select="'Default value for host'"/>
        
        <xsl:apply-templates  select="$doc//div[@class='product-gallery__size-limiter']"/>
        
        <xsl:apply-templates select="$doc//div[@class='rte text--pull']"/>
        
    </xsl:template>

    <xsl:template match="div[@class='rte text--pull']">
        <xsl:variable name="tr" select=".//tr"/>
        
        <numPlayer>
            <xsl:value-of select="$tr[3]/td[2]"/>
        </numPlayer>
        
        <time>
            <xsl:value-of select="$tr[4]/td[2]"/>
        </time>
        <age>
            <xsl:value-of select="$tr[5]/td[2]"/>
        </age>
        
        <description>
            <xsl:variable name="des" select="text()"/>
            <xsl:if test="$des">
                <xsl:value-of select="$des"/>    
            </xsl:if>
            <xsl:if test="not($des)">
                <xsl:apply-templates select="p | ul"/>
            </xsl:if>
        </description>
    </xsl:template>

    <xsl:template match="div[@class='product-gallery__size-limiter']">
        <images>
            <xsl:for-each select="div/img">
                <image>
                    <xsl:value-of select="@data-zoom"/>
                </image>
            </xsl:for-each>
        </images>
    </xsl:template>
    
</xsl:stylesheet>
