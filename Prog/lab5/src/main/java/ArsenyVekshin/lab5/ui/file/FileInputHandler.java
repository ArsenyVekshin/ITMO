package ArsenyVekshin.lab5.ui.file;

import ArsenyVekshin.lab5.ui.InputHandler;

import java.io.*;
import java.util.Scanner;

public class FileInputHandler implements InputHandler {
    private InputStream file = null;
    private Scanner stream = null;

    public FileInputHandler(String path) throws FileNotFoundException {
        open(path);
    }

    public void open(String path) throws FileNotFoundException {
        try{
            file = new FileInputStream(path);
            stream = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
