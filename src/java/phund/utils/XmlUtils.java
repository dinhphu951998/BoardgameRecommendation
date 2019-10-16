/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.utils;

import java.io.File;
import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author PhuNDSE63159
 */
public class XmlUtils {

    public static Schema getSchema(String xsdFile) throws SAXException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema sc = factory.newSchema(new File(xsdFile));
        return sc;
    }

    public static int computeMatchingPercent(String a, String b) {
        int n = a.length();
        int m = b.length();

        int dp[][] = new int[n + 1][m + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (a.charAt(i) == b.charAt(j)) {
                    dp[i + 1][j + 1] = dp[i][j] + 1;
                } else {
                    dp[i + 1][j + 1] = Math.max(dp[i][j + 1], dp[i + 1][j]);
                }
            }
        }
        return (int) dp[n][m] * 100 / Math.min(m, m);
    }

}
