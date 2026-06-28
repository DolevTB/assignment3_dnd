package assignment3_dnd;

import java.util.List;

public class Rogue extends Hero {
    private final int MAX_ENERGY = 100;
    private final int ABILITY_RANGE = 2;
    private int energy;
    private int cost;

    public Rogue(String name, int hp_pool, int atk_pts, int def_pts, int cost) {
        super(name, hp_pool, atk_pts, def_pts);
        energy = MAX_ENERGY;
        this.cost = cost;
    }

    @Override
    public void GainExp(int exp) {
        int current_lvl = this.player_lvl;
        super.GainExp(exp);
        if(current_lvl < this.player_lvl) {
             energy = MAX_ENERGY;
        }
        while(current_lvl < this.player_lvl) {
            current_lvl++;
            this.atk_pts += 3*current_lvl;
        }
    }

    @Override
    public void Tick() {
        super.Tick();
        energy = Math.min(energy + 10, MAX_ENERGY);
    }

    @Override
    public boolean CastAbility(List<Unit> targets) { //Fan of Knives
        if (energy >= cost) {
            energy -= cost;
            //find all enemies in range < 2. atk for 100% atk pts, enemies will defend.
        }
        else {
            //not enough energy
        }
        return true; //TEMPORARY
    }

    public int GetAbilityRange() {
        return ABILITY_RANGE;
    }
}
