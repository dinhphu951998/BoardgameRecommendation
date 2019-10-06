/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.jaxbclass;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import phund.utils.JAXBUtils;

/**
 *
 * @author PhuNDSE63159
 */
public class Generator {

    public static void main(String[] args) {
        try {
            String output = "src/java";
            String packageName = "phund.jaxbclass";
            JAXBUtils.generateJavaClass(output, packageName, "web/WEB-INF/schema/boardgame.xsd");
        } catch (Exception ex) {
            Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
