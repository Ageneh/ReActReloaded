package functions;

import org.ini4j.Ini;

import java.io.*;

public abstract class INIReader {
    
    public static Ini getIni(String path) throws IOException {
        return new Ini(INIReader.iniReader(path));
    }
    
    public static BufferedReader iniReader(String path) throws FileNotFoundException {
        return new BufferedReader(new FileReader(new File(path)));
    }
    
}
