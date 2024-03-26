/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apud;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.JButton;
import javax.swing.JProgressBar;

public class HTTPSDownloadUtility {

    private static final int BUFFER_SIZE = 4096;

    public static final int SUCCESSO = 1;
    public static final int ERRO_CONEXAO = 0;
    public static final int ERRO_SCIHUB = -1;
    public static final int FILE_NOT_FOUND = -2;

    /**
     * Downloads a file from a URL
     *
     * @param fileURL HTTP URL of the file to be downloaded
     * @param saveDir path of the directory to save the file
     * @throws IOException
     */
    public static int downloadFile(String fileURL, DOITableModel dtm, int row) {
        try {
            URL url = new URL(fileURL);
            File fileOut = new File(dtm.getCaminhos().get(row));
            System.out.println("Baixando [" + fileOut.getName() + "] -> " + url);
            
            dtm.fireContentChanged(row);
            HttpsURLConnection httpConn = (HttpsURLConnection) url.openConnection();
            httpConn.setConnectTimeout(4000);
            int responseCode = httpConn.getResponseCode();

            // always check HTTP response code first
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String disposition = httpConn.getHeaderField("Content-Disposition");
                String contentType = httpConn.getContentType();
                int contentLength = httpConn.getContentLength();

                System.out.println("Content-Type = " + contentType);
                System.out.println("Content-Disposition = " + disposition);
                System.out.println("Content-Length = " + contentLength);
                System.out.println("fileName = " + fileOut.getName());

                // opens input stream from the HTTP connection
                InputStream inputStream = httpConn.getInputStream();

                // opens an output stream to save into file
                FileOutputStream outputStream = new FileOutputStream(fileOut);

                int bytesRead = -1;
                long sum = 0;
                byte[] buffer = new byte[BUFFER_SIZE];

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    sum += bytesRead;
                    outputStream.write(buffer, 0, bytesRead);
                    dtm.getProgressos().get(row).setValue((int) ((sum * 100) / contentLength));
                    dtm.fireContentChanged(row);
                }
                outputStream.close();
                inputStream.close();

                System.out.println("File downloaded");
                httpConn.disconnect();

                String check = new String(buffer);
                if (check.contains("article not found")) {
                    dtm.getBotoes().get(row).setEnabled(false);
                    fileOut.delete();
                    dtm.getProgressos().get(row).setValue(0);
                    dtm.fireContentChanged(row);
                    return -1;
                } else {
                    dtm.getBotoes().get(row).setEnabled(true);
                    dtm.fireContentChanged(row);
                    return 1;
                }
            } else {
                System.out.println("No file to download. Server replied HTTP code: " + responseCode);
                return -2;
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(HTTPSDownloadUtility.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HTTPSDownloadUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
