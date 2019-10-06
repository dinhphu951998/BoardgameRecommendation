/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.validation;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;

/**
 *
 * @author PhuNDSE63159
 */
public class BoardGameValidationHandler implements ValidationEventHandler {

    private int errorCounter = 0;
    private OutputStream errorStream;

    public int getErrorCounter() {
        return errorCounter;
    }

    public OutputStream getErrorStream() {
        return errorStream;
    }

    @Override
    public boolean handleEvent(ValidationEvent event) {
        try {
            if (errorStream == null) {
                errorStream = new ByteArrayOutputStream();
            }
            if (event.getSeverity() == ValidationEvent.ERROR
                    || event.getSeverity() == ValidationEvent.FATAL_ERROR) {
                ValidationEventLocator locator = event.getLocator();
                String error = "";
                error += String.format("%s.\n", ++errorCounter);
                error += String.format("Invalid board game document: %s\n", locator.getURL());
                error += String.format("Error: %s\n", event.getMessage());
                error += String.format("Error at column %d, line %s \n", locator.getColumnNumber(), locator.getLineNumber());

                errorStream.write(error.getBytes());
                errorStream.flush();
            }
        } catch (IOException ex) {
            Logger.getLogger(BoardGameValidationHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

}
