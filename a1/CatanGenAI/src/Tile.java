/**
 * Represents a tile on the Catan board
 */
public class Tile {
    private int numberToken;
    private Resource resource;
    
    public Tile(int numberToken, Resource resource) {
        this.numberToken = numberToken;
        this.resource = resource;
    }
    
    public int getNumberToken() {
        return numberToken;
    }
    
    public void setNumberToken(int numberToken) {
        this.numberToken = numberToken;
    }
    
    public Resource getResource() {
        return resource;
    }
    
    public void setResource(Resource resource) {
        this.resource = resource;
    }
    
    @Override
    public String toString() {
        return "Tile{numberToken=" + numberToken + ", resource=" + resource + "}";
    }
}
