import javax.swing.*;
import javax.swing.text.BadLocationException;

public abstract class PageCentrale extends JPanel {

    public abstract void addMyButton();

    public abstract void initMyButton();

    public abstract void dessiner() throws BadLocationException;
}
