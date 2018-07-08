package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * @author Henock Arega
 */
public class Ranking implements Serializable, Close {
    
    private final String FILENAME = "ranking.react";
    private final int MAX_RANK_NUM = 17;
    
    private File file;
    private ArrayList<User> ranking;
    
    public Ranking() {
        this.ranking = new ArrayList<>();
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
        if (this.ranking == null) this.ranking = new ArrayList<>();
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            this.close(Code.CLOSE);
        }));
    }
    
    public static void main(String[] args) {
        Ranking r = new Ranking();
        User u = new User("HelloPlayer");
        u.setPoints(3546576);
        r.add(u);
        r.close(Code.CLOSE);
        
        r = new Ranking();
        System.out.println(r.getRanking());
    }
    
    public ArrayList<User> getRanking() {
        this.ranking.sort(Comparator.comparing(User::getPoints).reversed());
        return new ArrayList<>(this.ranking);
    }
    
    public void update() {
        this.read();
    }
    
    private void read() {
        try {
            FileInputStream fis = new FileInputStream(this.file);
            ObjectInputStream ois = new ObjectInputStream(fis);
    
            Object o = ois.readObject();
    
            this.ranking = (ArrayList<User>) o;
        }catch (EOFException eof){
            this.ranking = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
        }
    }
    
    public void write() {
        try {
            FileOutputStream fos = new FileOutputStream(this.file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            
            oos.writeObject(this.ranking);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void add(User user){
        if(this.ranking.contains(user)) return;
        this.ranking.add(user);
        this.write();
    }
    
    public void add(Object arg){
        if(arg instanceof User){
            this.add((User) arg);
        }
    }
    
    public void reset(){
        this.ranking = new ArrayList<>();
        this.write();
    }
    
    @Override
    public void close(Code code) {
        this.write();
    }
}
