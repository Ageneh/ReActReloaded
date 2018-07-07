package functions;

/**
 * A class which allows to print strings in different colors. - Not really important when it comes
 * to the main program and way mainly used when debugging.
 *
 * @author Michael Heide
 * @author Henock Arega
 */
public enum ANSI {
    
    BLUE("[38;34m"),
    RED("[38;31m"),
    GREEN("[38;32m"),
    YELLOW("[38;33m"),
    BLACK("[38;0m"),
    MAGENTA("[38;35m"),
    CYAN("[38;36m"),
    WHITE("[38;37m"),
    REVERSE("[38;7m"),
    GREEN_BG("[38;2;92;82;92;48;2;100;100;0m"),
    RED_BG("[38;2;255;82;197;48;2;100;100;0m");
    
    private String esc;
    private final String end = "\033[0m";
    
    ANSI(String esc){
        this.esc = "\033" + esc;
    }
    
    public String colorize(String str) {
        return esc + str + end;
    }
    
    public void print(String str) {
        System.out.print(esc + str + end);
    }
    
    public void printf(String format, Object... args) {
        String string = String.format(format, args);
        this.print(string);
    }
    
    public void println(String str) {
        this.print(str + System.lineSeparator());
    }
    
    public void println(Object obj) {
        this.print(obj.toString() + System.lineSeparator());
    }
}
