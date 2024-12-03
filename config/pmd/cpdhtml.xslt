<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>
    
    <xsl:template match="/">
        <html>
            <head>
                <title>CPD Results</title>
                <style type="text/css">
                    .SummaryTitle  { }
                    .SummaryNumber { background-color:#DDDDDD;text-align:center; }
                    .ItemNumber    { background-color: #DDDDDD; }
                    .CodeFragment  { background-color: #BBBBBB; display:none; margin:0.5em 0em 0.5em 0em; }
                    .ExpandButton  { background-color: #FFFFFF; font-size: 8pt; width: 89px; margin:0px; padding:0px; }
                </style>
                <script type="text/javascript">
                    function toggleCodeSection(btn, id)
                    {
                        var code = document.getElementById(id);
                        if (code.style.display == 'none') {
                            code.style.display = 'block';
                            btn.innerHTML = '-';
                        } else {
                            code.style.display = 'none';
                            btn.innerHTML = '+';
                        }
                    }
                </script>
            </head>
            <body>
                <h2>Summary of duplicated code</h2>
                <table border="1" class="summary">
                    <tr>
                        <th>Files</th>
                        <th>Total Lines</th>
                        <th>Duplicated Lines</th>
                        <th>Duplicated Blocks</th>
                    </tr>
                    <tr>
                        <td class="SummaryNumber"><xsl:value-of select="count(//file)"/></td>
                        <td class="SummaryNumber"><xsl:value-of select="sum(//file/@lines)"/></td>
                        <td class="SummaryNumber"><xsl:value-of select="sum(//duplication/@lines)"/></td>
                        <td class="SummaryNumber"><xsl:value-of select="count(//duplication)"/></td>
                    </tr>
                </table>
                <h2>Duplications</h2>
                <xsl:for-each select="//duplication">
                    <xsl:variable name="duplicationId" select="generate-id(.)"/>
                    <table border="1" width="100%" class="duplication">
                        <tr>
                            <td>
                                <table>
                                    <tr>
                                        <td valign="top">
                                            <button class="ExpandButton" onclick="toggleCodeSection(this, '{$duplicationId}')">+</button>
                                        </td>
                                        <td>
                                            Lines: <xsl:value-of select="@lines"/><br/>
                                            Tokens: <xsl:value-of select="@tokens"/>
                                            <xsl:for-each select="file">
                                                <br/>File: <xsl:value-of select="@path"/>
                                                line: <xsl:value-of select="@line"/>
                                            </xsl:for-each>
                                        </td>
                                    </tr>
                                </table>
                                <pre id="{$duplicationId}" class="CodeFragment">
                                    <xsl:value-of select="codefragment"/>
                                </pre>
                            </td>
                        </tr>
                    </table>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>