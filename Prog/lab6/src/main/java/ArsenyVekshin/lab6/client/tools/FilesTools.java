package ArsenyVekshin.lab6.client.tools;

import java.io.File;

public class FilesTools {

    /**
     * Find absolute path from relative
     * @param path relative path
     * @return absolute path
     */
    public static String getAbsolutePath(String path){
        return new File(path).getAbsolutePath();
    }
}
