package io.slack.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


public class Outils {
    public static boolean isEmail(String chaine){
        return chaine.endsWith("@gmail.com") || chaine.endsWith("@hotmail.fr") || chaine.endsWith("@yahoo.fr") || chaine.endsWith("@upmc.fr") || chaine.endsWith("@lip6.fr") || chaine.endsWith("@sorbonne-universite.fr");
    }

    public static boolean isNumero(String chaine){
        return (chaine.startsWith("06") || chaine.startsWith("07") ) && chaine.length()==10;
    }

    public static boolean isPassword(String chaine){
        boolean presentMaj=false;
        boolean presentMin=false;
        boolean presentSpec=false;
        boolean presentNum=false;
        for(int i=0; i<chaine.length(); i++){
            if(Character.isUpperCase(chaine.charAt(i)) )
                presentMaj=true;
            if(Character.isLowerCase(chaine.charAt(i)) )
                presentMin=true;
            if(! Character.isLetterOrDigit(chaine.charAt(i)) )
                presentSpec=true;
            if(Character.isDigit(chaine.charAt(i)) )
                presentNum=true;
        }
        return presentMaj && presentMin && presentNum && presentSpec && chaine.length()>=8;
    }

    public static void attente(int x) {
        for(int i=0; i<x; i++){
            //System.out.println(".");
            try{ Thread.sleep(1000); }
            catch(InterruptedException e){
                e.printStackTrace();
            }
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

    public static File copyFile(File source) throws IOException {
        File dest = new File("dest");
        Files.copy(source.toPath(), dest.toPath());

        return dest;
    }

}
