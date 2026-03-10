/**
 * Demonstrator class - entry point for the Catan game
 */
public class Demonstrator {
    private String mainArea;
    
    public Demonstrator(String mainArea) {
        this.mainArea = mainArea;
    }
    
    public String getMainArea() {
        return mainArea;
    }
    
    public void setMainArea(String mainArea) {
        this.mainArea = mainArea;
    }
    
    public static void main(String[] args) {
        // Main entry point for the game
        System.out.println("Starting Catan Game...");
        Demonstrator demo = new Demonstrator("Catan Game");
    }
}
