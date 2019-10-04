/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author PhuNDSE63159
 */
public class HttpUtils {

    public static InputStream getHttp(String href) {
        URL url = null;
        HttpsURLConnection connection = null;
        InputStream is = null;
        try {
            href = normalizeUrl(href);
            url = new URL(href);
            connection = (HttpsURLConnection) url.openConnection();
            setHeader(connection);
            is = connection.getInputStream();
        } catch (MalformedURLException ex) {
            Logger.getLogger(HttpUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HttpUtils.class.getName()).log(Level.SEVERE, null, ex);
            is = connection.getErrorStream();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return is;
    }

    public static String getHttpContent(String href) {
        URL url = null;
        HttpsURLConnection connection = null;
        InputStream is = null;
        String data = "";
        try {
            href = normalizeUrl(href);
            url = new URL(href);
            connection = (HttpsURLConnection) url.openConnection();
            setHeader(connection);
            is = connection.getInputStream();
            int code = connection.getResponseCode();
            String msg = connection.getResponseMessage();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String c = "";
            while ((c = br.readLine()) != null) {
                data += c;
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(HttpUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            is = connection.getErrorStream();
            data = FileUtils.read(is);
            Logger.getLogger(HttpUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return data;
    }

    private static void setHeader(HttpsURLConnection connection) throws ProtocolException {
//        connection.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;application/html");
//        connection.addRequestProperty("Accept-Encoding", "gzip, deflate");
//        connection.addRequestProperty("Accept-Language", "en-US,en;q=0.9,vi;q=0.8");
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36");
    }
    
    private static String normalizeUrl(String url){
        url = url.replace("&amp;", "&");
        
        return url;
    }

}
