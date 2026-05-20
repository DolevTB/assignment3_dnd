package assignment3_dnd;

public class Occupant {
    private Unit unit;

    public Occupant() {
        unit = null;
    }

    public Unit GetUnit() { return this.unit; }

    public void SetUnit(Unit unit) {
        this.unit = unit;
    }
}
