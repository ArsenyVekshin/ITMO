package ArsenyVekshin.lab6.common.tools;

import java.io.*;

public final class ObjectSerializer {

    private ObjectSerializer(){}

    public static byte[] serializeObject(Object o) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(o);
            out.flush();
            return bos.toByteArray();
        }
    }

    public static Object deserializeObject(byte[] byteArray) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
             ObjectInputStream in = new ObjectInputStream(bis)) {
            return in.readObject();
        }
    }
}
