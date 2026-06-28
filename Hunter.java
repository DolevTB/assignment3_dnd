package assignment3_dnd;

public class Hunter extends Hero{
    private int arrows;
    private int tick_count;
    private final int STARTING_ARROWS = 10;

    public Hunter(String name, int hp_pool, int atk_pts, int def_pts, int range) {
        super(name, hp_pool, atk_pts, def_pts);
        this.ability_range = range;
        arrows = STARTING_ARROWS;
        tick_count = 0;
    }

    @Override
    public void GainExp(int exp) {
        int current_lvl = this.player_lvl;
        super.GainExp(exp);
        while(current_lvl < this.player_lvl) {
            current_lvl++;
            arrows += 10*current_lvl;
            this.atk_pts += 2*current_lvl;
            this.atk_pts += 2*current_lvl;
            def_pts += current_lvl;
        }
    }

    @Override
    public void Tick() {
        if (tick_count == 10) {
            tick_count = 0;
            arrows += this.player_lvl;
        }
        else {
            tick_count++;
        }
    }

    @Override
    public boolean CastAbility(Unit target) { //Shoot
        if (arrows > 0) {
            arrows--;
            //find the nearest enemy within range. atk for 100% atk pts, 
            //enemy will defend. if there are multiple nearest enemies, choose one randomly.
        }
        else {
            //not enough arrows
        }
        return true; //TEMPORARY
    }
}
