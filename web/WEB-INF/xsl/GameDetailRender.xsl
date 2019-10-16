<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>
    <!--<xsl:output method="xml" indent="yes" encoding="UTF-8"/>-->

    <xsl:template match="game">
        <div class="game-detail" id="game-detail" style="display: block">
            <div class="row game-modal-content">
                <div class="col span-2-of-6 game-image ">
                    <img>
                        <xsl:attribute name="src">
                            <xsl:value-of select="normalize-space(images//imageUrl[position()=1])"/>
                        </xsl:attribute>
                        <xsl:attribute name="alt">
                            <xsl:value-of select="title"/>
                        </xsl:attribute>
                    </img>
                </div>
                <div class="col span-4-of-6 game-info ">
                    <div class="game-title ">
                        <xsl:value-of select="title"/>
                    </div>
                    <xsl:apply-templates select="description"/>
                    
                    <xsl:apply-templates select="category"/>
                    
                    <div class="game-panel">
                        <div class="time">
                            <div class="icon">
                                <i class="fa fa-clock-o" aria-hidden="true"></i>
                            </div>
                            <div class="text">
                                <span class="mintime">
                                    <xsl:value-of select="minTime"/>m
                                    <xsl:if test="not(maxTime)">
                                        +
                                    </xsl:if>
                                </span>
                                <xsl:apply-templates select="maxTime"/>
                            </div>
                        </div>
                        <div class="age">
                            <div class="icon">
                                <i class="fa fa-child" aria-hidden="true"></i>
                            </div>
                            <div class="text">
                                <span class="minAge">
                                    <xsl:value-of select="minAge"/>
                                    <xsl:if test="not(maxAge)">
                                        +
                                    </xsl:if>
                                </span>
                                <xsl:apply-templates select="maxAge"/>
                            </div>
                        </div>
                        <div class="num-players">
                            <div class="icon">
                                <i class="fa fa-users" aria-hidden="true"></i>
                            </div>
                            <div class="text">
                                <span class="minPlayers">
                                    <xsl:value-of select="minPlayer"/>
                                    <xsl:if test="not(maxPlayer)">
                                        +
                                    </xsl:if>
                                </span> 
                                <xsl:apply-templates select="maxPlayer"/>
                            </div>
                        </div>
                    </div>
                    <a href="{normalize-space(link)}" class="more-info" target="_blank">
                        <i class="fa fa-external-link" aria-hidden="true"></i> More information
                    </a>
                </div>
            </div>
        </div>
    </xsl:template>
    
    
    <xsl:template match="description[text()]">
        <div class="row game-description ">
            <xsl:value-of select="."/>
        </div>
    </xsl:template>
    
    <xsl:template match="category[text()]">
        <div class="category">
            <strong>Category: </strong>
            <span>
                <xsl:value-of select="."/>
            </span>
        </div>
    </xsl:template>
    
    <xsl:template match="maxTime[text()]">
        - <span class="maxtime">
            <xsl:value-of select="."/>m
        </span>
    </xsl:template>
    <xsl:template match="maxTime">
        +
    </xsl:template>
    
    <xsl:template match="maxAge[text()]">
        - <span class="maxAge">
            <xsl:value-of select="."/>
        </span>
    </xsl:template>
    <xsl:template match="maxAge">
        +
    </xsl:template>

    <xsl:template match="maxPlayer[text()]">
        - <span class="maxPlayers">
            <xsl:value-of select="."/>
        </span>
    </xsl:template>
    <xsl:template match="maxPlayer">
        +
    </xsl:template>



</xsl:stylesheet>
