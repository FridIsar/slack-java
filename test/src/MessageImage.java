import javax.swing.*;
import java.awt.*;

public class MessageImage extends Message {

    private Image image = null;

    public MessageImage(User auteur, String message, Image image) {
        super(auteur, message);
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    @Override
    public JTextPane dessiner() {
        JTextPane textPane = super.dessiner();

        ImageIcon img = new ImageIcon(image.getScaledInstance(200,200, Image.SCALE_SMOOTH));
        textPane.insertIcon( img );
        return textPane;

    }
}
