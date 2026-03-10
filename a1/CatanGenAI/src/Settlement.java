/**
 * Represents a settlement building in Catan
 */
public class Settlement extends Building {
    
    public Settlement(Player owner, Node location) {
        super(owner, location);
    }
    
    @Override
    public int getPoints() {
        return 1;
    }
    
    @Override
    public String toString() {
        return "Settlement{owner=" + getOwner() + ", points=" + getPoints() + "}";
    }
}
