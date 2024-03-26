/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apud;

import java.io.File;
import java.io.FilenameFilter;

/**
 *
 * @author Adriano
 */
public class PDFFileFilter implements FilenameFilter {

    public final static String PDF = "pdf";

    @Override
    public boolean accept(File dir, String name) {

        String extension = getExtension(name);
        if (extension != null) {
            if (extension.equals(PDF)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private String getExtension(String s) {
        String ext = null;
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }
}
