package io.slack.utils;

import javax.swing.*;
import java.awt.*;

public class GraphicsUtils {

    public static void notification(JButton button){
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setForeground(Color.white);
        button.setFont( new Font(Font.DIALOG,Font.BOLD,15));
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.CENTER);
    }

    public static void buttonWithText(JButton button){
        button.setFont( new Font(Font.DIALOG,Font.BOLD,25));
        button.setHorizontalTextPosition(JButton.RIGHT);
        button.setVerticalTextPosition(JButton.CENTER);
        button.setHorizontalAlignment(JButton.LEFT);
        button.setBorderPainted(false);
        //button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        //button.setOpaque(false);
    }
}
