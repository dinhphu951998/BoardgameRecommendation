<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" version="1.0" indent="yes" encoding="UTF-8"/>

    <xsl:template name="tokenize">
        <xsl:param name="string" select="'Default string value'"/>
        <xsl:param name="delim" select="'Default delim value'"/>
        <xsl:choose>
            <xsl:when test="contains($string, $delim)">
                <token>
                    <xsl:value-of select="substring-before($string,$delim)"/>
                </token>
                <xsl:call-template name="tokenize">
                    <xsl:with-param name="string" select="substring-after($string,$delim)"/>
                    <xsl:with-param name="delim" select="$delim"/>
                </xsl:call-template> 
            </xsl:when>
            <xsl:otherwise>
                <token>
                    <xsl:value-of select="$string"/>
                </token>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>
