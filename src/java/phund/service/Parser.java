/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.service;

import java.io.OutputStream;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author PhuNDSE63159
 */
public abstract class Parser {
    
    public abstract OutputStream parse() throws XMLStreamException;
    
}
