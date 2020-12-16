package io.slack.utils;

public class EmailUtils {

    public static boolean isEmail(String chaine) {
        return chaine.endsWith("@gmail.com") || chaine.endsWith("@hotmail.fr") || chaine.endsWith("@yahoo.fr") || chaine.endsWith("@upmc.fr") || chaine.endsWith("@lip6.fr") || chaine.endsWith("@sorbonne-universite.fr");
    }

    public static boolean isPassword(String chaine) {
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

    private EmailUtils() {}
}
