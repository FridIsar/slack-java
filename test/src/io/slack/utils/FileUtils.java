package io.slack.utils;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

//class permettant la gestion des images et fichiers

public class FileUtils {

    public static Image getImage(String nom) {
        return getImage(new File("./Images/" + nom));
    }

    public static Image getImage(File file) {
        try{
            return ImageIO.read(file);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}
