package assignment3_dnd;

public class Trap extends Enemy {
    private int visibility_time;
    private int invisibility_time;
    private int ticks_count;
    private boolean visible;
    private int TRAP_RANGE = 2;

    public Trap(char tile, String name, int hp_pool, int atk_pts, int def_pts, int exp_val, int visibility_time, int invisibility_time) {
        super(tile, name, hp_pool, atk_pts, def_pts, exp_val);
        this.visibility_time = visibility_time;
        this.invisibility_time = invisibility_time;
        this.ticks_count = 0;
        this.visible = true;
    }

    public boolean Tick(Position myPosition, Position targetPosition) {
        visible = ticks_count < visibility_time;
        
        if (ticks_count == (visibility_time + invisibility_time)) {
            ticks_count = 0;
        }
        else {
            ticks_count++;
        }
        return myPosition.Range(targetPosition) < TRAP_RANGE;
    }

    @Override
    public String toString() {
        return visible ? String.valueOf(super.tile) : ".";
    }
}