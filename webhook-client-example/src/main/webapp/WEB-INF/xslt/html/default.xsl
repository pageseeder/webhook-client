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
<html>
  <head>
  </head>
  <body>
    <h1><xsl:value-of select="//app/@title"/></h1>
  </body>
</html>
</xsl:template>

</xsl:stylesheet>
