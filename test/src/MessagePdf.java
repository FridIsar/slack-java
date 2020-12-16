import java.awt.*;
import java.io.File;

public class MessagePdf extends Message{
    private File file;

    public MessagePdf(User auteur, String message, File file) {
        super(auteur, message);
        this.file = file;
    }


}
