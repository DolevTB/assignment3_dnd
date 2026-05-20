package assignment3_dnd;

public class Cell {
    private Occupant occupant;
    private boolean isFloor;
    private Position pos;

    public Cell(boolean isFloor, Position pos) {
        this.isFloor = isFloor;
        if (isFloor) { occupant = new Occupant(); }
        this.pos = pos;
    }

    public Occupant GetOccupant() { return occupant; }
    public boolean GetIsFloor() { return isFloor; }
    public Position GetPosition() { return pos; }
    public boolean GetIsFree() { return isFloor && occupant == null; }

    public void SetOccupant(Occupant occupant) {
        if (isFloor) { this.occupant = occupant; }
        //else { throw exception? }
    }
}