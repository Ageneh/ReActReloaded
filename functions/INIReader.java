package functions;

import org.ini4j.Ini;

import java.io.*;

public abstract class INIReader {

    public static BufferedReader readIni(String path) throws FileNotFoundException {
        return new BufferedReader(new FileReader(new File(path)));
    }

    public static Ini iniReader(String path) throws IOException {
        return new Ini(INIReader.readIni(path));
    }

}
