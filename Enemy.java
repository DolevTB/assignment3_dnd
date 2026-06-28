package assignment3_dnd;

public abstract class Enemy extends Unit {
    protected int exp_val;
    protected char tile;

    public Enemy(char tile, String name, int hp_pool, int atk_pts, int def_pts, int exp_val) {
        super(name, hp_pool, atk_pts, def_pts);
        this.tile = tile;
        this.exp_val = exp_val;
    }

    public int GetExpVal() { 
        return exp_val; 
    }

    @Override
    public String toString() {
        return String.valueOf(tile);
    }
}