package assignment3_dnd;

import java.util.List;

public class Mage extends Hero {
    private int mana_pool;
    private int current_mana;
    private int mana_cost;
    private int spell_power;
    private int hits_count;
    private int ability_range;

    public Mage(String name, int hp_pool, int atk_pts, int def_pts, int mana_pool, int mana_cost, int spell_power, int hits_count, int ability_range) {
        super(name, hp_pool, atk_pts, def_pts);
        this.mana_pool = mana_pool;
        current_mana = mana_pool/4;
        this.mana_cost = mana_cost;
        this.spell_power = spell_power;
        this.hits_count = hits_count;
        this.ability_range = ability_range;
    }

    @Override
    public void GainExp(int exp) {
        int current_lvl = this.player_lvl;
        super.GainExp(exp);
        while(current_lvl < this.player_lvl) {
            current_lvl++;
            mana_pool += 25*current_lvl;
            current_mana = Math.min(current_mana + mana_pool/4, mana_pool);
            spell_power += 10*current_lvl;
        }
    }

    @Override
    public void Tick() {
        super.Tick();
        current_mana = Math.min(current_mana + this.player_lvl, mana_pool);
    }

    @Override
    public boolean CastAbility(List<Unit> targets) { //Cast Blizzard
        if (current_mana >= mana_cost) {
            current_mana -= mana_cost;
            int hits = 0;
            int targets_count = targets.size();
            while (hits < hits_count && targets_count > 0) { 
                Unit target = targets.get((int)(Math.random() * targets_count));
                //if while attacking, the target dies - remove it from the list.
                if (Attack(target, spell_power, (int)(Math.random() * (target.def_pts+1)))) {
                    targets.remove(target);
                    targets_count--;
                }   
                hits++;
            }
        }
        else {
            //not enough mana
        }
        return true; //TEMPORARY
    }

    public int GetAbilityRange() {
        return ability_range;
    }
}