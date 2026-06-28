package assignment3_dnd;

public interface OccupantVisitor {
    void visit(Hero hero);
    void visit(Enemy enemy);
}