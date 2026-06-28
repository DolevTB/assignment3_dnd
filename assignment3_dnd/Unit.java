package assignment3_dnd;

public abstract class Unit {
    protected String name;
    protected int hp_pool;
    protected int hp_current;
    protected int atk_pts;
    protected int def_pts;

    public Unit(String name, int hp_pool, int atk_pts, int def_pts) {
        this.name = name;
        this.hp_pool = hp_pool;
        this.hp_current = hp_pool;
        this.atk_pts = atk_pts;
        this.def_pts = def_pts;
    }
    //This method defines the behavior of any unit after a Game Tick.
    public void Tick() {}

    //This method defines the behavior of any unit when it engages in combat with another unit.
    //This function returns true if the target was killed in combat, and false otherwise.
    public boolean Combat(Unit target) {
        int atk_roll = (int)(Math.random()*(this.atk_pts + 1));
        int def_roll = (int)(Math.random()*(this.def_pts + 1));
        return Attack(target, atk_roll, def_roll); //true == killed target
    }

    public boolean Attack(Unit target, int atk_pts, int def_pts) {
        int damage = Math.max(0, atk_pts - def_pts);
        target.hp_current -= damage;
        if (target.hp_current <= 0) {
            target.hp_current = 0;
            return true;
        }
        return false;
    }    
}