package assignment3_dnd;

public class Unit {
    private String name;
    private int hp_pool;
    private int hp_current;
    private int atk_pts;
    private int def_pts;

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
    public void Combat(Unit target) {}

    //Getters and Setters
    public String GetName() { return name; }
    public int GetHpPool() { return hp_pool; }
    public int GetHpCurrent() { return hp_current; }
    public int GetAtkPts() { return atk_pts; }
    public int GetDefPts() { return def_pts; }

    public void SetHpPool(int hp_pool) { 
        if(hp_pool >= 0) {
            
            this.hp_pool = hp_pool; 
        }
        else {
            //throw Exception?
        }
    }

    public void SetHpCurrent(int hp_current) { 
        if (hp_current < 0) {
            this.hp_current = 0; //Unit is dead - need a func for that?
        }
        else if (hp_current > hp_pool) { //overheal
            this.hp_current = hp_pool;
        }
        else {
            this.hp_current = hp_current;
        }
    }

    public void SetAtkPts(int atk_pts) { 
        if (atk_pts >= this.atk_pts) { //atk pts can only increase
            this.atk_pts = atk_pts; 
        }
        else {
            //throw Exception?
        }
    }

    public void SetDefPts(int def_pts) { 
        if (def_pts >= this.def_pts) { //def pts can only increase
            this.def_pts = def_pts; 
        }
        else {
            //throw Exception?
        }
    }
}