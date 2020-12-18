package io.slack.front;

import io.slack.controller.ControllerClient;
import io.slack.front.UI.UIMessage;
import io.slack.model.Message;
import io.slack.model.MessageImage;
import io.slack.model.User;
import io.slack.utils.FileUtils;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.Dimension;
import java.util.ArrayList;


public class PageAccueil extends PageCentrale{
    private ArrayList<Message> contenu = new ArrayList<Message> ();

    JTextPane textPane = new JTextPane();
    JScrollPane jScrollPane= new JScrollPane(textPane);

    private static PageAccueil page=new PageAccueil();

    private PageAccueil() {
        setPreferredSize(new Dimension(1000, 850) ) ;
        setLayout(null);

        //remplirAccueil();

        try {
            dessiner();
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        textPane.setEditable(false);
        add(jScrollPane);
        jScrollPane.setBounds(0,0,1000,850);
        /*
        JScrollBar bar = jScrollPane.getVerticalScrollBar();
        bar.setValue( bar.getMaximum() );*/
    }

    public static PageAccueil getPage() {
        return page;
    }

    public void remplirAccueil(){
        MessageImage m1 = new MessageImage(new User("root@slack.com", "root", "createur"),"image 1 : petit test de la page d'accueil ", FileUtils.getImage( "Icons/logo.png" ) );
        MessageImage m2 = new MessageImage(new User("root@slack.com", "root", "createur"),"image 2 : petit test de la page d'accueil ", FileUtils.getImage( "Icons/logo.png" ) );
        MessageImage m3 = new MessageImage(new User("root@slack.com", "root", "createur"),"image 3 : petit test de la page d'accueil ", FileUtils.getImage( "Icons/logo.png" ) );
        MessageImage m4 = new MessageImage(new User("root@slack.com", "root", "createur"),"image 4 : petit test de la page d'accueil ", FileUtils.getImage( "Icons/logo.png" ) );

        contenu.add(m1);
        contenu.add(m2);
        contenu.add(m3);
        contenu.add(m4);
    }

    public void dessiner() throws BadLocationException {
        //System.out.println("dessiner");
        StyledDocument doc = textPane.getStyledDocument();
        textPane.getDocument().remove(0, doc.getLength() );
        for(int i =contenu.size()-1; i>=0; i--){
            Message mess = contenu.get(i);
            textPane.insertComponent( new UIMessage(mess).dessiner() );


            SimpleAttributeSet style = new SimpleAttributeSet();
            StyleConstants.setFontFamily(style, "Calibri");
            StyleConstants.setFontSize(style, 20);
            try {
                doc.insertString(doc.getLength(), "\n\n", style );
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    public void addMyButton() { }

    public void initMyButton() { }

}
