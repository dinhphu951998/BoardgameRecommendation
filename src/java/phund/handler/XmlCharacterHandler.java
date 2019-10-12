///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package phund.handler;
//
//import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;
//import java.io.IOException;
//import java.io.Writer;
//
///**
// *
// * @author PhuNDSE63159
// */
//public class XmlCharacterHandler implements CharacterEscapeHandler {
//
//    @Override
//    public void escape(char[] chars, int start, int length, boolean isAttValue, Writer writer) throws IOException {
//        String s = String.copyValueOf(chars, start, length);
//        s = s.replace("'", "&quot;");
//        writer.write(s);
//    }
//
//}
