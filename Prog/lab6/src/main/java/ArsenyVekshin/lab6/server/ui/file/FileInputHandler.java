package ArsenyVekshin.lab6.server.ui.file;

import ArsenyVekshin.lab6.server.ui.InputHandler;
import ArsenyVekshin.lab6.server.ui.exeptions.LargeFileException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class FileInputHandler implements InputHandler {
    private InputStream file = null;
    private Scanner stream = null;

    public FileInputHandler(String path){
        open(path);
    }

    /**
     * Open input-stream from file
     * @param path file path
     */
    public void open(String path){
        try {
            file = new FileInputStream(path);
            if (new File(path).length() >= 100000) throw new LargeFileException();
            stream = new Scanner(file);
        } catch (FileNotFoundException | LargeFileException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String get() {
        return stream.nextLine();
    }

    @Override
    public boolean hasNextLine() {
        return stream.hasNextLine();
    }

    @Override
    public void close() {
        stream.close();
    }
}
