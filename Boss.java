package assignment3_dnd;

import java.util.ArrayList;
import java.util.List;

public class Boss extends Monster implements HeroicUnit {
    private int ability_frequency;
    private int combat_ticks;

    public Boss(char tile, String name, int hp_pool, int atk_pts, int def_pts, int exp_val, int vision_range, int ability_frequency) {
        super(tile, name, hp_pool, atk_pts, def_pts, exp_val, vision_range);
        this.ability_frequency = ability_frequency;
        this.combat_ticks = 0;
    }

    public boolean CastAbility(List<Unit> targets) {
        Unit target = targets.get(0);
        int abilityDamage = this.atk_pts;
        int defenseRoll = (int)(Math.random() * (target.def_pts + 1));
        return Attack(target, abilityDamage, defenseRoll);
    }

    public Position Move(Position bossPos, Position playerPos, Hero player) {
        double rangeToPlayer = bossPos.Range(playerPos);
        if (rangeToPlayer < GetVisionRange()) {
            if (combat_ticks == ability_frequency) {
                combat_ticks = 0;
                List<Unit> player_lst = new ArrayList<>();
                player_lst.add(player);
                CastAbility(player_lst);
                return bossPos; 
            } else {
                combat_ticks++;
                return super.Move(bossPos, playerPos); 
            }
        } else {
            combat_ticks = 0;
            return super.Move(bossPos, playerPos);
        }
    }
}