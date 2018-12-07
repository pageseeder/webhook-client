<?xml version="1.0" encoding="UTF-8"?>
<!--
  Templates for the 'default' group.

  This is the main stylesheet where the general layout of the HTML is defined.
-->
<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:f="http://allette.com.au/sus"
                exclude-result-prefixes="#all">

<!-- General Output properties. -->
<xsl:output method="html" encoding="utf-8" indent="yes" undeclare-prefixes="no" media-type="text/html"/>

<!--
  Main template called in all cases.
-->
<xsl:template match="/">
<!-- Display the HTML Doctype -->
<xsl:text disable-output-escaping="yes"><![CDATA[<!doctype html>
]]></xsl:text>

<xsl:variable name="events" select="//events/event"/>

<html>
  <head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/foundation/6.5.1/css/foundation.min.css" />
  </head>
  <body>
    <div class="grid-x grid-margin-x">
      <div class="cell small-8 large-offset-2">
        <h1><xsl:value-of select="//app/@title"/></h1>
        
        <h2>Events Received</h2>
        
        <table>
          <thead>
            <tr>
              <th>Id</th>
              <th>Type</th>
              <th>Datetime</th>
              <th>Groups</th>
              <th>Object</th>
            </tr>
          </thead>
          <tbody>
            <xsl:for-each select="$events">
            <tr>
              <td><xsl:value-of select="@id"/></td>
              <td><xsl:value-of select="@type"/></td>
              <td><xsl:value-of select="@date-time"/></td>
              <td>
                <xsl:if test="groups/group">
                <ul>
                  <xsl:for-each select="groups/group">
                    <li><xsl:value-of select="concat(@id, '-', @name)"/></li> 
                  </xsl:for-each>
                </ul>
                </xsl:if>
              </td>
              <td>
                <ul>
                  <li>Element: <xsl:value-of select="object/@element"/></li>
                  <li>Id: <xsl:value-of select="object/@id"/></li>
                   <xsl:for-each select="object/@*[not(name() = ('element', 'id'))]">
                     <li><xsl:value-of select="concat(name(), ': ', .)"/></li> 
                   </xsl:for-each>
                </ul>
              </td>
            </tr>
            </xsl:for-each>
          </tbody>
        </table>
      </div>
    </div>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/foundation/6.5.1/js/foundation.min.js"></script>
  </body>
</html>
</xsl:template>

</xsl:stylesheet>
