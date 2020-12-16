import java.awt.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.*;

//class permettant la gestion des images

public class Imagerie{

    private static Image image=null;

    public static Image getImage(String nom){
        try {
            image = ImageIO.read(new File("./Images/"+nom));
        }
        catch(IOException exc){
            exc.printStackTrace();
        }
        
        return image;
    }

    public static Image getImage(File file){
        try{
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}
