package ArsenyVekshin.lab6.client.ui.file;

import ArsenyVekshin.lab6.client.ui.OutputHandler;
import ArsenyVekshin.lab6.client.ui.exeptions.FileAccessRightsException;
import ArsenyVekshin.lab6.client.ui.exeptions.StreamBrooked;

import java.io.*;

public class FileOutputHandler implements OutputHandler {
    private OutputStream file = null;
    private Writer stream = null;

    public FileOutputHandler(String path){
        try {
            open(path);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());//e.printStackTrace();

        }
    }

    /**
     * Open output-stream to file
     * @param path file path
     */
    public void open(String path) throws FileNotFoundException {
        try{
            if(! new File(path).isFile()) new File(path).createNewFile();
            if(! new File(path).canWrite()) throw new FileAccessRightsException("write");
            file = new FileOutputStream(path);
            stream = new OutputStreamWriter(file);
        } catch (IOException e) {
            System.out.println(e.getMessage());//e.printStackTrace();
        }
    }

    @Override
    public void print(String data) throws StreamBrooked {
        if(file == null || stream == null) throw new StreamBrooked();
        try {
            stream.write(data);
        } catch (IOException e) {
            System.out.println(e.getMessage());//e.printStackTrace();
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
