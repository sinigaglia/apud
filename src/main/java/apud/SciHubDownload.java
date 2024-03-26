/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author Xando
 */
public class SciHubDownload {

    public static String getContent(String doi) {
        String https = "https://sci-hub.tw/" + doi;
        URL url;
        String content = "";
        try {

            url = new URL(https);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

            //dump all the content
            content = getContent(con);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    private static String getContent(HttpsURLConnection con) {
        if (con != null) {
            try {

                BufferedReader br
                        = new BufferedReader(
                                new InputStreamReader(con.getInputStream()));

                String buffer = "";
                String input;

                while ((input = br.readLine()) != null) {
                    buffer += input;
                }
                br.close();
                return buffer;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
