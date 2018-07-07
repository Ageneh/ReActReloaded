package model;

import java.io.*;
import java.util.HashMap;

/**
 * @author Henock Arega
 */
public class Ranking implements Serializable, Close {
    
    private final String FILENAME = "ranking.react";
    private final int MAX_RANK_NUM = 17;
    
    private File file;
    private HashMap<Integer, String> ranking;
    
    public Ranking() {
        this.ranking = new HashMap<>();
        this.file = new File("res/serialized/" + FILENAME);
        if (! file.exists()) {
            try {
                this.file.createNewFile();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.read();
        if (this.ranking == null) this.ranking = new HashMap<>();
    }
    
    public HashMap<Integer, String> getRanking() {
        return ranking;
    }
    
    private void read() {
        try {
            FileInputStream fis = new FileInputStream(this.file);
            ObjectInputStream ois = new ObjectInputStream(fis);
    
            Object o = ois.readObject();
    
            this.ranking = (HashMap<Integer, String>) o;
        }catch (EOFException eof){
            this.ranking = new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
        }
    }
    
    private void write() {
        try {
            FileOutputStream fos = new FileOutputStream(this.file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            
            oos.writeObject(this.ranking);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void close(Code code) {
        this.write();
    }
}
