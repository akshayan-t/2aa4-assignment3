/**
 * Represents a city building in Catan
 */
public class City extends Building {
    
    public City(Player owner, Node location) {
        super(owner, location);
    }
    
    @Override
    public int getPoints() {
        return 2;
    }
    
    @Override
    public String toString() {
        return "City{owner=" + getOwner() + ", points=" + getPoints() + "}";
    }
}
