package assignment3_dnd;

public class Monster extends Enemy{
    private int vision_range;

    public Monster(String name, int hp_pool, int atk_pts, int def_pts, int exp_val, int vision_range) {
        super(name, hp_pool, atk_pts, def_pts, exp_val);
        this.vision_range = vision_range;
    }

    public void Move(Position player_pos) {

    }
}