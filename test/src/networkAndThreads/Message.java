import org.omg.CORBA.DataOutputStream;

import java.io.ObjectInputStream;
import java.io.Serializable;

public class Message implements Serializable {
    protected int code;

    public Message(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Message{" +
                "code=" + code +
                '}';
    }
}
