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
        
        <images>
            <image>
                <xsl:value-of select="$doc//div[@id='feature-image']/img/@src"/>
            </image>
        </images>
        
        <xsl:apply-templates  select="$doc//div[@id='detail-icons']"/>
        
        <description>
            <xsl:variable name="description">
                <xsl:apply-templates select="$doc//div[@id='overview']"/>
            </xsl:variable>
            
            <xsl:value-of select="normalize-space($description)"/>
        </description>

    </xsl:template>

    <xsl:template match="div[@id='detail-icons']">
        <numPlayer>
            <xsl:value-of select="div[@id='detail-icon-players']/text()"/>
        </numPlayer>
        <time>
            <xsl:value-of select="div[@id='detail-icon-time']/text()"/>
        </time>
        <age>
            <xsl:value-of select="div[@id='detail-icon-age']/text()"/>
        </age>
        
    </xsl:template>
</xsl:stylesheet>
