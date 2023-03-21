package ArsenyVekshin.lab5.ui.exeptions;

import java.io.IOException;

import java.io.IOException;

public class LargeFileException extends IOException {
    public LargeFileException() { super("Выбранный файл слишком большой (максимальный размер файла 2КБ)"); }
}