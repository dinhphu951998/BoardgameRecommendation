<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : GameRender.xsl
    Created on : October 7, 2019, 10:41 PM
    Author     : PhuNDSE63159
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" >
    <xsl:output method="html" encoding="UTF-8" indent="yes" omit-xml-declaration="yes"/>
    <!--<xsl:param name="group" select="number('4')"/>-->
    <xsl:param name="group" select="4"/>
        
    <xsl:template match="board-game">
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
    
    
    <xsl:template name="callGroup">
        <xsl:param name="sibling" select="'Default'"/>
        
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
            
            <fieldset class="rate">
                <input type="radio" id="rating10{@id}" name="{@id}" value="10" onClick="onVote(this)"/>
                <label for="rating10{@id}" title="5 starts"></label>
                <input type="radio" id="rating9{@id}" name="{@id}" value="9" onClick="onVote(this)"/>
                <label class="half" for="rating9{@id}" title="4.5 starts"></label>
                <input type="radio" id="rating8{@id}" name="{@id}" value="8" onClick="onVote(this)"/>
                <label for="rating8{@id}" title="4 stars"></label>
                <input type="radio" id="rating7{@id}" name="{@id}" value="7" onClick="onVote(this)"/>
                <label class="half" for="rating7{@id}" title="3.5 starts"></label>
                <input type="radio" id="rating6{@id}" name="{@id}" value="6" onClick="onVote(this)"/>
                <label for="rating6{@id}" title="3 stars"></label>
                <input type="radio" id="rating5{@id}" name="{@id}" value="5" onClick="onVote(this)"/>
                <label class="half" for="rating5{@id}" title="2.5 starts"></label>
                <input type="radio" id="rating4{@id}" name="{@id}" value="4" onClick="onVote(this)"/>
                <label for="rating4{@id}" title="2 stars"></label>
                <input type="radio" id="rating3{@id}" name="{@id}" value="3" onClick="onVote(this)"/>
                <label class="half" for="rating3{@id}" title="1.5 starts"></label>
                <input type="radio" id="rating2{@id}" name="{@id}" value="2" onClick="onVote(this)"/>
                <label for="rating2{@id}" title="1 star"></label>
                <input type="radio" id="rating1{@id}" name="{@id}" value="1" onClick="onVote(this)"/>
                <label class="half" for="rating1{@id}" title="0.5 start"></label>
                <input type="radio" id="rating0{@id}" name="rating" value="0" />
            </fieldset>
            
            <img src="{thumbnail}" alt="{title}" />

            <div class="desc">
                <span class="game-name">
                    <xsl:value-of select="title"/>
                </span>
                <span class="matched-point">
                    
                </span>
            </div>

        </div>
    </xsl:template>

</xsl:stylesheet>
