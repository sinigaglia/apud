/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apud;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author Adriano
 */
public class DirFileFilter implements FileFilter {

    @Override
    public boolean accept(File file) {
        return file.isDirectory();
    }
    
}
