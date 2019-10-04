/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PhuNDSE63159
 */
public class FileUtils {

    public static String read(String filename) {
        String content = "";
        BufferedReader br = null;
        FileReader fr = null;
        try {
            fr = new FileReader(filename);
            br = new BufferedReader(fr);
            String c = "";
            while ((c = br.readLine()) != null) {
                content += c;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return content;
    }

    public static String read(InputStream inputStream) {
        String content = "";
        BufferedReader br = null;
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(inputStream);
            br = new BufferedReader(isr);
            String c = "";
            while ((c = br.readLine()) != null) {
                content += c;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return content;
    }

    public static void writeFile(String filename, String content) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(filename);
            bw = new BufferedWriter(fw);
            bw.write(content);
            bw.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
