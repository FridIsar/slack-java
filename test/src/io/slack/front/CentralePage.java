package io.slack.front;

import javax.swing.JPanel;
import javax.swing.text.BadLocationException;

//TODO poser question interface ou class abstract
public abstract class CentralePage extends JPanel {

    public abstract void addMyButton();

    public abstract void initMyButton();

    public abstract void dessiner() throws BadLocationException;
}