<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" >
    <xsl:output method="html" encoding="UTF-8" indent="yes" omit-xml-declaration="yes"/>
    <xsl:param name="group" select="4"/>
        
    <xsl:template match="board-game[games]">
        <div class="game-list">
            <xsl:for-each select="games[position() mod $group = 1]">
                <div class="row">
                    <xsl:apply-templates select="."/>
                    
                    <xsl:call-template name="callGroup">
                        <xsl:with-param name="sibling" select="(following-sibling::*)[1]"/>
                    </xsl:call-template>
                    
                </div>
            </xsl:for-each>
        </div>
    </xsl:template>
    
    <xsl:template match="board-game">
        <h2 style="text-align: center; color: #00acac">
            We will update soon!
        </h2>
    </xsl:template>
    
    <xsl:template name="callGroup">
        <xsl:param name="sibling" />
        
        <xsl:variable name="position" select="count($sibling/preceding-sibling::*)+1"/>
        
        <xsl:if test="($position mod $group) != 1">
            <xsl:apply-templates select="$sibling"/>
            <xsl:call-template name="callGroup">
                <xsl:with-param name="sibling" 
                                select="($sibling/following-sibling::*)[1]"/>
            </xsl:call-template>
        </xsl:if>
        
    </xsl:template>
    
    <xsl:template match="games">
        <div class="col span-1-of-4 game-item">
            <div class="image-rate">
                <xsl:apply-templates select="ratingPoint[text()]"/>
                <img src="{thumbnail}" alt="{title}" />
            </div>
            <div class="desc">
                <span class="rating-point">
                    <xsl:element name="fieldset">
                        <xsl:attribute name="class">rate</xsl:attribute>
                        <xsl:if test="point">
                            <xsl:attribute name="value">
                                <xsl:value-of select="point"/>
                            </xsl:attribute>    
                        </xsl:if>
                        <input type="radio" id="rating10{id}" name="{id}" value="10" onClick="onVote(this)"/>
                        <label for="rating10{id}" title="5 starts"></label>
                        <input type="radio" id="rating9{id}" name="{id}" value="9" onClick="onVote(this)"/>
                        <label class="half" for="rating9{id}" title="4.5 starts"></label>
                        <input type="radio" id="rating8{id}" name="{id}" value="8" onClick="onVote(this)"/>
                        <label for="rating8{id}" title="4 stars"></label>
                        <input type="radio" id="rating7{id}" name="{id}" value="7" onClick="onVote(this)"/>
                        <label class="half" for="rating7{id}" title="3.5 starts"></label>
                        <input type="radio" id="rating6{id}" name="{id}" value="6" onClick="onVote(this)"/>
                        <label for="rating6{id}" title="3 stars"></label>
                        <input type="radio" id="rating5{id}" name="{id}" value="5" onClick="onVote(this)"/>
                        <label class="half" for="rating5{id}" title="2.5 starts"></label>
                        <input type="radio" id="rating4{id}" name="{id}" value="4" onClick="onVote(this)"/>
                        <label for="rating4{id}" title="2 stars"></label>
                        <input type="radio" id="rating3{id}" name="{id}" value="3" onClick="onVote(this)"/>
                        <label class="half" for="rating3{id}" title="1.5 starts"></label>
                        <input type="radio" id="rating2{id}" name="{id}" value="2" onClick="onVote(this)"/>
                        <label for="rating2{id}" title="1 star"></label>
                        <input type="radio" id="rating1{id}" name="{id}" value="1" onClick="onVote(this)"/>
                        <label class="half" for="rating1{id}" title="0.5 start"></label>
                        <input type="radio" id="rating0{id}" name="rating" value="0" />
                    </xsl:element>
                   
                </span>
                
                <a href="#" class="reference-link" value="{id}" onclick="initGameDetail(this)">
                    <span class="game-name">
                        <xsl:value-of select="title"/>
                    </span>
                </a>
                
                <span class="matched-point">
                    <xsl:apply-templates select="matchingPercent[text()]"/>
                </span>
            </div>

        </div>
    </xsl:template>
    
    <xsl:template match="matchingPercent[text()]">
        <xsl:value-of select="."/>% matched
    </xsl:template>
    <xsl:template match="ratingPoint[text()]">
        <span class="average-point">
            <xsl:value-of select="."/>/5
        </span>
    </xsl:template>
</xsl:stylesheet>
