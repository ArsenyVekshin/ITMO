package ArsenyVekshin.lab5.ui.file;

import ArsenyVekshin.lab5.ui.OutputHandler;
import ArsenyVekshin.lab5.ui.exeptions.StreamBrooked;

import java.io.*;

public class FileOutputHandler implements OutputHandler {
    private OutputStream file = null;
    private Writer stream = null;

    public void open(String path) throws FileNotFoundException {
        try{
            file = new FileOutputStream(path);
            stream = new OutputStreamWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void print(String data) throws StreamBrooked {
        if(file == null || stream == null) throw new StreamBrooked();
        try {
            stream.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void println(String data) throws StreamBrooked {
        print(data + "\n");
    }

    public void close() throws IOException {
        stream.close();
    }
}
