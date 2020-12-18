package io.slack.front;

import javax.swing.JPanel;
import javax.swing.text.BadLocationException;

//TODO ask question about interface or abstract class
public abstract class CentralePage extends JPanel {

    public abstract void addMyButton();

    public abstract void initMyButton();

    public abstract void dessiner() throws BadLocationException;
}
