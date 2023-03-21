package ArsenyVekshin.lab5.ui.file;

import ArsenyVekshin.lab5.ui.OutputHandler;
import ArsenyVekshin.lab5.ui.exeptions.FileAccessRightsException;
import ArsenyVekshin.lab5.ui.exeptions.StreamBrooked;

import java.io.*;

import java.io.*;

public class FileOutputHandler implements OutputHandler {
    private OutputStream file = null;
    private Writer stream = null;

    public FileOutputHandler(String path){
        try {
            open(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }
    }

    public void open(String path) throws FileNotFoundException {
        try{
            if(! new java.io.File(path).isFile()) new java.io.File(path).createNewFile();
            if(! new java.io.File(path).canWrite()) throw new FileAccessRightsException("write");
            file = new FileOutputStream(path);
            stream = new OutputStreamWriter(file);
        } catch (IOException e) {
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
