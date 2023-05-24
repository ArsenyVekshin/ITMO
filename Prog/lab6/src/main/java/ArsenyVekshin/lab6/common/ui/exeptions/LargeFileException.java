package ArsenyVekshin.lab6.common.ui.exeptions;

import java.io.IOException;

/**
 * target file is too large
 */
public class LargeFileException extends IOException {
    public LargeFileException() { super("Выбранный файл слишком большой (максимальный размер файла 2КБ)"); }
}