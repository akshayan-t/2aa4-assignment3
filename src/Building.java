public abstract class Building extends Build { //Abstract building class
    protected Node location;

    public abstract int getPoints(); //Gets points
    public abstract int getResourceMult(); //Gets resource multiplier
    public Node getLocation() { //Gets location
        return location;
    }
}
