package functions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public abstract class INIReader {

    public static BufferedReader readIni(String path) throws FileNotFoundException {
        return new BufferedReader(new FileReader(new File(path)));
    }

}
