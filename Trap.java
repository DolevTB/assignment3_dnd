package assignment3_dnd;

public class Trap extends Enemy {
    private int visibility_time;
    private int invisibility_time;
    private int ticks_count;
    private boolean visible;
    private boolean triggered = false;
    private int TRAP_RANGE = 2;

    public Trap(char tile, String name, int hp_pool, int atk_pts, int def_pts, int exp_val, int visibility_time, int invisibility_time) {
        super(tile, name, hp_pool, atk_pts, def_pts, exp_val);
        this.visibility_time = visibility_time;
        this.invisibility_time = invisibility_time;
        this.ticks_count = 0;
        this.visible = true;
    }

    public boolean isTriggered() { return triggered; }

    public void triggerTrap(Hero player) {
        if (!triggered) {
            sendMsg("You triggered " + this.name + "!");
            this.Combat(player);
            this.triggered = true;
            this.hp_current = 0;
        }
    }

    @Override
    public void Tick() {
        visible = ticks_count < visibility_time;
        if (ticks_count == (visibility_time + invisibility_time)) {
            ticks_count = 0;
        }
        else {
            ticks_count++;
        }
    }

    @Override
    public String toString() {
        return (visible || !triggered)? String.valueOf(super.tile) : ".";
    }
}