package assignment3_dnd;

public interface CellVisitor {
    void visit(Wall wall);
    void visit(Floor floor);
}