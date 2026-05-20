package assignment3_dnd;

public class Enemy extends Unit {
    private int exp_val;

    public Enemy(String name, int hp_pool, int atk_pts, int def_pts, int exp_val) {
        super(name, hp_pool, atk_pts, def_pts);
        this.exp_val = exp_val;
    }

    // Getters and Setters
    public int GetExpVal() { return exp_val; }
}