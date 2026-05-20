package assignment3_dnd;

public class Hunter extends Hero{
    private int range;
    private int arrows;
    private int tick_count;
    private final int STARTING_ARROWS = 10;

    public Hunter(String name, int hp_pool, int atk_pts, int def_pts, int range) {
        super(name, hp_pool, atk_pts, def_pts);
        this.range = range;
        this.arrows = STARTING_ARROWS;
        this.tick_count = 0;
    }

    @Override
    public void GainExp(int exp) {
        int current_lvl = GetPlayerLvl();
        super.GainExp(exp);
        while(current_lvl < GetPlayerLvl()) {
            current_lvl++;
            arrows += 10*current_lvl;
            SetAtkPts(GetAtkPts() + 2*current_lvl);
            SetDefPts(GetDefPts() + current_lvl);
        }
    }

    @Override
    public void Tick() {
        if (tick_count == 10) {
            tick_count = 0;
            arrows += GetPlayerLvl();
        }
        else {
            tick_count++;
        }
    }

    @Override
    public void CastAbility() { //Shoot
        if (arrows > 0) {
            arrows--;
            //find the nearest enemy within range. atk for 100% atk pts, 
            //enemy will defend. if there are multiple nearest enemies, choose one randomly.
        }
        else {
            //not enough arrows
        }
    }
}
