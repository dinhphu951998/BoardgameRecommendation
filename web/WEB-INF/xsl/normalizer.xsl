<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : lastfilter.xsl
    Created on : October 6, 2019, 5:55 PM
    Author     : PhuNDSE63159
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" indent="yes" encoding="UTF-8" version="1.0"/>

    <xsl:template match="board-game">
        <board-game>
            <xsl:apply-templates select="game"/>
        </board-game>
    </xsl:template>
    
    <xsl:template match="game">
        <games>
            <xsl:apply-templates select="title[text()]"/>
            <xsl:apply-templates select="category[text()]"/>
            <xsl:apply-templates select="thumbnail[text()]"/>
            <xsl:apply-templates select="description[text()]"/>
            <xsl:apply-templates select="link[text()]"/>
            
            <xsl:apply-templates select="images[count(image)>0]"/>
            
            <xsl:apply-templates select="age/minAge[text()]"/>
            <xsl:apply-templates select="age/maxAge[text()]"/>
                
            <xsl:apply-templates select="time/minTime[text()]"/>
            <xsl:apply-templates select="time/maxTime[text()]"/>
                
            <xsl:apply-templates select="numPlayer/minPlayer[text()]"/>
            <xsl:apply-templates select="numPlayer/maxPlayer[text()]"/>
        </games>
    </xsl:template>
    
    <xsl:template match="images[count(image)>0]">
        <images>
            <xsl:for-each select="image[text()]">
                <imageUrl>
                    <xsl:value-of select="."/>
                </imageUrl>
            </xsl:for-each>
        </images>
    </xsl:template>
    
    <xsl:template match="category[text()]">
        <category>
            <xsl:value-of select="."/>
        </category>
    </xsl:template>
    
    <xsl:template match="title[text()]">
        <title>
            <xsl:value-of select="."/>
        </title>
    </xsl:template>
    
    <xsl:template match="thumbnail[text()]">
        <thumbnail>
            <xsl:value-of select="."/>
        </thumbnail>
    </xsl:template>

    <xsl:template match="description[text()]">
        <description>
            <xsl:value-of select="."/>
        </description>
    </xsl:template>
    
    <xsl:template match="link[text()]">
        <link>
            <xsl:value-of select="."/>
        </link>
    </xsl:template>
    
    <xsl:template match="age/minAge[text()]">
        <minAge>
            <xsl:value-of select="."/>
        </minAge>
    </xsl:template>
    
    <xsl:template match="age/maxAge[text()]">
        <maxAge>
            <xsl:value-of select="."/>
        </maxAge>
    </xsl:template>
    
    <xsl:template match="time/minTime[text()]">
        <minTime>
            <xsl:value-of select="."/>
        </minTime>
    </xsl:template>
    
    <xsl:template match="time/maxTime[text()]">
        <maxTime>
            <xsl:value-of select="."/>
        </maxTime>
    </xsl:template>
    
    <xsl:template match="numPlayer/minPlayer[text()]">
        <minPlayer>
            <xsl:value-of select="."/>
        </minPlayer>
    </xsl:template>
    
    <xsl:template match="numPlayer/maxPlayer[text()]">
        <maxPlayer>
            <xsl:value-of select="."/>
        </maxPlayer>
    </xsl:template>
    
</xsl:stylesheet>
