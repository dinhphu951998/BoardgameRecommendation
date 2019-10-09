/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.resolver;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamSource;
import phund.checker.XmlSyntaxChecker;
import phund.utils.FileUtils;
import phund.utils.HttpUtils;

/**
 *
 * @author PhuNDSE63159
 */
public class CustomURIResolver implements URIResolver {

    private int count = 0;

    @Override
    public Source resolve(String href, String base) throws TransformerException {
        if (href != null
                && (href.indexOf("https://hocvienboardgame.vn") == 0
                || href.indexOf("https://www.boardgamehub.net") == 0
                || href.indexOf("https://boardgaming.com") == 0
                || href.indexOf("https://www.boardgamebliss.com") == 0)) {
            try {
                String content = HttpUtils.getHttpContent(href);
                XmlSyntaxChecker checker = new XmlSyntaxChecker();
                content = checker.refineHtml(content);
                content = checker.check(content);

                InputStream stream = new ByteArrayInputStream(content.toString().getBytes(StandardCharsets.UTF_8));
                System.out.println("Gọi hàm " + ++count + ": " + href);
                return new StreamSource(stream);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

}
