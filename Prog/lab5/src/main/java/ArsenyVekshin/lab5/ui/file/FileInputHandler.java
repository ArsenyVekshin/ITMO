package ArsenyVekshin.lab5.ui.file;

import ArsenyVekshin.lab5.ui.InputHandler;
import ArsenyVekshin.lab5.ui.exeptions.LargeFileException;

import java.io.*;
import java.util.Scanner;

import static ArsenyVekshin.lab5.tools.FilesTools.getAbsolutePath;

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
            if (new java.io.File(path).length() >= 2000) throw new LargeFileException();
            stream = new Scanner(file);
        } catch (FileNotFoundException | LargeFileException e) {
            System.out.println(e.getMessage());
        }
    }

    public String nextLine(){return get();}

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
